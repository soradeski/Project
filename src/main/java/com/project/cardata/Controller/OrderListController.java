package com.project.cardata.Controller;

import com.alibaba.fastjson.JSONObject;

import com.project.cardata.Mapper.GoodsInformationMapper;
import com.project.cardata.Mapper.OrderFinalMapper;
import com.project.cardata.bean.GoodsInformation;
import com.project.cardata.bean.OrderFinal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

@RestController
public class OrderListController {
    @Autowired
    OrderFinalMapper orderfinalmapper;
    @Autowired
    GoodsInformationMapper goodsinformationmapper;

    Object jsonobject;
    OrderFinal orderfinal=new OrderFinal();
    GoodsInformation goodsinformation=new GoodsInformation();
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


    @RequestMapping("/myorders/{user_id}")
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

    @RequestMapping("/myorders/{user_id}/{order_id}/cancel")
    public Object cancelOrder(@PathVariable("user_id") Integer user_id,@PathVariable("order_id") Integer order_id){
        String[] singlidandnum;
        String[] allidandnum;
        String goods_id;
        char[] charnums;
        int[] intnums=new int[100];//商品id最大长度
        int i;
        int realnum=0;
        int checknum=0;

        orderfinal.setUser_id(user_id);
        orderfinal.setState(-2);
        orderfinal.setOrder_id(order_id);
        orderfinalmapper.updateStateByOrder_id(orderfinal);
        //还原库存
        orderfinal=orderfinalmapper.getOrderFinalByOrder_id(orderfinal);
        goods_id=orderfinal.getGoods_id();
        allidandnum=goods_id.split("#");

        for (String allidandnumdiv:allidandnum)
        {
            singlidandnum=allidandnumdiv.split("/");
            for (String singlidandnumdiv:singlidandnum)
            {
                charnums=singlidandnumdiv.toCharArray();
                if (charnums.length>0){
                    i=0;
                    for (char charnum:charnums)
                    {   if((int) charnum>=48){}
                        intnums[i]=(int) charnum-48;
                        i++;
                    }
                    realnum=0;
                    for (int num=i-1;num>=0;num--) {
                        realnum=realnum+intnums[num]*(int) Math.pow(10,i-num-1);
                    }
                    if(checknum%2==0){
                        System.out.println("商品："+realnum);
                        goodsinformation.setGoods_id(realnum);
                    }
                    else{
                        System.out.println("数量："+realnum);
                        goodsinformation=goodsinformationmapper.getInformationByGoods_id(goodsinformation);
                        goodsinformation.setGoods_num(goodsinformation.getGoods_num()+realnum);
                        goodsinformationmapper.updateRepertoryByGoods_id(goodsinformation);
                    }
                    checknum++;

                }
                System.out.println("-------------------");
            }
        }

        jsonobject=JSONObject.toJSON("订单取消成功");
        return jsonobject;
    }

    @RequestMapping("/myorders/{user_id}/{order_id}/confirm")
    public Object confirmOrder(@PathVariable("user_id") Integer user_id, @PathVariable("order_id") Integer order_id) {
        orderfinal.setUser_id(user_id);
        orderfinal.setState(3);
        orderfinalmapper.updateStateByOrder_id(orderfinal);
        jsonobject = JSONObject.toJSON("确认收货成功");
        return jsonobject;
    }
}
