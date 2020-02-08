package com.project.cardata.Mapper;

import com.project.cardata.bean.Car;
import com.project.cardata.bean.Check;
import com.project.cardata.bean.goods_real;
import org.apache.ibatis.annotations.*;

import java.util.Map;

@Mapper
public interface GoodsRealMapper {
    //查询购物车

    @Options(useGeneratedKeys = true,keyProperty = "goods_rel_id")
    @Select({"select * from goods_rel where car_id=#{car_id}"})
    public Map<Integer,goods_real> getgoods_real(Integer car_id);

    //查询购物车(car_Id/goods_id)
    //SELECT * FROM goods_rel WHERE goods_id=1 && car_id =1
    @Options(useGeneratedKeys = true,keyProperty = "goods_rel_id")
    @Select({"select * from goods_rel where car_id=#{car_id} and goods_id=#{goods_id}"})
    public goods_real getgoods_realByTwo(Check check);

    //删除购物车
    @Delete("delete from goods_rel where goods_rel_id =#{goods_rel_id} ")
    public int deleteGoods_real(Integer goods_real_id);


    //增加购物车
    @Insert("insert into goods_rel(car_id,goods_id,quantity) values(#{car_id},#{goods_id},#{quantity})")
    public  int  InsertGoods_real(goods_real goods_real);

    //更新购物车
    @Update("update goods_rel set quantity=#{quantity} where goods_rel_id=#{goods_rel_id}")
    public  int Updategoods_real(goods_real goods_real);
}
