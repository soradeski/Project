package com.project.cardata.Controller;


import com.project.cardata.Mapper.SfMapper;
import com.project.cardata.bean.Sf;
import com.project.cardata.server.NetTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SfController {
    @Autowired
    SfMapper sfmapper;

    Sf sf=new Sf();
    NetTime nettime=new NetTime();
    StringBuilder stringbuilder=new StringBuilder();

    public void sfWait(){
        try {
            Thread.sleep(4000);
            stringbuilder=new StringBuilder();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/sf/{sf_id}")
    public void checkSf(@PathVariable("sf_id") Integer sf_id){
        sf.setSf_id(sf_id);
        stringbuilder.append(nettime.getNetTime().toString()+"  快件已揽收");
        sf.setSf_timeandlocation(stringbuilder.toString());
        sfmapper.updateTimeAndLocationBySf_id(sf);
        sfWait();
        stringbuilder.append(nettime.getNetTime().toString()+"  快件在上海浦东营业部已装车，准备发往上海虹桥中转场");
        sf.setSf_timeandlocation(stringbuilder.toString());
        sfmapper.updateTimeAndLocationBySf_id(sf);
        sfWait();
        stringbuilder.append(nettime.getNetTime().toString()+"  快件已到达上海虹桥中转场");
        sf.setSf_timeandlocation(stringbuilder.toString());
        sfmapper.updateTimeAndLocationBySf_id(sf);
        sfWait();
        stringbuilder.append(nettime.getNetTime().toString()+"  快件在上海虹桥中转场已装车，准备发往上海虹桥机场");
        sf.setSf_timeandlocation(stringbuilder.toString());
        sfmapper.updateTimeAndLocationBySf_id(sf);
        sfWait();
        stringbuilder.append(nettime.getNetTime().toString()+"  快件已到达上海虹桥机场，准备发往西安机场");
        sf.setSf_timeandlocation(stringbuilder.toString());
        sfmapper.updateTimeAndLocationBySf_id(sf);
        sfWait();
        stringbuilder.append(nettime.getNetTime().toString()+"  航班已起飞，飞往西安机场");
        sf.setSf_timeandlocation(stringbuilder.toString());
        sfmapper.updateTimeAndLocationBySf_id(sf);
        sfWait();
        stringbuilder.append(nettime.getNetTime().toString()+"  快件已到达西安机场，准备发往西安集散中心");
        sf.setSf_timeandlocation(stringbuilder.toString());
        sfmapper.updateTimeAndLocationBySf_id(sf);
        sfWait();
        stringbuilder.append(nettime.getNetTime().toString()+"  快件已到达西安集散中心已装车，准备发往西安书香路营业点");
        sf.setSf_timeandlocation(stringbuilder.toString());
        sfmapper.updateTimeAndLocationBySf_id(sf);
        sfWait();
        stringbuilder.append(nettime.getNetTime().toString()+"  快件已到达西安书香路营业点");
        sf.setSf_timeandlocation(stringbuilder.toString());
        sfmapper.updateTimeAndLocationBySf_id(sf);
        sfWait();
        stringbuilder.append(nettime.getNetTime().toString()+"  快件派送中");
        sf.setSf_timeandlocation(stringbuilder.toString());
        sfmapper.updateTimeAndLocationBySf_id(sf);
        sfWait();
        stringbuilder.append(nettime.getNetTime().toString()+"  快件已签收");
        sf.setSf_timeandlocation(stringbuilder.toString());
        sfmapper.updateTimeAndLocationBySf_id(sf);
    }
}
