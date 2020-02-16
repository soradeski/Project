package com.project.cardata.Mapper;


import com.project.cardata.bean.OrderFinal;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;


@Mapper
public interface OrderFinalMapper {
    @Insert("Insert into orders_final" +
            " (goods_id,user_id,date,state,sum)" +
            " values(#{goods_id},#{user_id},#{date},#{state},#{sum})")
    public void addOrderFinal(OrderFinal orderfinal);


    @Select("select * from orders_final where user_id=#{user_id}")
    public ArrayList<OrderFinal> getOrderFinalByUser_id(OrderFinal orderfinal);

    @Select("select * from orders_final where date=#{date} and user_id=#{user_id}")
    public  OrderFinal getOrderFinalByDateAndUser_id(OrderFinal orderfinal);

    @Options(useGeneratedKeys = true,keyProperty = "order_id")
    @Select("select * from orders_final where order_id=#{order_id}")
    public  OrderFinal getOrderFinalByOrder_id(OrderFinal orderfinal);

    @Delete("delete from orders_final where date=#{date} and user_id=#{user_id}")
    public void deleteOrderFinalByDateAndUser_id(OrderFinal orderfinal);


    @Update("update orders_final set state=#{state} where date=#{date} and user_id=#{user_id}")
    public void updateStateByDateAndUser_id(OrderFinal orderfinal);

    @Options(useGeneratedKeys = true,keyProperty = "order_id")
    @Update("update orders_final set state=#{state} where order_id=#{order_id}")
    public void updateStateByOrder_id(OrderFinal orderfinal);
}
