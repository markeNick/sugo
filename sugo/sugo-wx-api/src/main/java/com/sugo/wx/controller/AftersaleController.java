package com.sugo.wx.controller;

import com.sugo.common.annotator.Order;
import com.sugo.common.annotator.Sort;
import com.sugo.common.util.ResponseUtil;
import com.sugo.sql.entity.SugoAftersale;
import com.sugo.sql.entity.SugoOrder;
import com.sugo.sql.entity.SugoOrderGoods;
import com.sugo.sql.service.order.OrderGoodsService;
import com.sugo.sql.service.order.OrderService;
import com.sugo.sql.service.user.AftersaleService;
import com.sugo.sql.util.AftersaleConstant;
import com.sugo.sql.util.OrderUtil;
import com.sugo.wx.annotation.LoginUser;
import com.sugo.wx.util.WxResponseCode;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/wx/aftersale")
@Validated
public class AftersaleController {
    private final Log logger = LogFactory.getLog(AftersaleController.class);

    @Autowired
    private AftersaleService aftersaleService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderGoodsService orderGoodsService;

    /**
     * 售后列表
     *
     * @param userId
     * @param status
     * @param page
     * @param limit
     * @param sort
     * @param order
     * @return
     */
    @GetMapping("list")
    public Object list(@LoginUser Integer userId,
                       @RequestParam Short status,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order) {

        if (userId == null) {
            return ResponseUtil.unlogin();
        }

        List<SugoAftersale> aftersaleList = aftersaleService.queryList(userId, status, page, limit, sort, order);

        List<Map<String, Object>> aftersaleVoList = new ArrayList<>(aftersaleList.size());
        for (SugoAftersale aftersale : aftersaleList) {
            List<SugoOrderGoods> orderGoodsList = orderGoodsService.queryByOid(aftersale.getOrderId());

            Map<String, Object> aftersaleVo = new HashMap<>();
            aftersaleVo.put("aftersale", aftersale);
            aftersaleVo.put("goodsList", orderGoodsList);

            aftersaleVoList.add(aftersaleVo);
        }

        return ResponseUtil.okList(aftersaleVoList, aftersaleList);
    }

    /**
     * 售后详情
     *
     * @param userId
     * @param orderId
     * @return
     */
    @GetMapping("detail")
    public Object detail(@LoginUser Integer userId, @NotNull Integer orderId) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }

        SugoOrder order = orderService.findById(userId, orderId);
        if (order == null) {
            return ResponseUtil.badArgumentValue();
        }

        List<SugoOrderGoods> orderGoodsList = orderGoodsService.queryByOid(orderId);
        SugoAftersale aftersale = aftersaleService.findByOrderId(userId, orderId);

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("aftersale", aftersale);
        data.put("order", order);
        data.put("orderGoods", orderGoodsList);
        return ResponseUtil.ok(data);
    }

    /**
     * 申请售后
     *
     * @param userId
     * @param aftersale
     * @return
     */
    @PostMapping("submit")
    public Object submit(@LoginUser Integer userId, @RequestBody SugoAftersale aftersale) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }

        Object error = validate(aftersale);
        if (error != null) {
            return error;
        }
        // 进一步验证
        Integer orderId = aftersale.getOrderId();
        if(orderId == null){
            return ResponseUtil.badArgument();
        }

        SugoOrder order = orderService.findById(userId, orderId);
        if (order == null) {
            return ResponseUtil.badArgumentValue();
        }
        // 订单必须完成才能进入售后流程。
        if(!OrderUtil.isConfirmStatus(order) && !OrderUtil.isAutoConfirmStatus(order)){
            return ResponseUtil.fail(WxResponseCode.AFTERSALE_UNALLOWED, "不能申请售后");
        }

        BigDecimal amount = order.getActualPrice().subtract(order.getFreightPrice());
        if(aftersale.getAmount().compareTo(amount) > 0){
            return ResponseUtil.fail(WxResponseCode.AFTERSALE_INVALID_AMOUNT, "退款金额不正确");
        }

        Short afterStatus = order.getAftersaleStatus();
        if(afterStatus.equals(AftersaleConstant.STATUS_RECEPT) || afterStatus.equals(AftersaleConstant.STATUS_REFUND)){
            return ResponseUtil.fail(WxResponseCode.AFTERSALE_INVALID_AMOUNT, "已申请售后");
        }

        // 如果有旧的售后记录则删除（例如用户已取消，管理员拒绝）
        aftersaleService.deleteByOrderId(userId, orderId);

        aftersale.setStatus(AftersaleConstant.STATUS_REQUEST);
        aftersale.setAftersaleSn(aftersaleService.generateAftersaleSn(userId));
        aftersale.setUserId(userId);
        aftersaleService.add(aftersale);

        // 订单的aftersale_status和售后记录的status是一致的。
        orderService.updateAftersaleStatus(orderId, AftersaleConstant.STATUS_REQUEST);
        return ResponseUtil.ok();
    }

    private Object validate(SugoAftersale aftersale) {
        Short type = aftersale.getType();
        if (type == null) {
            return ResponseUtil.badArgument();
        }
        BigDecimal amount = aftersale.getAmount();
        if (amount == null) {
            return ResponseUtil.badArgument();
        }
        String reason = aftersale.getReason();
        if (StringUtils.isEmpty(reason)) {
            return ResponseUtil.badArgument();
        }
        return null;
    }

    /**
     * 取消售后
     *
     * @param userId
     * @param aftersale
     * @return
     */
    @PostMapping("cancel")
    public Object cancel(@LoginUser Integer userId, @RequestBody SugoAftersale aftersale) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        Integer id = aftersale.getId();
        if(id == null){
            return ResponseUtil.badArgument();
        }
        SugoAftersale aftersaleOne = aftersaleService.findById(userId, id);
        if(aftersaleOne == null){
            return ResponseUtil.badArgument();
        }

        Integer orderId = aftersaleOne.getOrderId();
        SugoOrder order = orderService.findById(userId, orderId);
        if(!order.getUserId().equals(userId)){
            return ResponseUtil.badArgumentValue();
        }

        // 订单必须完成才能进入售后流程。
        if(!OrderUtil.isConfirmStatus(order) && !OrderUtil.isAutoConfirmStatus(order)){
            return ResponseUtil.fail(WxResponseCode.AFTERSALE_UNALLOWED, "不支持售后");
        }
        Short afterStatus = order.getAftersaleStatus();
        if(!afterStatus.equals(AftersaleConstant.STATUS_REQUEST)){
            return ResponseUtil.fail(WxResponseCode.AFTERSALE_INVALID_STATUS, "不能取消售后");
        }

        aftersale.setStatus(AftersaleConstant.STATUS_CANCEL);
        aftersale.setUserId(userId);
        aftersaleService.updateById(aftersale);

        // 订单的aftersale_status和售后记录的status是一致的。
        orderService.updateAftersaleStatus(orderId, AftersaleConstant.STATUS_CANCEL);
        return ResponseUtil.ok();
    }
}

























