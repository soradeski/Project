package com.project.cardata.bean;

import org.springframework.stereotype.Component;

@Component
public class GoodsInformation {
    private Integer goods_id;
    private Integer goods_num;
    private Integer goods_sale;
    private Integer goods_price;

    public Integer getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(Integer goods_id) {
        this.goods_id = goods_id;
    }

    public Integer getGoods_num() {
        return goods_num;
    }

    public void setGoods_num(Integer goods_num) {
        this.goods_num = goods_num;
    }

    public Integer getGoods_sale() {
        return goods_sale;
    }

    public void setGoods_sale(Integer goods_sale) {
        this.goods_sale = goods_sale;
    }

    public Integer getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(Integer goods_price) {
        this.goods_price = goods_price;
    }
}
