package com.project.cardata.Controller;

import com.alibaba.fastjson.JSONObject;
import com.project.cardata.Mapper.*;
import com.project.cardata.bean.*;
import jdk.management.resource.internal.inst.SocketOutputStreamRMHooks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.sound.midi.Soundbank;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Controller
public class CarController {
    @Autowired
    GoodsRealMapper goodsRealMapper;
    @Autowired
    CarDataMapper carDataMapper;
    @Autowired
    GoodsMapper goodsMapper;
    @Autowired
    UserDataMapper userDataMapper;
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    UserAddrMapper userAddrMapper;

    //处理加号增加一个商品-------------------------------------------------//
    @RequestMapping("/gouwuche/{user_id}/{goods_id}")
    public String GouWuChe(@PathVariable("user_id") Integer User_id,@PathVariable("goods_id") Integer goods_id){
            //int
        //获取CarId
        Car car = carDataMapper.getCar(User_id);
        System.out.println("CarID"+car.getCar_id());
        System.out.println("goodsId" + goods_id);
        // 新建一个Check类
        Check check = new Check();
        check.setCar_id(car.getCar_id());
        check.setGoods_id(goods_id);
        // 获取goods_real_id
         goods_real goods_real=goodsRealMapper.getgoods_realByTwo(check);

        System.out.println("goods_real_id" + goods_real.getGoods_real_id());
        //获取商品
        Goods goods = goodsMapper.getGoods(goods_id);
       if(goods.getGoods_num()>0) {
           //增加该产品的数量
           goods_real.setQuantity(goods_real.getQuantity() + 1);
           System.out.println("quantity" + goods_real.getQuantity());

           goodsRealMapper.Updategoods_real(goods_real);//更新数量即可

           System.out.println("以增加购物车");
//           return 1;
       }else {
           System.out.println("商品不足");
//           return 0;
       }
        return "Car";

    }
    //处理点减号
    @PostMapping("/minuseCar/{user_id}/{goods_id}")
    public String MinuseShopCar(@PathVariable("user_id") Integer User_id,@PathVariable("goods_id") Integer goods_id){
        //int
        //获取CarId
        Car car = carDataMapper.getCar(User_id);
        System.out.println("CarID"+car.getCar_id());
        System.out.println("goodsId" + goods_id);
        // 新建一个Check类
        Check check = new Check();
        check.setCar_id(car.getCar_id());
        check.setGoods_id(goods_id);
        // 获取goods_real_id
        goods_real goods_real=goodsRealMapper.getgoods_realByTwo(check);

        System.out.println("goods_real_id" + goods_real.getGoods_real_id());
        //判断商品在购物车的数量是否大于1
        if(goods_real.getQuantity()>1){
            System.out.println("减少购物车里该商品的存放数量");
            goods_real.setQuantity(goods_real.getQuantity()-1);
            goodsRealMapper.Updategoods_real(goods_real);
           // return 1;
        }else{
            System.out.println("删除该商品从购物车");
            System.out.println("goods_rel_id"+goods_real.getGoods_real_id());
            goodsRealMapper.deleteGoods_real(goods_real.getGoods_real_id());
            // return 1;
        }

        //return 0;
        return "Car";
    }

    //处理一个商品前面打上勾
    @PostMapping("/Onedetermine/{user_id}/{goods_id}")
    public String Onedetermined(@PathVariable("user_id") Integer User_id,@PathVariable("goods_id") Integer goods_id){
           //int
        try {
            Car car = carDataMapper.getCar(User_id);
            //封装一个Check
            Check check = new Check();
            check.setCar_id(car.getCar_id());
            check.setGoods_id(goods_id);
            //goods_real中的quantity
            goods_real goods_real = goodsRealMapper.getgoods_realByTwo(check);
            System.out.println("quantity" + goods_real.getQuantity());
            //封装一个CheckOrder
            CheckOrder checkOrder = new CheckOrder();
            checkOrder.setGoods_id(goods_id);
            checkOrder.setUser_id(User_id);
            System.out.println("goods_id" + goods_id + "User_id" + User_id);

            //获取用户住址
            UserAddr userAddr = userAddrMapper.USER_ADDR(User_id);

            System.out.println("即将新建");

            //新建orders类
            Orders norders = new Orders();
            norders.setGoods_id(goods_id);//加入商品
            norders.setUser_id(User_id);//用户
            norders.setUser_addr_id(userAddr.getUser_addrid());//加入住址
            norders.setSum(goods_real.getQuantity());//加入数量

            //加入到Orders表中
            orderMapper.InsertGoods_real(norders);
            System.out.println("加入完成nordersId");
            //减少商品的库存
            //获取该商品
            Goods goods = goodsMapper.getGoods(goods_id);
            System.out.println("goodsNum" + goods.getGoods_num());
            System.out.println("goodsSel" + goods.getGoods_sale());//销售量
            goods.setGoods_num(goods.getGoods_num() - goods_real.getQuantity());
            goods.setGoods_sale(goods.getGoods_sale() + goods_real.getQuantity());
            goodsMapper.UpdateCar(goods);//更改物品数量
            System.out.println("以增加订单/修改销售量/修改库存");

          //  return 1;
        }catch (Exception e){
            System.out.println("增加订单出错QAQ");
         //   return 0;

        }
        return "Car";

    }
    //处理一个商品前面去掉勾
    @PostMapping("/OneDelgoods/{user_id}/{goods_id}")
    public String DelCommplants(@PathVariable("user_id") Integer User_id,@PathVariable("goods_id") Integer goods_id){
            //int
        try {
            //封装一个CheckOrder
            CheckOrder checkOrder = new CheckOrder();
            checkOrder.setGoods_id(goods_id);
            checkOrder.setUser_id(User_id);
            System.out.println("goods_id" + goods_id + "User_id" + User_id);

            System.out.println("orders_id=" + orderMapper.OrdersBythree(checkOrder).getOrder_id());

            //删除订单中的该商品
            orderMapper.deleteGoods_real(orderMapper.OrdersBythree(checkOrder).getOrder_id());
            //return 1;
        }catch (Exception e){
            System.out.println("失败");
           // return  0;
        }
        return "Car";

    }

    //处理结算--------------------------------------------
    @PostMapping("/confirmorder/{user_id}")
    public String Settlement(@PathVariable("user_id") Integer User_id){
        return "Settlement";
    }

    //处理合计
 //   @ResponseBody
    @PostMapping("/Count/{user_id}")
    public String CountMoney(@PathVariable("user_id") Integer User_id,Model model){
        //object
      List<Orders> OderList =orderMapper.getOrders(User_id);
        System.out.println("是否有元素"+OderList.isEmpty());
        Integer countMoney = 0;
      System.out.println("元素："+OderList);
        for (Orders orders:OderList) {
            System.out.println("ordersId"+orders.getGoods_id()+"/"+orders.getSum());
            countMoney = countMoney+((goodsMapper.getGoods(orders.getGoods_id()).getGoods_price())*orders.getSum());

        } System.out.println("countMoney"+countMoney);

        Object o = JSONObject.toJSON(countMoney);

        model.addAttribute("countMoney",countMoney);
        System.out.println(o);
        //return o;
        return  "Car";
    }

    //库存少的购物车商品
    @PostMapping("/less/{car_id}")
    public String checkLess(@PathVariable("car_id") Integer car_Id){
            //object
   //查出car_Id所有商品
        List<goods_real> goodslist =  goodsRealMapper.getgoods_real(car_Id);
    //新建一个list存放商品库存较少的商品
        List<goods_real> lessList = new ArrayList<goods_real>();
    //遍历所有商品
        for (goods_real goodslist1:goodslist) {
            //获取商品ID
           Integer goods_id =goodslist1.getGoods_id();
           ////查询商品，判断库存
            if (goodsMapper.getGoods(goods_id).getGoods_num()<10 &&goodsMapper.getGoods(goods_id).getGoods_num()>0 ){
                System.out.println("该商品库存较少");
                //存到新list里面
                lessList.add(goodslist1);
            }
        }
        //把新的list转成JSON
        Object lessGoodsList = JSONObject.toJSON(lessList);
        System.out.println(lessGoodsList);
        //return lessGoodsList;
    return "Car";
    }
    //库存为0的购物车商品
    @PostMapping("/zero/{car_id}")
    public String checkZero(@PathVariable("car_id") Integer car_Id){
        //查出car_Id所有商品
        List<goods_real> goodslist =  goodsRealMapper.getgoods_real(car_Id);
        //新建一个list存放商品库存为0的商品
        List<goods_real> zeroList = new ArrayList<goods_real>();
        //遍历所有商品
        for (goods_real goodslist1:goodslist) {
            //获取商品ID
            Integer goods_id =goodslist1.getGoods_id();
            ////查询商品，判断库存
            if (goodsMapper.getGoods(goods_id).getGoods_num() == 0 ){
                System.out.println("该商品库存为0");
                //存到新list里面
                zeroList.add(goodslist1);
            }
        }
        //把新的list转成JSON
        Object lessGoodsList = JSONObject.toJSON(zeroList);
        System.out.println(lessGoodsList);
        return "Car";
    }


}