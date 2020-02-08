package com.project.cardata.Mapper;

import com.project.cardata.bean.CheckOrder;
import com.project.cardata.bean.Orders;
import com.project.cardata.bean.goods_real;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface OrderMapper {

    //查询订单表(user_id/goods_id)
    //SELECT * FROM orders WHERE goods_id=1 && car_id =1
    @Options(useGeneratedKeys = true,keyProperty = "order_id")
    @Select({"select * from orders where user_id=#{user_id} and goods_id=#{goods_id}"})
    public Orders OrdersBytwo(CheckOrder checkOrder);

    //查询订单表(user_id/goods_id/empty)
    //SELECT * FROM orders WHERE goods_id=1 && car_id =1
    @Options(useGeneratedKeys = true,keyProperty = "order_id")
    @Select({"select * from orders where user_id=#{user_id} and goods_id=#{goods_id} and data='empty'"})
    public Orders OrdersBythree(CheckOrder checkOrder);


    //查询订单表<user_id/empty>

    @Options(useGeneratedKeys = true,keyProperty = "order_id")
    @Select({"select * from orders where user_id=#{user_id} and data='empty'"})
    public List<Orders> getOrders(Integer user_id);

    //增加订单表
    @Insert("insert into orders(goods_id,user_id,user_addr_id,sum) values(#{goods_id},#{user_id},#{user_addr_id},#{sum})")
    public  int  InsertGoods_real(Orders orders);

    //更新订单表
    @Update("update orders set sum=#{sum} where order_id=#{order_id}")
    public  int Updategoods_real(Orders orders);

    //删除购物车
    @Delete("delete from orders where order_id =#{order_id} ")
    public int deleteGoods_real(Integer order_id);

}
