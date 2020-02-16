package com.project.cardata.Controller;


import com.project.cardata.Mapper.DataFromCarMapper;
import com.project.cardata.Mapper.GoodsInformationMapper;
import com.project.cardata.Mapper.OrderFinalMapper;
import com.project.cardata.bean.ConfirmOrderTools;
import com.project.cardata.bean.DataFromCar;
import com.project.cardata.bean.GoodsInformation;
import com.project.cardata.bean.OrderFinal;
import com.project.cardata.server.NetTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;
import java.util.List;

@RestController
public class DataFromCarController {

    @Autowired
    OrderFinalMapper orderfinalmapper;
    @Autowired
    DataFromCarMapper datafromcarmapper;
    @Autowired
    GoodsInformationMapper goodsinformationmapper;

    DataFromCar datafromcar=new DataFromCar();
    ConfirmOrderTools confirmeordertools=new ConfirmOrderTools();
    GoodsInformation goodsinformation=new GoodsInformation();
    List<DataFromCar> datafromcarlist;
    Iterator<DataFromCar> datafromcariterator;
    Integer sum;
    Integer discountvalue=60;

    private Integer countSum(Integer discount){
        datafromcarlist=datafromcarmapper.getInitialOrderList();
        datafromcariterator=datafromcarlist.iterator();
        while(datafromcariterator.hasNext()){
            datafromcar=datafromcariterator.next();
            goodsinformation.setGoods_id(datafromcar.getGoods_id());
            System.out.println("1: "+goodsinformation);
            try {
                if(goodsinformationmapper.getInformationByGoods_id(goodsinformation)!=null){
                    goodsinformation=goodsinformationmapper.getInformationByGoods_id(goodsinformation);
                }
            }
            catch (NullPointerException e){
                System.out.println("没有更多商品");
                break;
            }

            sum=sum+goodsinformation.getGoods_num()*goodsinformation.getGoods_price();
            System.out.println(datafromcar.getGoods_id()+"  "+goodsinformation.getGoods_num()+"  "+sum);
        }
        if(discount==1){
            sum=sum-discountvalue;
            System.out.println(datafromcar.getGoods_id()+"  "+goodsinformation.getGoods_num()+""+sum);
        }
        return sum;

    }

    //订单确认页面
    @GetMapping("/confirmorder/{user_id}")
    public void confirmOrderPage(){}

    @GetMapping("/confirmorder/{user_id}/{operation}/{goods_id}/{discount}")//operation为操作码：0为减1为加  discount为优惠券码：0为不使用1为使用
    public void confirmOrder(@PathVariable("operation") Integer operation,@PathVariable("user_id") Integer user_id,@PathVariable("goods_id") Integer goods_id,@PathVariable("discount") Integer discount){

        confirmeordertools.setUser_id(user_id);
        confirmeordertools.setGoods_id(goods_id);
        goodsinformation.setGoods_id(goods_id);
        sum=0;
        datafromcar=datafromcarmapper.getSumByConfirmOrderTools(confirmeordertools);
        if(operation==1){
            goodsinformation= goodsinformationmapper.getInformationByGoods_id(goodsinformation);
            //判断库存
            if(goodsinformation.getGoods_num()>0){
                goodsinformation.setGoods_num(goodsinformation.getGoods_num()-1);
                goodsinformationmapper.updateRepertoryByGoods_id(goodsinformation);

                confirmeordertools.setSum(datafromcar.getSum()+1);
                datafromcarmapper.updateSumByConfirmOrderTools(confirmeordertools);
                //计算金额
                sum=countSum(discount);
            }
        }
        if(operation==0){
            goodsinformation= goodsinformationmapper.getInformationByGoods_id(goodsinformation);
            goodsinformation.setGoods_num(goodsinformation.getGoods_num()+1);
            goodsinformationmapper.updateRepertoryByGoods_id(goodsinformation);
            confirmeordertools.setSum(datafromcar.getSum()-1);
            datafromcarmapper.updateSumByConfirmOrderTools(confirmeordertools);
            sum=countSum(discount);
        }
     }

     //点击确认订单传入数据库并在数据库生成订单数据
    @GetMapping("/order")
    public String createOrderFinal(){
        OrderFinal orderfinal=new OrderFinal();

        StringBuilder goods_id=new StringBuilder();
        NetTime nettime=new NetTime();


        datafromcarlist=datafromcarmapper.getInitialOrderList();
        datafromcarmapper.updateOrderData();

        datafromcariterator=datafromcarlist.iterator();
        while(datafromcariterator.hasNext()){
            //得到orderfinal中的goods_id
            datafromcar=datafromcariterator.next();
            goods_id=goods_id.append("#"+datafromcar.getGoods_id()+"/"+datafromcar.getSum());
        }
        orderfinal.setGoods_id(goods_id.toString());
        orderfinal.setUser_id(datafromcar.getUser_id());
        orderfinal.setDate(nettime.getNetTime());
        orderfinal.setState(0);
        orderfinal.setSum(sum);
        orderfinalmapper.addOrderFinal(orderfinal);
        System.out.println("1:"+goods_id.toString());
        System.out.println("2:"+nettime.getNetTime());
        System.out.println("3:"+sum);

        try {
            Thread.sleep(150000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        orderfinal.setState(1);
//        orderfinalmapper.updateStateByDateAndUser_id(orderfinal);
        //检查是否支付更改状态码并更新销量-1为超时未付，-2为取消订单
        orderfinal=orderfinalmapper.getOrderFinalByDateAndUser_id(orderfinal);
        if(orderfinal.getState()==0){
            System.out.println("用户超时未付");
            orderfinal.setState(-1);
            orderfinalmapper.updateStateByDateAndUser_id(orderfinal);
            //orderfinalmapper.deleteOrderFinalByDateAndUser_id(orderfinal);
        }
        else{
            if(orderfinal.getState()==1){
                System.out.println("系统支付状态自动检查：用户已支付订单");
                datafromcariterator=datafromcarlist.iterator();
                while(datafromcariterator.hasNext()){
                    //得到orderfinal中的goods_id
                    datafromcar=datafromcariterator.next();
                    goodsinformation.setGoods_id(datafromcar.getGoods_id());
                    goodsinformation.setGoods_sale(goodsinformationmapper.getInformationByGoods_id(goodsinformation).getGoods_sale()+datafromcar.getSum());
                    goodsinformationmapper.updateSaleByGoods_id(goodsinformation);

                    System.out.println(datafromcar.getGoods_id()+": "+goodsinformationmapper.getInformationByGoods_id(goodsinformation).getGoods_sale());


                }

            }
            else if(orderfinal.getState()==-2){
                System.out.println("用户已取消订单");
            }

        }
        return "/alipay";


    }

}
