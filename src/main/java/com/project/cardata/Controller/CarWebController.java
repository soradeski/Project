package com.project.cardata.Controller;


import com.alibaba.fastjson.JSONObject;
import com.project.cardata.Mapper.CarDataMapper;
import com.project.cardata.Mapper.GoodsMapper;
import com.project.cardata.Mapper.GoodsRealMapper;
import com.project.cardata.Mapper.UserDataMapper;
import com.project.cardata.bean.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Controller
public class CarWebController {

    @Autowired
    CarDataMapper carDataMapper;//把car数据库MAPPER注册进来
    @Autowired
    UserDataMapper userDataMapper;//把User数据库MAPPER注册进来
    @Autowired
    GoodsRealMapper goodsRealMapper;//把goods_real数据库的mapper注册进来
     @Autowired
     GoodsMapper  goodsMapper;//把goods数据库的mapper注册进来


  // @ResponseBody
    @RequestMapping({"/","/index.html"})
    public Object index(ModelMap modelMap){
        List<Goods> Allgoods = goodsMapper.getAllGoods();
        modelMap.addAttribute("allgoods",Allgoods);
        for (Goods goods:Allgoods) {
            System.out.println("goods_id"+goods.getGoods_id());
        }
        Object o = JSONObject.toJSON(Allgoods);
        System.out.println(o);

        return "index";
    }

 //   @RequestMapping("/car")
//    public String success(){
//        return "Car";
//    }

//    @ResponseBody
//    @RequestMapping("/hello")
//    public String hello(){
//        return  "HELLO";
//    }

    //判断用户登路
    @PostMapping(value = {"/car/{user_id}"})
    public String Car(@PathVariable("user_id") Integer User_id){
        //从数据库中查询User_id是否存在
        //carDataMapper.getCar(User_id);
         User user = userDataMapper.getUser(User_id);
         Car  car  = carDataMapper.getCar(User_id);
        System.out.println("User_id"+user.getUser_id());
//        System.out.println("Car_id"+car.getUser_id());
        if (user.getUser_id() > 0)
        {
            System.out.println("User_id"+user.getUser_id());
            //存放所有商品
            List<goods_real> Alllist = goodsRealMapper.getgoods_real(car.getCar_id());
            //转成JSON
   //         Object AlllistJson = JSONObject.toJSON(Alllist);
   //         System.out.println(AlllistJson);
            //将存放的商品信息查出来
            //建一个商品list
            List<Goods> AllCarGoods = new ArrayList<Goods>();
            for (goods_real goods_real:Alllist) {
                //通过goods_real查询goods_Id查询goods,并加入goods_list
                AllCarGoods.add(goodsMapper.getGoods( goods_real.getGoods_id()));
            }
            Object AllCarGoods_JSON = JSONObject.toJSON(AllCarGoods);
            System.out.println(AllCarGoods_JSON);

            return "Car";
        }

        else {
            return "login";
        }

    }
//
//    @PostMapping(value = {"/car","/plus"})
//    public String NoLogin(){
//        return "login";
//    }
//验证用户是否登录-----------------------------------------------------------------------

    @PostMapping(value = "/show" )
    public String show(){
        return "Show";
    }

//点击进入商品详情界面


    @PostMapping(value = "/plus/{user_id}/{goods_id}/{quantity}")
    public String addGoodsReal(@PathVariable("user_id") Integer User_id,@PathVariable("goods_id") Integer goods_id,@PathVariable("quantity") Integer quantity){

        Goods goods = goodsMapper.getGoods(goods_id);
    try {
        User user = userDataMapper.getUser(User_id);
        try {
            Car car = carDataMapper.getCar(User_id);

        } catch (Exception e) {
            System.out.println("未查到UserID为" + User_id + "的购物车");
            //创建User_id的购物车
            Car ncar = new Car();//建一个Car类
            ncar.setUser_id(User_id);//存入新的User_Id
            Integer ucar = carDataMapper.InsertCar(ncar);//存入数据表

        } finally {
            Car car = carDataMapper.getCar(User_id);
            System.out.println("UserID" + car.getUser_id());
            System.out.println("car_id" + car.getCar_id()); //获取到User_id的购物车的car_id
            System.out.println("goods_id" + goods.getGoods_id());//获取到商品信息
            System.out.println("quantity" + quantity);//获取到商品存储量

            //将商品信息封装带goods_real类里面
            goods_real ngoods_real = new goods_real();
            ngoods_real.setCar_id(car.getCar_id());
            ngoods_real.setGoods_id(goods.getGoods_id());
            ngoods_real.setQuantity(quantity);

            //判断是否还有商品
            if (goods.getGoods_num()>0 && goods.getGoods_num()>quantity){
                //增加到购物车
                try {
                    Check check = new Check();
                    check.setGoods_id(car.getCar_id());
                    check.setCar_id(goods_id);



                    goods_real goods_real = goodsRealMapper.getgoods_realByTwo(check);

                    if(goods_real.getGoods_real_id()!= null) {
                        System.out.println("已经有相同产品");
                        System.out.println("goods_real_id" + goods_real.getGoods_real_id());
                        System.out.println("car_id" + goods_real.getCar_id() + "goods_Id" + goods_real.getGoods_id());

                        goods_real.setQuantity(goods_real.getQuantity() + quantity);
                        System.out.println("quantity" + goods_real.getQuantity());

                        goodsRealMapper.Updategoods_real(goods_real);//更新数量即可
                    }
                }catch (Exception e) {

                    //如果没有该相同的goods_Id,car_id所对应的商品新建一个
                    //将商品信息封装带goods_real类里面
                    System.out.println("没有相同产品");
                    goods_real ngoods2_real = new goods_real();
                    ngoods2_real.setCar_id(car.getCar_id());
                    ngoods2_real.setGoods_id(goods.getGoods_id());
                    ngoods2_real.setQuantity(quantity);
                    goodsRealMapper.InsertGoods_real(ngoods2_real);

//                        //购物车里面有相同的goods_Id,car_id所对应的商品

//                        System.out.println("已经有相同产品");

                }finally {
                    //减少商品的库存
                    Goods goods1 = new Goods();
                    Integer seal =goods.getGoods_sale();//销售量
//                    goods1 = goodsMapper.getGoods(goods_id);//将该商品存入goods
//                    goods1.setGoods_num(goods1.getGoods_num()-1);//将库存减一
//                    goods1.setGoods_sale(seal+1);//更新销售量
    //                goodsMapper.UpdateCar(goods1);//更新user的库存数据
                    System.out.println("以增加购物车");

                }
            }else {
                System.out.println("商品库存不足");
            }
        }

    }catch (Exception e) {
        System.out.println("请登录");
        return "login";

    }

        return "index";
    }

    //点击加号添加到购物车的商品

}
