package com.project.cardata.Mapper;


import com.project.cardata.bean.Car;
import com.project.cardata.bean.User;
import org.apache.ibatis.annotations.*;

@Mapper//这是一个操作User数据库的Mapper
public interface UserDataMapper {

    //查询用户表
    @Select("select * from user where User_id=#{User_id}")
    public User getUser(Integer User_id);


}
