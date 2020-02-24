package com.sugo.sql.service;

import com.sugo.sql.dao.SugoGoodsAttributeMapper;
import com.sugo.sql.entity.SugoGoodsAttribute;
import com.sugo.sql.entity.SugoGoodsAttributeExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class GoodsAttributeService {
    @Resource
    private SugoGoodsAttributeMapper goodsAttributeMapper;

    /**
     * 根据商品id查找商品属性
     * @param gid
     * @return
     */
    public List<SugoGoodsAttribute> queryByGid(Integer gid) {
        SugoGoodsAttributeExample example = new SugoGoodsAttributeExample();
        example.or().andGoodsIdEqualTo(gid).andDeletedEqualTo(false);
        return goodsAttributeMapper.selectByExample(example);
    }

    /**
     * 添加商品属性
     * @param goodsAttribute
     */
    public void add(SugoGoodsAttribute goodsAttribute) {
        goodsAttribute.setAddTime(LocalDateTime.now());
        goodsAttribute.setUpdateTime(LocalDateTime.now());
        goodsAttributeMapper.insertSelective(goodsAttribute);
    }

    /**
     * 根据id查找商品属性
     * @param id
     * @return
     */
    public SugoGoodsAttribute findById(Integer id) {
        return goodsAttributeMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据商品id删除商品属性
     * @param gid
     */
    public void deleteByGid(Integer gid) {
        SugoGoodsAttributeExample example = new SugoGoodsAttributeExample();
        example.or().andGoodsIdEqualTo(gid);
        goodsAttributeMapper.logicalDeleteByExample(example);
    }

    /**
     * 根据id删除商品属性
     * @param id
     */
    public void deleteById(Integer id) {
        goodsAttributeMapper.logicalDeleteByPrimaryKey(id);
    }

    /**
     * 修改商品属性信息
     * @param goodsAttribute
     */
    public void updateById(SugoGoodsAttribute goodsAttribute) {
        goodsAttribute.setUpdateTime(LocalDateTime.now());
        goodsAttributeMapper.updateByPrimaryKeySelective(goodsAttribute);
    }
}