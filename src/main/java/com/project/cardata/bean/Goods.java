package com.project.cardata.bean;

import org.springframework.stereotype.Component;

@Component
public class Goods {
    private Integer goods_id;
    private String  goods_name;
    private Integer goods_type;
    private Integer goods_price;
    private Integer goods_num;
    private Integer goods_sale;
    private String goods_pic;
    private Integer assistant_id;
    private String goods_des;

    public Integer getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(Integer goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public Integer getGoods_type() {
        return goods_type;
    }

    public void setGoods_type(Integer goods_type) {
        this.goods_type = goods_type;
    }

    public Integer getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(Integer goods_price) {
        this.goods_price = goods_price;
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

    public String getGoods_pic() {
        return goods_pic;
    }

    public void setGoods_pic(String goods_pic) {
        this.goods_pic = goods_pic;
    }

    public Integer getAssistant_id() {
        return assistant_id;
    }

    public void setAssistant_id(Integer assistant_id) {
        this.assistant_id = assistant_id;
    }

    public String getGoods_des() {
        return goods_des;
    }

    public void setGoods_des(String goods_des) {
        this.goods_des = goods_des;
    }
}
