package com.project.cardata.Controller;


import com.project.cardata.Mapper.CarDataMapper;
import com.project.cardata.bean.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class TestController {

    @Autowired
    CarDataMapper carDataMapper;

    @ResponseBody
    @GetMapping("/test/{user_id}")
    public Car getCar(@PathVariable("user_id") Integer user_id){
       System.out.println("userid:"+carDataMapper.getCar(user_id).getCar_id());
        return carDataMapper.getCar(user_id);
    }


    @ResponseBody
    @GetMapping("/test")
    public Car insertCar(Car car) {
        carDataMapper.InsertCar(car);

          System.out.println("userid:"+car.getCar_id());

        return car;
    }

//    @RequestMapping("/test3")
//    public String test2(Model model){
//      student student = new student();
//      student.setAge(1);
//      student.setName("CJS");
//        model.addAttribute(student);
//        return "Test";
//    }

}
