package com.sugo.sql.service;

import com.github.pagehelper.PageHelper;
import com.sugo.sql.dao.GoodsMapper;
import com.sugo.sql.dao.SugoGoodsMapper;
import com.sugo.sql.entity.SugoGoods;
import com.sugo.sql.entity.SugoGoodsExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sugo.sql.entity.SugoGoods.Column;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class GoodsService {
    private Column[] columns = new Column[]{
            Column.id, Column.name, Column.brief, Column.picUrl, Column.isHot,
            Column.isNew, Column.counterPrice, Column.retailPrice
    };

    @Resource
    private SugoGoodsMapper sugoGoodsMapper;

    @Resource
    private GoodsMapper goodsMapper;

    /**
     * 查询热卖商品
     * @param offset
     * @param limit
     * @return
     */
    public List<SugoGoods> queryByHot(int offset, int limit) {
        SugoGoodsExample example = new SugoGoodsExample();
        example.or().andIsHotEqualTo(true).andIsOnSaleEqualTo(true).andDeletedEqualTo(false);
        example.setOrderByClause("add_time desc");
        PageHelper.startPage(offset, limit);

        return sugoGoodsMapper.selectByExampleSelective(example, columns);
    }

    /**
     * 查询新品F
     * @param offset
     * @param limit
     * @return
     */
    public List<SugoGoods> queryByNew(int offset, int limit, int adcode) {
        SugoGoodsExample example = new SugoGoodsExample();
        example.or().andIsNewEqualTo(true).andIsOnSaleEqualTo(true).andAdcodeEqualTo(adcode).andDeletedEqualTo(false);
        example.setOrderByClause("add_time desc");
        PageHelper.startPage(offset, limit);

        return sugoGoodsMapper.selectByExampleSelective(example, columns);
    }

    /**
     * 获取分类下的商品
     * @param catList
     * @param offset
     * @param limit
     * @return
     */
    public List<SugoGoods> queryByCategory(List<Integer> catList, int offset, int limit) {
        SugoGoodsExample example = new SugoGoodsExample();
        example.or().andCategoryIdIn(catList).andIsOnSaleEqualTo(true).andDeletedEqualTo(false);
        example.setOrderByClause("add_time desc");
        PageHelper.startPage(offset, limit);

        return sugoGoodsMapper.selectByExampleSelective(example, columns);
    }

    /**
     * 获取所有在售物品总数
     *
     * @return
     */
    public Integer queryOnSale() {
        SugoGoodsExample example = new SugoGoodsExample();
        example.or().andIsOnSaleEqualTo(true).andDeletedEqualTo(false);
        return (int) sugoGoodsMapper.countByExample(example);
    }

    /**
     * 统计用户附近的商品数量
     * @param adcode
     * @return
     */
    public Long countGoodsByAdCode(String adcode) {

        return goodsMapper.countGoodsByAdCode(adcode);
    }

//    public List<SugoGoods> selsectByNew(int offset, int limit, String adcode) {
//
//
//    }

    public List<SugoGoods> queryByCategory(Integer catId, int offset, int limit) {
        SugoGoodsExample example = new SugoGoodsExample();
        example.or().andCategoryIdEqualTo(catId).andIsOnSaleEqualTo(true).andDeletedEqualTo(false);
        example.setOrderByClause("add_time desc");
        PageHelper.startPage(offset, limit);

        return sugoGoodsMapper.selectByExampleSelective(example, columns);
    }

    public List<SugoGoods> querySelective(Integer catId, Integer brandId, String keywords, Boolean isHot, Boolean isNew, Integer offset, Integer limit, String sort, String order) {
        SugoGoodsExample example = new SugoGoodsExample();
        SugoGoodsExample.Criteria criteria1 = example.or();
        SugoGoodsExample.Criteria criteria2 = example.or();

        if (!StringUtils.isEmpty(catId) && catId != 0) {
            criteria1.andCategoryIdEqualTo(catId);
            criteria2.andCategoryIdEqualTo(catId);
        }
        if (!StringUtils.isEmpty(brandId)) {
            criteria1.andBrandIdEqualTo(brandId);
            criteria2.andBrandIdEqualTo(brandId);
        }
        if (!StringUtils.isEmpty(isNew)) {
            criteria1.andIsNewEqualTo(isNew);
            criteria2.andIsNewEqualTo(isNew);
        }
        if (!StringUtils.isEmpty(isHot)) {
            criteria1.andIsHotEqualTo(isHot);
            criteria2.andIsHotEqualTo(isHot);
        }
        if (!StringUtils.isEmpty(keywords)) {
            criteria1.andKeywordsLike("%" + keywords + "%");
            criteria2.andNameLike("%" + keywords + "%");
        }
        criteria1.andIsOnSaleEqualTo(true);
        criteria2.andIsOnSaleEqualTo(true);
        criteria1.andDeletedEqualTo(false);
        criteria2.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(offset, limit);

        return sugoGoodsMapper.selectByExampleSelective(example, columns);
    }

    public List<SugoGoods> querySelective(Integer goodsId, String goodsSn, String name, Integer page, Integer size, String sort, String order) {
        SugoGoodsExample example = new SugoGoodsExample();
        SugoGoodsExample.Criteria criteria = example.createCriteria();

        if (goodsId != null) {
            criteria.andIdEqualTo(goodsId);
        }
        if (!StringUtils.isEmpty(goodsSn)) {
            criteria.andGoodsSnEqualTo(goodsSn);
        }
        if (!StringUtils.isEmpty(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, size);
        return sugoGoodsMapper.selectByExampleWithBLOBs(example);
    }

    /**
     * 查询某个商品的完整信息
     * @param id
     * @return
     */
    public SugoGoods findById(Integer id) {
        SugoGoodsExample example = new SugoGoodsExample();
        example.or().andIdEqualTo(id).andDeletedEqualTo(false);
        return sugoGoodsMapper.selectOneByExampleWithBLOBs(example);
    }

    public SugoGoods findByIdVO(Integer id) {
        SugoGoodsExample example = new SugoGoodsExample();
        example.or().andIdEqualTo(id).andDeletedEqualTo(false).andIsOnSaleEqualTo(true);
        return sugoGoodsMapper.selectOneByExampleSelective(example, columns);
    }

    /**
     * 统计所有在售商品总数
     * @return
     */
    public Integer countOnSale() {
        SugoGoodsExample example = new SugoGoodsExample();
        example.or().andIsOnSaleEqualTo(true).andDeletedEqualTo(false);
        return (int) sugoGoodsMapper.countByExample(example);
    }

    /**
     * 更新商品信息
     * @param goods
     * @return
     */
    public int updateById(SugoGoods goods) {
        goods.setUpdateTime(LocalDateTime.now());
        return sugoGoodsMapper.updateByPrimaryKeySelective(goods);
    }

    /**
     * 删除商品信息
     * @param id
     */
    public void deleteById(Integer id) {
        sugoGoodsMapper.logicalDeleteByPrimaryKey(id);
    }

    /**
     * 添加商品
     * @param goods
     */
    public void add(SugoGoods goods) {
        goods.setAddTime(LocalDateTime.now());
        goods.setUpdateTime(LocalDateTime.now());
        sugoGoodsMapper.insertSelective(goods);
    }

    /**
     * 统计未删除的所有物品总数，包括在售和下架
     * @return
     */
    public int countGoods() {
        SugoGoodsExample example = new SugoGoodsExample();
        example.or().andDeletedEqualTo(false);
        return (int) sugoGoodsMapper.countByExample(example);
    }

    public List<Integer> getCatIds(Integer brandId, String keywords, Boolean isHot, Boolean isNew) {
        SugoGoodsExample example = new SugoGoodsExample();
        SugoGoodsExample.Criteria criteria1 = example.or();
        SugoGoodsExample.Criteria criteria2 = example.or();

        if (!StringUtils.isEmpty(brandId)) {
            criteria1.andBrandIdEqualTo(brandId);
            criteria2.andBrandIdEqualTo(brandId);
        }
        if (!StringUtils.isEmpty(isNew)) {
            criteria1.andIsNewEqualTo(isNew);
            criteria2.andIsNewEqualTo(isNew);
        }
        if (!StringUtils.isEmpty(isHot)) {
            criteria1.andIsHotEqualTo(isHot);
            criteria2.andIsHotEqualTo(isHot);
        }
        if (!StringUtils.isEmpty(keywords)) {
            criteria1.andKeywordsLike("%" + keywords + "%");
            criteria2.andNameLike("%" + keywords + "%");
        }
        criteria1.andIsOnSaleEqualTo(true);
        criteria2.andIsOnSaleEqualTo(true);
        criteria1.andDeletedEqualTo(false);
        criteria2.andDeletedEqualTo(false);

        List<SugoGoods> goodsList = sugoGoodsMapper.selectByExampleSelective(example, Column.categoryId);
        List<Integer> cats = new ArrayList<Integer>();
        for (SugoGoods goods : goodsList) {
            cats.add(goods.getCategoryId());
        }
        return cats;
    }

    public boolean checkExistByName(String name) {
        SugoGoodsExample example = new SugoGoodsExample();
        example.or().andNameEqualTo(name).andIsOnSaleEqualTo(true).andDeletedEqualTo(false);
        return sugoGoodsMapper.countByExample(example) != 0;
    }

    public List<SugoGoods> queryByIds(Integer[] ids) {
        SugoGoodsExample example = new SugoGoodsExample();
        example.or().andIdIn(Arrays.asList(ids)).andIsOnSaleEqualTo(true).andDeletedEqualTo(false);
        return sugoGoodsMapper.selectByExampleSelective(example, columns);
    }
}




























