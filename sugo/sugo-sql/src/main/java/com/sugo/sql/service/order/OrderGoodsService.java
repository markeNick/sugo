package com.sugo.sql.service.order;

import com.sugo.sql.dao.SugoOrderGoodsMapper;
import com.sugo.sql.entity.SugoOrderGoods;
import com.sugo.sql.entity.SugoOrderGoodsExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderGoodsService {
    @Resource
    private SugoOrderGoodsMapper orderGoodsMapper;

    public int add(SugoOrderGoods orderGoods) {
        orderGoods.setAddTime(LocalDateTime.now());
        orderGoods.setUpdateTime(LocalDateTime.now());
        return orderGoodsMapper.insertSelective(orderGoods);
    }

    public List<SugoOrderGoods> queryByOid(Integer orderId) {
        SugoOrderGoodsExample example = new SugoOrderGoodsExample();
        example.or().andOrderIdEqualTo(orderId).andDeletedEqualTo(false);
        return orderGoodsMapper.selectByExample(example);
    }

    public List<SugoOrderGoods> findByOidAndGid(Integer orderId, Integer goodsId) {
        SugoOrderGoodsExample example = new SugoOrderGoodsExample();
        example.or().andOrderIdEqualTo(orderId).andGoodsIdEqualTo(goodsId).andDeletedEqualTo(false);
        return orderGoodsMapper.selectByExample(example);
    }

    public SugoOrderGoods findById(Integer id) {
        return orderGoodsMapper.selectByPrimaryKey(id);
    }

    public void updateById(SugoOrderGoods orderGoods) {
        orderGoods.setUpdateTime(LocalDateTime.now());
        orderGoodsMapper.updateByPrimaryKeySelective(orderGoods);
    }

    public Short getComments(Integer orderId) {
        SugoOrderGoodsExample example = new SugoOrderGoodsExample();
        example.or().andOrderIdEqualTo(orderId).andDeletedEqualTo(false);
        long count = orderGoodsMapper.countByExample(example);
        return (short) count;
    }

    public boolean checkExist(Integer goodsId) {
        SugoOrderGoodsExample example = new SugoOrderGoodsExample();
        example.or().andGoodsIdEqualTo(goodsId).andDeletedEqualTo(false);
        return orderGoodsMapper.countByExample(example) != 0;
    }
}