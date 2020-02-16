package com.project.cardata.Mapper;




import com.project.cardata.bean.GoodsInformation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface GoodsInformationMapper {
    @Select("select * from goods where goods_id=#{goods_id}")
    public GoodsInformation getInformationByGoods_id(GoodsInformation goodsinformation);
    @Update("update goods set goods_num=#{goods_num} where goods_id=#{goods_id}")
    public void updateRepertoryByGoods_id(GoodsInformation goodsinformation);
    @Update("update goods set goods_sale=#{goods_sale} where goods_id=#{goods_id}")
    public void updateSaleByGoods_id(GoodsInformation goodsinformation);
}
