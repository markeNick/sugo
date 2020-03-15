package com.sugo.wx.controller;

import com.sugo.common.annotator.Order;
import com.sugo.common.annotator.Sort;
import com.sugo.common.util.JacksonUtil;
import com.sugo.common.util.ResponseUtil;
import com.sugo.sql.entity.SugoCollect;
import com.sugo.sql.entity.SugoGoods;
import com.sugo.sql.service.GoodsService;
import com.sugo.sql.service.CollectService;
import com.sugo.wx.annotation.LoginUser;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户收藏
 */
@RestController
@RequestMapping("/wx/collect")
@Validated
public class CollectController {
    private final Log logger = LogFactory.getLog(CollectController.class);

    @Autowired
    private CollectService collectService;
    @Autowired
    private GoodsService goodsService;

    /**
     * 用户收藏列表
     * @param userId
     * @param type
     * @param page
     * @param limit
     * @param sort
     * @param oreder
     * @return
     */
    @GetMapping("list")
    public Object list(@LoginUser Integer userId,
                       @NotNull Byte type,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                       @Order @RequestParam(defaultValue = "desc") String oreder) {

        if(userId == null) {
            return ResponseUtil.unlogin();
        }

        type = 0;   // 表示查询用户收藏的商品

        List<SugoCollect> collectList = collectService.queryByType(userId, type, page, limit, sort, oreder);

        List<Object> collects = new ArrayList<>(collectList.size());
        for (SugoCollect collect : collectList) {
            Map<String, Object> c = new HashMap<>();
            c.put("id", collect.getId());
            c.put("type", collect.getType());
            c.put("valueId", collect.getValueId());

            SugoGoods goods = goodsService.findById(collect.getValueId());

            c.put("name", goods.getName());
            c.put("brief", goods.getBrief());
            c.put("picUrl", goods.getPicUrl());
            c.put("retailPrice", goods.getRetailPrice());

            collects.add(c);
        }

        return ResponseUtil.okList(collects, collectList);
    }

    /**
     * 收藏或取消收藏
     *      如果没有收藏就是收藏，如果已经收藏就是删除
     * @param userId
     * @param body
     * @return
     */
    @PostMapping("addordelete")
    public Object addordelete(@LoginUser Integer userId, @RequestBody String body) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }

        Byte type = JacksonUtil.parseByte(body, "type");
        Integer valueId = JacksonUtil.parseInteger(body, "valueId");

        if(!ObjectUtils.allNotNull(type, valueId)) {
            return ResponseUtil.badArgument();
        }

        // valueId：商品ID，这里找出要删除的收藏
        SugoCollect collect = collectService.queryByTypeAndValue(userId, type, valueId);

        // 如果不为空则表示用户要删除，否则就是收藏
        if(collect != null) {
            collectService.deleteById(collect.getId());
        } else {
            collect = new SugoCollect();
            collect.setUserId(userId);
            collect.setValueId(valueId);
            collect.setType(type);
            collectService.add(collect);
        }

        return ResponseUtil.ok();
    }

}