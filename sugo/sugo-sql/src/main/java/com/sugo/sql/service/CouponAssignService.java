package com.sugo.sql.service;

import com.sugo.sql.entity.SugoCoupon;
import com.sugo.sql.entity.SugoCouponUser;
import com.sugo.sql.util.CouponConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CouponAssignService {
    @Autowired
    private CouponUserService couponUserService;

    @Autowired
    private CouponService couponService;

    /**
     * 新注册发放优惠券
     * @param userId
     */
    public void assignForRegister(Integer userId) {
        List<SugoCoupon> couponList = couponService.queryRegister();
        for (SugoCoupon coupon : couponList) {
            Integer couponId = coupon.getId();

            Integer count = couponUserService.countUserAndCoupon(userId, couponId);
            if(count > 0) {
                continue;
            }

            Short limit = coupon.getLimit();    // 限制领取的数量
            while (limit > 0) {
                SugoCouponUser couponUser = new SugoCouponUser();
                couponUser.setCouponId(couponId);
                couponUser.setUserId(userId);
                Short timeType = coupon.getTimeType();

                if(timeType.equals(CouponConstant.TIME_TYPE_DAYS)) {
                    LocalDateTime now = LocalDateTime.now();
                    couponUser.setStartTime(now);
                    couponUser.setEndTime(now.plusDays(coupon.getDays()));
                } else {
                    couponUser.setStartTime(coupon.getStartTime());
                    couponUser.setEndTime(coupon.getEndTime());
                }
                couponUserService.add(couponUser);
                limit--;
            }


        }

    }
}