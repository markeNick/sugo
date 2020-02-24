package com.sugo.sql.service;

import com.github.pagehelper.PageHelper;
import com.sugo.sql.dao.SugoCouponUserMapper;
import com.sugo.sql.entity.SugoCouponUser;
import com.sugo.sql.entity.SugoCouponUserExample;
import com.sugo.sql.util.CouponUserConstant;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CouponUserService {
    @Resource
    private SugoCouponUserMapper couponUserMapper;

    /**
     * 统计某一id的优惠券数量
     * @param couponId
     * @return
     */
    public Integer countCoupon(Integer couponId) {
        SugoCouponUserExample example = new SugoCouponUserExample();
        example.or().andCouponIdEqualTo(couponId).andDeletedEqualTo(false);
        return (int)couponUserMapper.countByExample(example);
    }

    /**
     * 统计某一用户的某一id的优惠券数量
     * @param userId
     * @param couponId
     * @return
     */
    public Integer countUserAndCoupon(Integer userId, Integer couponId) {
        SugoCouponUserExample example = new SugoCouponUserExample();
        example.or().andUserIdEqualTo(userId).andCouponIdEqualTo(couponId).andDeletedEqualTo(false);
        return (int)couponUserMapper.countByExample(example);
    }

    /**
     * 发放优惠卷给用户
     * @param couponUser
     */
    public void add(SugoCouponUser couponUser) {
        couponUser.setAddTime(LocalDateTime.now());
        couponUser.setUpdateTime(LocalDateTime.now());
        couponUserMapper.insertSelective(couponUser);
    }

    public List<SugoCouponUser> queryList(Integer userId, Integer couponId, Short status, Integer page, Integer size, String sort, String order) {
        SugoCouponUserExample example = new SugoCouponUserExample();
        SugoCouponUserExample.Criteria criteria = example.createCriteria();
        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }
        if(couponId != null){
            criteria.andCouponIdEqualTo(couponId);
        }
        if (status != null) {
            criteria.andStatusEqualTo(status);
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        if (!StringUtils.isEmpty(page) && !StringUtils.isEmpty(size)) {
            PageHelper.startPage(page, size);
        }

        return couponUserMapper.selectByExample(example);
    }

    public List<SugoCouponUser> queryAll(Integer userId, Integer couponId) {
        return queryList(userId, couponId, CouponUserConstant.STATUS_USABLE, null, null, "add_time", "desc");
    }

    /**
     * 查询用户所有优惠券
     * @param userId
     * @return
     */
    public List<SugoCouponUser> queryAll(Integer userId) {
        return queryList(userId, null, CouponUserConstant.STATUS_USABLE, null, null, "add_time", "desc");
    }

    /**
     * 查询用户的某一id的优惠券
     * @param userId
     * @param couponId
     * @return
     */
    public SugoCouponUser queryOne(Integer userId, Integer couponId) {
        List<SugoCouponUser> couponUserList = queryList(userId, couponId, CouponUserConstant.STATUS_USABLE, 1, 1, "add_time", "desc");
        if(couponUserList.size() == 0){
            return null;
        }
        return couponUserList.get(0);
    }

    /**
     * 通过id查找用户拥有优惠券
     * @param id
     * @return
     */
    public SugoCouponUser findById(Integer id) {
        return couponUserMapper.selectByPrimaryKey(id);
    }


    public int update(SugoCouponUser couponUser) {
        couponUser.setUpdateTime(LocalDateTime.now());
        return couponUserMapper.updateByPrimaryKeySelective(couponUser);
    }

    public List<SugoCouponUser> queryExpired() {
        SugoCouponUserExample example = new SugoCouponUserExample();
        example.or().andStatusEqualTo(CouponUserConstant.STATUS_USABLE).andEndTimeLessThan(LocalDateTime.now()).andDeletedEqualTo(false);
        return couponUserMapper.selectByExample(example);
    }
}