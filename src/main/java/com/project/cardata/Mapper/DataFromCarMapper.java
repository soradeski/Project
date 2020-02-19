package com.project.cardata.Mapper;


import com.project.cardata.bean.ConfirmOrderTools;
import com.project.cardata.bean.DataFromCar;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface DataFromCarMapper {
    @Select("select * from orders where data='empty'and user_id=#{user_id}")
    public List<DataFromCar> getInitialOrderList(Integer user_id);

    @Select("select * from orders where user_id=#{user_id} and goods_id=#{goods_id} and data='empty'")
    public DataFromCar getSumByConfirmOrderTools(ConfirmOrderTools confirmordertools);

    @Update("update orders set data='not empty'")
    public void updateOrderData();

    @Update("update orders set sum=#{sum} where (user_id=#{user_id} and goods_id=#{goods_id})and data='empty'")
    public void updateSumByConfirmOrderTools(ConfirmOrderTools confirmordertools);
}
