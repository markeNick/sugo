package com.sugo.sql.service;

import com.github.pagehelper.PageHelper;
import com.sugo.sql.dao.SugoAftersaleMapper;
import com.sugo.sql.entity.SugoAftersale;
import com.sugo.sql.entity.SugoAftersaleExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

@Service
public class AftersaleService {
    @Resource
    private SugoAftersaleMapper aftersaleMapper;

    public SugoAftersale findById(Integer id) {
        return aftersaleMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据userId和订单编号id查询用户的非软删除售后订单
     * @param userId
     * @param id
     * @return
     */
    public SugoAftersale findById(Integer userId, Integer id) {
        SugoAftersaleExample example = new SugoAftersaleExample();
        example.or().andIdEqualTo(id).andUserIdEqualTo(userId).andDeletedEqualTo(false);
        return aftersaleMapper.selectOneByExample(example);
    }

    /**
     * 分页查询用户的售后订单，根据userId
     * @param userId
     * @param status    0是可申请，1是用户已申请，2是管理员审核通过，3是管理员退款成功，4是管理员审核拒绝，5是用户已取消
     * @param page
     * @param limit
     * @param sort
     * @param order
     * @return
     */
    public List<SugoAftersale> queryList(Integer userId, Short status, Integer page, Integer limit, String sort, String order) {
        SugoAftersaleExample example = new SugoAftersaleExample();
        SugoAftersaleExample.Criteria criteria = example.or();
        criteria.andUserIdEqualTo(userId);

        if(status != null) {
            criteria.andStatusEqualTo(status);
        }

        criteria.andDeletedEqualTo(false);

        if(!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        } else {
            example.setOrderByClause(SugoAftersale.Column.addTime.desc());
        }
        PageHelper.startPage(page, limit);
        return aftersaleMapper.selectByExample(example);
    }

    public List<SugoAftersale> querySelective(Integer orderId, String aftersaleSn, Short status, Integer page, Integer limit, String sort, String order) {
        SugoAftersaleExample example = new SugoAftersaleExample();
        SugoAftersaleExample.Criteria criteria = example.or();
        if (orderId != null) {
            criteria.andOrderIdEqualTo(orderId);
        }
        if (!StringUtils.isEmpty(aftersaleSn)) {
            criteria.andAftersaleSnEqualTo(aftersaleSn);
        }
        if (status != null) {
            criteria.andStatusEqualTo(status);
        }
        criteria.andDeletedEqualTo(false);
        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }
        else{
            example.setOrderByClause(SugoAftersale.Column.addTime.desc());
        }

        PageHelper.startPage(page, limit);
        return aftersaleMapper.selectByExample(example);
    }

    /**
     * 生成一个随机数
     * @param num
     * @return
     */
    private String getRandomNum(Integer num) {
        String base = "0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < num; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 根据userId查询该用户售后记录数
     * @param userId
     * @param aftersaleSn
     * @return
     */
    public int countByAftersaleSn(Integer userId, String aftersaleSn) {
        SugoAftersaleExample example = new SugoAftersaleExample();
        example.or().andUserIdEqualTo(userId).andAftersaleSnEqualTo(aftersaleSn).andDeletedEqualTo(false);
        return (int) aftersaleMapper.countByExample(example);
    }

    // 这里应该产生一个唯一的编号，但是实际上这里仍然存在两个售后编号相同的可能性
    public String generateAftersaleSn(Integer userId) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMMdd");
        String now = df.format(LocalDate.now());
        String aftersaleSn = now + getRandomNum(6);
        while (countByAftersaleSn(userId, aftersaleSn) != 0) {
            aftersaleSn = now + getRandomNum(6);
        }
        return aftersaleSn;
    }

    /**
     * 添加售后记录
     * @param aftersale
     */
    public void add(SugoAftersale aftersale) {
        aftersale.setAddTime(LocalDateTime.now());
        aftersale.setUpdateTime(LocalDateTime.now());
        aftersaleMapper.insertSelective(aftersale);
    }

    /**
     * 批量软删除售后订单
     * @param ids
     */
    public void deleteByIds(List<Integer> ids) {
        SugoAftersaleExample example = new SugoAftersaleExample();
        example.or().andIdIn(ids).andDeletedEqualTo(false);
        SugoAftersale aftersale = new SugoAftersale();
        aftersale.setUpdateTime(LocalDateTime.now());
        aftersale.setDeleted(true);
        aftersaleMapper.updateByExampleSelective(aftersale, example);
    }

    /**
     * 软删除订单
     * @param id
     */
    public void deleteById(Integer id) {
        aftersaleMapper.logicalDeleteByPrimaryKey(id);
    }

    /**
     * 通过订单编号软删除售后订单
     * @param userId
     * @param orderId
     */
    public void deleteByOrderId(Integer userId, Integer orderId) {
        SugoAftersaleExample example = new SugoAftersaleExample();
        example.or().andOrderIdEqualTo(orderId).andUserIdEqualTo(userId).andDeletedEqualTo(false);
        SugoAftersale aftersale = new SugoAftersale();
        aftersale.setUpdateTime(LocalDateTime.now());
        aftersale.setDeleted(true);
        aftersaleMapper.updateByExampleSelective(aftersale, example);
    }

    public void updateById(SugoAftersale aftersale) {
        aftersale.setUpdateTime(LocalDateTime.now());
        aftersaleMapper.updateByPrimaryKeySelective(aftersale);
    }

    /**
     * 根据订单编号查找售后订单
     * @param userId
     * @param orderId
     * @return
     */
    public SugoAftersale findByOrderId(Integer userId, Integer orderId) {
        SugoAftersaleExample example = new SugoAftersaleExample();
        example.or().andOrderIdEqualTo(orderId).andUserIdEqualTo(userId).andDeletedEqualTo(false);
        return aftersaleMapper.selectOneByExample(example);
    }
}






























