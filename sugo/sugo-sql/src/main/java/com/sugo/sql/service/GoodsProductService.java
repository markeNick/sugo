package com.sugo.sql.service;

import com.sugo.sql.dao.GoodsProductMapper;
import com.sugo.sql.dao.SugoGoodsProductMapper;
import com.sugo.sql.entity.SugoGoodsProduct;
import com.sugo.sql.entity.SugoGoodsProductExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class GoodsProductService {
    @Resource
    private SugoGoodsProductMapper sugoGoodsProductMapper;
    @Resource
    private GoodsProductMapper goodsProductMapper;

    public List<SugoGoodsProduct> queryByGid(Integer gid) {
        SugoGoodsProductExample example = new SugoGoodsProductExample();
        example.or().andGoodsIdEqualTo(gid).andDeletedEqualTo(false);
        return sugoGoodsProductMapper.selectByExample(example);
    }

    public SugoGoodsProduct findById(Integer id) {
        return sugoGoodsProductMapper.selectByPrimaryKey(id);
    }

    public void deleteById(Integer id) {
        sugoGoodsProductMapper.logicalDeleteByPrimaryKey(id);
    }

    public void add(SugoGoodsProduct goodsProduct) {
        goodsProduct.setAddTime(LocalDateTime.now());
        goodsProduct.setUpdateTime(LocalDateTime.now());
        sugoGoodsProductMapper.insertSelective(goodsProduct);
    }

    public int count() {
        SugoGoodsProductExample example = new SugoGoodsProductExample();
        example.or().andDeletedEqualTo(false);
        return (int) sugoGoodsProductMapper.countByExample(example);
    }

    public void deleteByGid(Integer gid) {
        SugoGoodsProductExample example = new SugoGoodsProductExample();
        example.or().andGoodsIdEqualTo(gid);
        sugoGoodsProductMapper.logicalDeleteByExample(example);
    }

    public int addStock(Integer id, Short num){
        return goodsProductMapper.addStock(id, num);
    }

    public int reduceStock(Integer id, Short num){
        return goodsProductMapper.reduceStock(id, num);
    }

    public void updateById(SugoGoodsProduct product) {
        product.setUpdateTime(LocalDateTime.now());
        sugoGoodsProductMapper.updateByPrimaryKeySelective(product);
    }
}