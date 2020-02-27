package com.sugo.sql.service;

import com.sugo.sql.entity.SugoCoupon;
import com.sugo.sql.entity.SugoCouponUser;
import com.sugo.sql.util.CouponConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class CouponVerifyService {

    @Autowired
    private CouponUserService couponUserService;
    @Autowired
    private CouponService couponService;

    /**
     * 检测优惠券是否可用
     * @param userId
     * @param couponId
     * @param userCouponId
     * @param checkedGoodsPrice
     * @return
     */
    public SugoCoupon checkCoupon(Integer userId, Integer couponId, Integer userCouponId, BigDecimal checkedGoodsPrice){
        // 检查优惠券是否存在
        SugoCoupon coupon = couponService.findById(couponId);
        if (coupon == null) {
            return null;
        }

        // 检查用户是否拥有该优惠券
        SugoCouponUser couponUser = couponUserService.findById(userCouponId);
        if(couponUser == null) {
            couponUser = couponUserService.queryOne(userId, couponId);
        } else if(!couponId.equals(couponUser.getCouponId())) {
            return null;
        }

        if(couponUser == null) {
            return null;
        }

        // 检查是否过期
        Short timeType = coupon.getTimeType();  // 过期规则，0：计算天数，1：计算起始时间和结束时间
        Short days = coupon.getDays();
        LocalDateTime now = LocalDateTime.now();

        // 如果是过期规则是：1：计算起止时间
        if(timeType.equals(CouponConstant.TIME_TYPE_TIME)) {
            if(now.isBefore(coupon.getStartTime()) || now.isAfter(coupon.getEndTime())) {
                return null;
            }
        } else if(timeType.equals(CouponConstant.TIME_TYPE_DAYS)) {
            LocalDateTime expire = couponUser.getAddTime().plusDays(days);
            if(now.isAfter(expire)) {
                return null;
            }
        } else {
            return null;
        }

        // 检查商品是否能使用优惠券
        // 目前支持全平台商品，可以根据需要修改参数
        Short goodType = coupon.getGoodsType();
        if(!goodType.equals(CouponConstant.GOODS_TYPE_ALL)) {
            return null;
        }

        // 检查订单状态
        Short status = coupon.getStatus();
        if(!status.equals(CouponConstant.STATUS_NORMAL)) {
            return null;
        }

        // 检查是否满足最低消费
        if(checkedGoodsPrice.compareTo(coupon.getMin()) == -1) {
            return null;
        }

        return coupon;
    }

}