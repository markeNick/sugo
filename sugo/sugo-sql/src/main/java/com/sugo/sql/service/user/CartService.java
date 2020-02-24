package com.sugo.sql.service.user;

import com.github.pagehelper.PageHelper;
import com.sugo.sql.dao.SugoCartMapper;
import com.sugo.sql.entity.SugoCart;
import com.sugo.sql.entity.SugoCartExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CartService {

    @Resource
    private SugoCartMapper cartMapper;

    public SugoCart queryExist(Integer goodsId, Integer productId, Integer userId) {
        SugoCartExample example = new SugoCartExample();
        example.or().andGoodsIdEqualTo(goodsId).andProductIdEqualTo(productId).andUserIdEqualTo(userId).andDeletedEqualTo(false);
        return cartMapper.selectOneByExample(example);
    }

    public void add(SugoCart cart) {
        cart.setAddTime(LocalDateTime.now());
        cart.setUpdateTime(LocalDateTime.now());
        cartMapper.insertSelective(cart);
    }

    public int updateById(SugoCart cart) {
        cart.setUpdateTime(LocalDateTime.now());
        return cartMapper.updateByPrimaryKeySelective(cart);
    }

    public List<SugoCart> queryByUid(int userId) {
        SugoCartExample example = new SugoCartExample();
        example.or().andUserIdEqualTo(userId).andDeletedEqualTo(false);
        return cartMapper.selectByExample(example);
    }

    public List<SugoCart> queryByUidAndChecked(Integer userId) {
        SugoCartExample example = new SugoCartExample();
        example.or().andUserIdEqualTo(userId).andCheckedEqualTo(true).andDeletedEqualTo(false);
        return cartMapper.selectByExample(example);
    }

    public int delete(List<Integer> productIdList, int userId) {
        SugoCartExample example = new SugoCartExample();
        example.or().andUserIdEqualTo(userId).andProductIdIn(productIdList);
        return cartMapper.logicalDeleteByExample(example);
    }

    public SugoCart findById(Integer id) {
        return cartMapper.selectByPrimaryKey(id);
    }

    public SugoCart findById(Integer userId, Integer id) {
        SugoCartExample example = new SugoCartExample();
        example.or().andUserIdEqualTo(userId).andIdEqualTo(id).andDeletedEqualTo(false);
        return cartMapper.selectOneByExample(example);
    }

    public int updateCheck(Integer userId, List<Integer> idsList, Boolean checked) {
        SugoCartExample example = new SugoCartExample();
        example.or().andUserIdEqualTo(userId).andProductIdIn(idsList).andDeletedEqualTo(false);
        SugoCart cart = new SugoCart();
        cart.setChecked(checked);
        cart.setUpdateTime(LocalDateTime.now());
        return cartMapper.updateByExampleSelective(cart, example);
    }

    public void clearGoods(Integer userId) {
        SugoCartExample example = new SugoCartExample();
        example.or().andUserIdEqualTo(userId).andCheckedEqualTo(true);
        SugoCart cart = new SugoCart();
        cart.setDeleted(true);
        cartMapper.updateByExampleSelective(cart, example);
    }

    public List<SugoCart> querySelective(Integer userId, Integer goodsId, Integer page, Integer limit, String sort, String order) {
        SugoCartExample example = new SugoCartExample();
        SugoCartExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(userId)) {
            criteria.andUserIdEqualTo(userId);
        }
        if (!StringUtils.isEmpty(goodsId)) {
            criteria.andGoodsIdEqualTo(goodsId);
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, limit);
        return cartMapper.selectByExample(example);
    }

    public void deleteById(Integer id) {
        cartMapper.logicalDeleteByPrimaryKey(id);
    }

    public boolean checkExist(Integer goodsId) {
        SugoCartExample example = new SugoCartExample();
        example.or().andGoodsIdEqualTo(goodsId).andCheckedEqualTo(true).andDeletedEqualTo(false);
        return cartMapper.countByExample(example) != 0;
    }

    public void updateProduct(Integer id, String goodsSn, String goodsName, BigDecimal price, String url) {
        SugoCart cart = new SugoCart();
        cart.setPrice(price);
        cart.setPicUrl(url);
        cart.setGoodsSn(goodsSn);
        cart.setGoodsName(goodsName);
        SugoCartExample example = new SugoCartExample();
        example.or().andProductIdEqualTo(id);
        cartMapper.updateByExampleSelective(cart, example);
    }
}