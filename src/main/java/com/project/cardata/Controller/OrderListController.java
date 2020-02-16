package com.project.cardata.Controller;

import com.alibaba.fastjson.JSONObject;

import com.project.cardata.Mapper.OrderFinalMapper;
import com.project.cardata.bean.OrderFinal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

@RestController
public class OrderListController {
   @Autowired
   OrderFinalMapper orderfinalmapper;

    Object jsonobject;
    OrderFinal orderfinal=new OrderFinal();
    ArrayList<OrderFinal> orderlist=new ArrayList<OrderFinal>();

    Comparator comparator= new Comparator<OrderFinal>() {
        @Override
        public int compare(OrderFinal o1, OrderFinal o2) {
            if (((o1 != null) && (o2 != null)) && (( o1.getDate().compareTo(o2.getDate()) )>0))
                return -1;
            else
                return 1;
        }
    };


    @GetMapping("/myorders/{user_id}")
    public Object myOrderPage(@PathVariable("user_id") Integer user_id){
        orderfinal.setUser_id(user_id);
        orderlist=orderfinalmapper.getOrderFinalByUser_id(orderfinal);
        orderlist.sort(comparator);
        Iterator<OrderFinal> orderfinaliterator=orderlist.iterator();
        while (orderfinaliterator.hasNext()){
            orderfinal=orderfinaliterator.next();
            System.out.println(orderfinal.getDate());
        }
        System.out.println(orderlist);
        jsonobject = JSONObject.toJSON(orderlist);
        return jsonobject;

    }

    @GetMapping("/myorders/{user_id}/{order_id}/del")
    public Object cancelOrder(@PathVariable("user_id") Integer user_id,@PathVariable("order_id") Integer order_id){
        orderfinal.setUser_id(user_id);
        orderfinal.setState(-2);
        orderfinalmapper.updateStateByOrder_id(orderfinal);
        jsonobject=JSONObject.toJSON("订单取消成功");
        return jsonobject;



    }
}
