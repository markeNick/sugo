package com.sugo.sql.service;

import com.github.pagehelper.PageHelper;
import com.sugo.sql.dao.SugoStorageMapper;
import com.sugo.sql.entity.SugoStorage;
import com.sugo.sql.entity.SugoStorageExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SugoStorageService {
    @Resource
    private SugoStorageMapper sugoStorageMapper;

    /**
     * 通过文件索引删除存储
     * @param key
     */
    public void deleteByKey(String key) {
        SugoStorageExample example = new SugoStorageExample();
        example.or().andKeyEqualTo(key);
        sugoStorageMapper.logicalDeleteByExample(example);
    }

    /**
     * 添加存储信息
     * @param sugoStorage
     */
    public void add(SugoStorage sugoStorage) {
        sugoStorage.setAddTime(LocalDateTime.now());
        sugoStorage.setUpdateTime(LocalDateTime.now());
        sugoStorageMapper.insertSelective(sugoStorage);
    }

    /**
     * 通过文件唯一索引查询文件
     * @param key
     * @return
     */
    public SugoStorage findByKey(String key) {
        SugoStorageExample example = new SugoStorageExample();
        example.or().andKeyEqualTo(key).andDeletedEqualTo(false);
        return sugoStorageMapper.selectOneByExample(example);
    }

    /**
     * 更新文件存储
     * @param storageInfo
     * @return
     */
    public int update(SugoStorage storageInfo) {
        storageInfo.setUpdateTime(LocalDateTime.now());
        return sugoStorageMapper.updateByPrimaryKeySelective(storageInfo);
    }

    /**
     * 通过id查询文件存储
     * @param id
     * @return
     */
    public SugoStorage findById(Integer id) {
        return sugoStorageMapper.selectByPrimaryKey(id);
    }

    public List<SugoStorage> querySelective(String key, String name, Integer page, Integer limit, String sort, String order) {
        SugoStorageExample example = new SugoStorageExample();
        SugoStorageExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(key)) {
            criteria.andKeyEqualTo(key);
        }
        if (!StringUtils.isEmpty(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, limit);
        return sugoStorageMapper.selectByExample(example);
    }
}