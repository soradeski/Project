package com.project.cardata.Mapper;

import com.project.cardata.bean.Car;
import com.project.cardata.bean.UserAddr;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserAddrMapper {
    @Options(useGeneratedKeys = true,keyProperty = "user_addrid")
    @Select({"select * from user_addr where User_id=#{User_id}"})
    public UserAddr USER_ADDR(Integer User_id);
}
