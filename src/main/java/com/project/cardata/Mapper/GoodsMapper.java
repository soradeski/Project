package com.project.cardata.Mapper;

import com.project.cardata.bean.Car;
import com.project.cardata.bean.goods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface GoodsMapper {
    //查询全部
    @Select("select * from goods")
    public List<goods> getAllGoods();
    //查询商品表
    @Select("select * from goods where goods_id=#{goods_id}")
    public goods getGoods(Integer goods_id);
    //更新商品数据
    @Update("update goods set goods_num=#{goods_num},goods_sale=#{goods_sale} where goods_id=#{goods_id}")
    public  int UpdateCar(goods goods);


}
