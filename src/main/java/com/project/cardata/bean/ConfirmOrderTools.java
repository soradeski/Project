package com.project.cardata.bean;

import org.springframework.stereotype.Component;

@Component
public class ConfirmOrderTools {
    private Integer goods_id;//单个商品id
    private Integer user_id;
    private Integer sum;

    public Integer getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(Integer goods_id) {
        this.goods_id = goods_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

}
