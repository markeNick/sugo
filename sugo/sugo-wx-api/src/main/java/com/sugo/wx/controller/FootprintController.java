package com.sugo.wx.controller;


import com.sugo.common.util.JacksonUtil;
import com.sugo.common.util.ResponseUtil;
import com.sugo.sql.entity.SugoFootprint;
import com.sugo.sql.entity.SugoGoods;
import com.sugo.sql.service.goods.GoodsService;
import com.sugo.sql.service.user.FootprintService;
import com.sugo.wx.annotation.LoginUser;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/wx/footprint")
@Validated
public class FootprintController {
    private final Log logger = LogFactory.getLog(FootprintController.class);

    @Autowired
    private FootprintService footprintService;
    @Autowired
    private GoodsService goodsService;

    /**
     * 删除足迹
     * @param userId
     * @param body  请求内容， { id: xxx }
     * @return
     */
    @PostMapping("delete")
    public Object delete(@LoginUser Integer userId, @RequestBody String body) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }

        if (body == null) {
            return ResponseUtil.badArgument();
        }

        Integer footprintId = JacksonUtil.parseInteger(body, "id");
        if (footprintId == null) {
            return ResponseUtil.badArgumentValue();
        }

        SugoFootprint footprint = footprintService.findById(userId, footprintId);
        if (footprint == null) {
            return ResponseUtil.badArgumentValue();
        }

        if(!footprint.getUserId().equals(userId)) {
            return ResponseUtil.badArgumentValue();
        }

        footprintService.deleteById(footprintId);

        return ResponseUtil.ok();
    }

    /**
     * 用户足迹列表
     *
     * @param userId
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("list")
    public Object list(@LoginUser Integer userId,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }

        List<SugoFootprint> footprintList = footprintService.queryByAddTime(userId, page, limit);
        List<Object> footprints = new ArrayList<>(footprintList.size());

        for (SugoFootprint footprint : footprintList) {
            Map<String, Object> f = new HashMap<>();
            f.put("id", footprint.getId());
            f.put("goodsId", footprint.getGoodsId());
            f.put("addTime", footprint.getAddTime());

            SugoGoods goods = goodsService.findById(footprint.getGoodsId());
            f.put("name", goods.getName());
            f.put("brief", goods.getBrief());
            f.put("picUrl", goods.getPicUrl());
            f.put("retailPrice", goods.getRetailPrice());

            footprints.add(f);
        }

        return ResponseUtil.okList(footprints, footprintList);
    }
}



















