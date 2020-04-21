package com.sugo.sql.service;

import com.github.pagehelper.PageHelper;
import com.sugo.sql.dao.OrderMapper;
import com.sugo.sql.dao.SugoOrderMapper;
import com.sugo.sql.entity.SugoOrder;
import com.sugo.sql.entity.SugoOrderExample;
import com.sugo.sql.util.MyRandom;
import com.sugo.sql.util.OrderUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class OrderService {
    @Resource
    private SugoOrderMapper sugoOrderMapper;
    @Resource
    private OrderMapper orderMapper;

    /**
     * 添加订单
     * @param order
     * @return
     */
    public int add(SugoOrder order) {
        order.setAddTime(LocalDateTime.now());
        order.setUpdateTime(LocalDateTime.now());
        return sugoOrderMapper.insertSelective(order);
    }

    /**
     * 根据userID统计用户的未删除订单数量
     * @param uid
     * @return
     */
    public int count(Integer uid) {
        SugoOrderExample example = new SugoOrderExample();
        example.or().andUserIdEqualTo(uid).andDeletedEqualTo(false);
        return (int) sugoOrderMapper.countByExample(example);
    }

    /**
     * 根据订单编号查询订单信息
     * @param orderId
     * @return
     */
    public SugoOrder findById(Integer orderId) {
        return sugoOrderMapper.selectByPrimaryKey(orderId);
    }

    /**
     * 根据订单编号和用户id查询订单信息
     * @param uid
     * @param orderId
     * @return
     */
    public SugoOrder findById(Integer uid, Integer orderId) {
        SugoOrderExample example = new SugoOrderExample();
        example.or().andIdEqualTo(orderId).andUserIdEqualTo(uid).andDeletedEqualTo(false);
        return sugoOrderMapper.selectOneByExample(example);
    }

    /**
     * 获取随机数
     * @param num
     * @return
     */
//    private String getRandomNum(Integer num) {
//        return MyRandom.getRandomNum(num);
//    }

    /**
     * 根据userId和订单编号统计用户未删除订单数量
     * @param uid
     * @param orderSn
     * @return
     */
    public int countByOrderSn(Integer uid, String orderSn) {
        SugoOrderExample example = new SugoOrderExample();
        example.or().andUserIdEqualTo(uid).andOrderSnEqualTo(orderSn).andDeletedEqualTo(false);
        return (int) sugoOrderMapper.countByExample(example);
    }

    // 生成一个唯一的订单，这里存在订单编号相同的可能性
    public String generateOrderSn(Integer userId) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMMdd");
        String now = df.format(LocalDate.now());
        String orderSn = now + MyRandom.getRandomNum(6);
        while (countByOrderSn(userId, orderSn) != 0) {
            orderSn = now + MyRandom.getRandomNum(6);
        }
        return orderSn;
    }

    /**
     * 分页查询查询用户未删除订单状态
     * @param userId
     * @param orderStatus
     * @param page
     * @param limit
     * @param sort
     * @param order
     * @return
     */
    public List<SugoOrder> queryByOrderStatus(Integer userId, List<Short> orderStatus, Integer page, Integer limit, String sort, String order) {
        SugoOrderExample example = new SugoOrderExample();
        example.setOrderByClause(SugoOrder.Column.addTime.desc());
        SugoOrderExample.Criteria criteria = example.or();
        criteria.andUserIdEqualTo(userId);
        if (orderStatus != null) {
            criteria.andOrderStatusIn(orderStatus);
        }
        criteria.andDeletedEqualTo(false);
        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, limit);
        return sugoOrderMapper.selectByExample(example);
    }

    public List<SugoOrder> querySelective(Integer userId, String orderSn, LocalDateTime start, LocalDateTime end, List<Short> orderStatusArray, Integer page, Integer limit, String sort, String order) {
        SugoOrderExample example = new SugoOrderExample();
        SugoOrderExample.Criteria criteria = example.createCriteria();

        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (!StringUtils.isEmpty(orderSn)) {
            criteria.andOrderSnEqualTo(orderSn);
        }
        if(start != null){
            criteria.andAddTimeGreaterThanOrEqualTo(start);
        }
        if(end != null){
            criteria.andAddTimeLessThanOrEqualTo(end);
        }
        if (orderStatusArray != null && orderStatusArray.size() != 0) {
            criteria.andOrderStatusIn(orderStatusArray);
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, limit);
        return sugoOrderMapper.selectByExample(example);
    }

    public int updateWithOptimisticLocker(SugoOrder order) {
        LocalDateTime preUpdateTime = order.getUpdateTime();
        order.setUpdateTime(LocalDateTime.now());

        return orderMapper.updateWithOptimisticLocker(preUpdateTime, order);
    }

    /**
     * 删除订单
     * @param id
     */
    public void deleteById(Integer id) {
        sugoOrderMapper.logicalDeleteByPrimaryKey(id);
    }

    /**
     * 统计未删除订单
     * @return
     */
    public int count() {
        SugoOrderExample example = new SugoOrderExample();
        example.or().andDeletedEqualTo(false);
        return (int) sugoOrderMapper.countByExample(example);
    }

    /**
     * 查询未付款订单
     * @param minutes
     * @return
     */
    public List<SugoOrder> queryUnpaid(int minutes) {
        SugoOrderExample example = new SugoOrderExample();
        example.or().andOrderStatusEqualTo(OrderUtil.STATUS_CREATE).andDeletedEqualTo(false);
        return sugoOrderMapper.selectByExample(example);
    }

    /**
     * 查询未确认订单
     * @param days
     * @return
     */
    public List<SugoOrder> queryUnconfirm(int days) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expired = now.minusDays(days);
        SugoOrderExample example = new SugoOrderExample();
        example.or().andOrderStatusEqualTo(OrderUtil.STATUS_SHIP).andShipTimeLessThan(expired).andDeletedEqualTo(false);
        return sugoOrderMapper.selectByExample(example);
    }

    /**
     * 通过订单编号查询订单信息
     * @param orderSn
     * @return
     */
    public SugoOrder findBySn(String orderSn) {
        SugoOrderExample example = new SugoOrderExample();
        example.or().andOrderSnEqualTo(orderSn).andDeletedEqualTo(false);
        return sugoOrderMapper.selectOneByExample(example);
    }

    /**
     * 查询用户的订单信息
     * @param userId
     * @return
     */
    public Map<Object, Object> orderInfo(Integer userId) {
        SugoOrderExample example = new SugoOrderExample();
        example.or().andUserIdEqualTo(userId).andDeletedEqualTo(false);
        List<SugoOrder> orders = sugoOrderMapper.selectByExampleSelective(example, SugoOrder.Column.orderStatus, SugoOrder.Column.comments);

        int unpaid = 0;
        int unship = 0;
        int unrecv = 0;
        int uncomment = 0;
        for (SugoOrder order : orders) {
            if (OrderUtil.isCreateStatus(order)) {
                unpaid++;
            } else if (OrderUtil.isPayStatus(order)) {
                unship++;
            } else if (OrderUtil.isShipStatus(order)) {
                unrecv++;
            } else if (OrderUtil.isConfirmStatus(order) || OrderUtil.isAutoConfirmStatus(order)) {
                uncomment += order.getComments();
            } else {
                // do nothing
            }
        }

        Map<Object, Object> orderInfo = new HashMap<Object, Object>();
        orderInfo.put("unpaid", unpaid);
        orderInfo.put("unship", unship);
        orderInfo.put("unrecv", unrecv);
        orderInfo.put("uncomment", uncomment);
        return orderInfo;

    }

    /**
     * 查询订单评论
     * @param days
     * @return
     */
    public List<SugoOrder> queryComment(int days) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expired = now.minusDays(days);
        SugoOrderExample example = new SugoOrderExample();
        example.or().andCommentsGreaterThan((short) 0).andConfirmTimeLessThan(expired).andDeletedEqualTo(false);
        return sugoOrderMapper.selectByExample(example);
    }

    /**
     * 更新订单售后状态
     * @param orderId
     * @param statusReject
     */
    public void updateAftersaleStatus(Integer orderId, Short statusReject) {
        SugoOrder order = new SugoOrder();
        order.setId(orderId);
        order.setAftersaleStatus(statusReject);
        order.setUpdateTime(LocalDateTime.now());
        sugoOrderMapper.updateByPrimaryKeySelective(order);
    }

    /**
     * 修改订单支付状态
     */
    public int updateOrderPayStatus(Integer id, Short orderStatus, LocalDateTime updateTime) {


        return orderMapper.updateOrderPayStatus(id, orderStatus, updateTime);
    }
}