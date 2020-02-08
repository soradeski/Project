package com.project.cardata.Mapper;


import com.project.cardata.bean.Car;
import org.apache.ibatis.annotations.*;

@Mapper
//这是一个操作数据库的MAPPER
public interface CarDataMapper {

    //查询购物车
    @Options(useGeneratedKeys = true,keyProperty = "Car.")
    @Select({"select * from car where User_id=#{User_id}"})
    public Car getCar(Integer Uer_id);

    //删除购物车
    @Options(useGeneratedKeys = true,keyProperty = "car_id")
    @Delete("delete from car where car_id =#{car_id} ")
    public int deleteCar(Integer User_id);


    //增加购物车
    @Options(useGeneratedKeys = true,keyProperty = "car_id",keyColumn = "car_id")
    @Insert("insert into car(user_id) values(#{User_id})")
    public  int  InsertCar(Car car);

    //更新购物车
    @Options(useGeneratedKeys = true,keyProperty = "car_id")
    @Update("update user set user_id=#{User_id} where user_id=#{User_id}")
    public  int UpdateCar(Car car);
}
