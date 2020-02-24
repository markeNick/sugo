package com.sugo.sql.service;

import com.github.pagehelper.PageHelper;
import com.sugo.sql.dao.SugoCategoryMapper;
import com.sugo.sql.entity.SugoCategory;
import com.sugo.sql.entity.SugoCategoryExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CategoryService {
    @Resource
    private SugoCategoryMapper categoryMapper;
    private SugoCategory.Column[] CHANNEL = {
            SugoCategory.Column.id,
            SugoCategory.Column.name,
            SugoCategory.Column.iconUrl
    };

    /**
     * 分页查询查询级别为L1和name!="推荐"的类目
     * @param offset
     * @param limit
     * @return
     */
    public List<SugoCategory> queryL1WithoutRecommend(int offset, int limit) {
        SugoCategoryExample example = new SugoCategoryExample();
        example.or().andLevelEqualTo("L1").andNameNotEqualTo("推荐").andDeletedEqualTo(false);
        PageHelper.startPage(offset, limit);
        return categoryMapper.selectByExample(example);
    }

    /**
     * 分页查询查询级别为L1的类目
     * @param offset
     * @param limit
     * @return
     */
    public List<SugoCategory> queryL1(int offset, int limit) {
        SugoCategoryExample example = new SugoCategoryExample();
        example.or().andLevelEqualTo("L1").andDeletedEqualTo(false);
        PageHelper.startPage(offset, limit);
        return categoryMapper.selectByExample(example);
    }

    /**
     * 查询所有级别为L1的类目
     * @return
     */
    public List<SugoCategory> queryL1() {
        SugoCategoryExample example = new SugoCategoryExample();
        example.or().andLevelEqualTo("L1").andDeletedEqualTo(false);

        return categoryMapper.selectByExample(example);
    }

    /**
     * 查询父类目ID的类目
     * @param pid
     * @return
     */
    public List<SugoCategory> queryByPid(Integer pid) {
        SugoCategoryExample example = new SugoCategoryExample();
        example.or().andPidEqualTo(pid).andDeletedEqualTo(false);
        return categoryMapper.selectByExample(example);
    }

    /**
     * 根据id组批量查询级别为L2的类目
     * @param ids
     * @return
     */
    public List<SugoCategory> queryL2ByIds(List<Integer> ids) {
        SugoCategoryExample example = new SugoCategoryExample();
        example.or().andIdIn(ids).andLevelEqualTo("L2").andDeletedEqualTo(false);
        return categoryMapper.selectByExample(example);
    }

    /**
     * 根据id查询某一类目
     * @param id
     * @return
     */
    public SugoCategory findById(Integer id) {
        return categoryMapper.selectByPrimaryKey(id);
    }

    public List<SugoCategory> querySelective(String id, String name, Integer page, Integer size, String sort, String order) {
        SugoCategoryExample example = new SugoCategoryExample();
        SugoCategoryExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(id)) {
            criteria.andIdEqualTo(Integer.valueOf(id));
        }

        if(!StringUtils.isEmpty(name)) {
            criteria.andNameLike("%" + name + "%");
        }

        criteria.andDeletedEqualTo(false);

        if(!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, size);
        return categoryMapper.selectByExample(example);
    }

    /**
     * 更新类目
     * @param category
     * @return
     */
    public int updateById(SugoCategory category) {
        category.setUpdateTime(LocalDateTime.now());
        return categoryMapper.updateByPrimaryKeySelective(category);
    }

    /**
     * 逻辑删除类目
     * @param id
     */
    public void deleteById(Integer id) {
        categoryMapper.logicalDeleteByPrimaryKey(id);
    }

    /**
     * 添加类目
     * @param category
     */
    public void add(SugoCategory category) {
        category.setAddTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        categoryMapper.insertSelective(category);
    }

    /**
     * 查询类目返回规定字段
     * @return
     */
    public List<SugoCategory> queryChannel() {
        SugoCategoryExample example = new SugoCategoryExample();
        example.or().andLevelEqualTo("L1").andDeletedEqualTo(false);
        return categoryMapper.selectByExampleSelective(example, CHANNEL);
    }

}

























