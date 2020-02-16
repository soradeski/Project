package com.project.cardata.bean;

import org.springframework.stereotype.Component;

@Component
public class OrderFinal {
    private Integer order_id;
    private String goods_id;//所以商品id和相应数量拼起来的字符串
    private Integer user_id;
    private Integer user_addr_id;
    private Integer sum;
    private java.sql.Timestamp date;
    private Integer state;//订单状态码
    private Integer logistics;//物流状态码

    public Integer getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Integer order_id) {
        this.order_id = order_id;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getUser_addr_id() {
        return user_addr_id;
    }

    public void setUser_addr_id(Integer user_addr_id) {
        this.user_addr_id = user_addr_id;
    }

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    public java.sql.Timestamp getDate() {
        return date;
    }

    public void setDate(java.sql.Timestamp date) {
        this.date = date;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getLogistics() {
        return logistics;
    }

    public void setLogistics(Integer logistics) {
        this.logistics = logistics;
    }
}
