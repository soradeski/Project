package com.project.cardata.Mapper;


import com.project.cardata.bean.Sf;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface SfMapper {

    @Select("select * from sf where sf_id=#{sf_id}")
    public Sf getSfInformationBySf_id(Sf sf);
    @Update("update sf set sf_timeandlocation=#{sf_timeandlocation}")
    public void updateTimeAndLocationBySf_id(Sf sf);
}
