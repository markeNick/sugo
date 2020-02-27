package com.sugo.wx.service;

import com.sugo.sql.entity.SugoRegion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.sugo.sql.service.RegionService;

import java.util.List;

@Component
public class GetRegionService {
    @Autowired
    private RegionService regionService;

    private static volatile List<SugoRegion> regionList;

    protected List<SugoRegion> getRegionList() {
        if(regionList == null) {
            createRegion();
        }

        return regionList;
    }

    /**
     * 获取regionList单例 —— 双重锁
     */
    private synchronized void createRegion() {
        if(regionList == null) {
            synchronized (GetRegionService.class) {
                if(regionList == null) {
                    regionList = regionService.getAll();
                }
            }
        }
    }


}