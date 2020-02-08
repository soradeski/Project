package com.project.cardata.bean;

public class Orders {
    private Integer order_id;
    private Integer goods_id;
    private Integer user_id;
    private Integer user_addr_id;
    private Integer sum;            //个数

    public Integer getOrder_id() {
        return order_id;
    }

    public Integer getGoods_id() {
        return goods_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public Integer getUser_addr_id() {
        return user_addr_id;
    }

    public Integer getSum() {
        return sum;
    }

    public void setOrder_id(Integer order_id) {
        this.order_id = order_id;
    }

    public void setGoods_id(Integer goods_id) {
        this.goods_id = goods_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public void setUser_addr_id(Integer user_addr_id) {
        this.user_addr_id = user_addr_id;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }
}
