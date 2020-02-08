package com.project.cardata.bean;

public class goods_real {
    private Integer goods_rel_id;
    private Integer car_id;
    private Integer goods_id;
    private Integer quantity;

    public Integer getGoods_real_id() {
        return goods_rel_id;
    }

    public Integer getCar_id() {
        return car_id;
    }

    public Integer getGoods_id() {
        return goods_id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setGoods_real_id(Integer goods_real_id) {
        this.goods_rel_id = goods_real_id;
    }

    public void setCar_id(Integer car_id) {
        this.car_id = car_id;
    }

    public void setGoods_id(Integer goods_id) {
        this.goods_id = goods_id;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
