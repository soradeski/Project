package com.project.cardata.Controller;


import com.project.cardata.Mapper.CarDataMapper;
import com.project.cardata.Mapper.GoodsMapper;
import com.project.cardata.Mapper.GoodsRealMapper;
import com.project.cardata.Mapper.UserDataMapper;
import com.project.cardata.bean.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.cert.TrustAnchor;

@Controller
public class ShowWebController {

    @Autowired
    UserDataMapper userDataMapper;
    @Autowired
    CarDataMapper carDataMapper;
    @Autowired
    GoodsRealMapper goodsRealMapper;
    @Autowired
    GoodsMapper goodsMapper;


    @PostMapping("/show/{user_id}/{goods_id}")
    public String AddGoods(@PathVariable("user_id") Integer User_id,@PathVariable("goods_id") Integer goods_id){
        Goods goods = goodsMapper.getGoods(goods_id);
        try {
            //判断用户是否登录
            User user = userDataMapper.getUser(User_id);
            try {
                //判断用户是否有购物车
                Car car = carDataMapper.getCar(User_id);
            }catch (Exception e){
                System.out.println("未查询到购物车Id");
                //创建一个购物车
                Car ncar = new Car();
                ncar.setUser_id(User_id);
                Integer car = carDataMapper.InsertCar(ncar);

            }
            finally {
                Car car = carDataMapper.getCar(User_id);
                System.out.println("UserID" + car.getUser_id());
                System.out.println("car_id" + car.getCar_id()); //获取到User_id的购物车的car_id
                System.out.println("goods_id" + goods.getGoods_id());//获取到商品信息
                System.out.println("goods_num"+goods.getGoods_num());
                //判断是否还有商品
                if (goods.getGoods_num()>0) {
                    //增加到购物车
                    try {
                        Check check = new Check();
                        check.setGoods_id(goods_id);
                        check.setCar_id(car.getCar_id());
                        goods_real goods_real = goodsRealMapper.getgoods_realByTwo(check);
                        System.out.println("checkId"+check.getCar_id());
                        System.out.println("checkGoods_id"+check.getGoods_id());


                        if(goods_real.getGoods_real_id()!= null) {
                            System.out.println("已经有相同产品");
                            System.out.println("goods_real_id" + goods_real.getGoods_real_id());
                            System.out.println("car_id" + goods_real.getCar_id() + "goods_Id" + goods_real.getGoods_id());

                            goods_real.setQuantity(goods_real.getQuantity() + 1);
                            System.out.println("quantity" + goods_real.getQuantity());

                            goodsRealMapper.Updategoods_real(goods_real);//更新数量即可
                        }
                    }catch (Exception e) {

                        //如果没有该相同的goods_Id,car_id所对应的商品新建一个
                        //将商品信息封装带goods_real类里面
                        System.out.println("没有相同产品");
                        goods_real ngoods_real = new goods_real();
                        ngoods_real.setCar_id(car.getCar_id());
                        ngoods_real.setGoods_id(goods.getGoods_id());
                        ngoods_real.setQuantity(1);
                        goodsRealMapper.InsertGoods_real(ngoods_real);

//                        //购物车里面有相同的goods_Id,car_id所对应的商品

//                        System.out.println("已经有相同产品");

                    }finally {
                        //减少商品的库存
                        Goods goods1 = new Goods();
                        Integer seal =goods.getGoods_sale();//销售量
 //                       goods1 = goodsMapper.getGoods(goods_id);//将该商品存入goods
  //                      goods1.setGoods_num(goods1.getGoods_num()-1);//将库存减一
 //                       goods1.setGoods_sale(seal+1);//更新销售量
  //                      goodsMapper.UpdateCar(goods1);//更新user的库存数据
                        System.out.println("以增加购物车");

                    }


                }else {
                    System.out.println("商品库存不足");
                }
            }
        }catch (Exception e){
            System.out.println("用户未登录，QAQ，请重新登录");
            return "login";
        }

        return "show";
    }
}
