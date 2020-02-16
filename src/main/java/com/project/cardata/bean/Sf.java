package com.project.cardata.bean;

import org.springframework.stereotype.Component;

@Component
public class Sf {
    private Integer sf_id;
    private String sf_timeandlocation;

    public Integer getSf_id() {
        return sf_id;
    }

    public void setSf_id(Integer sf_id) {
        this.sf_id = sf_id;
    }

    public String getSf_timeandlocation() {
        return sf_timeandlocation;
    }

    public void setSf_timeandlocation(String sf_timeandlocation) {
        this.sf_timeandlocation = sf_timeandlocation;
    }
}
