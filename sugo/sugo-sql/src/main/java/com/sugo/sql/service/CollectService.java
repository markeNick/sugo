package com.sugo.sql.service;

import com.github.pagehelper.PageHelper;
import com.sugo.sql.dao.SugoCollectMapper;
import com.sugo.sql.entity.SugoCollect;
import com.sugo.sql.entity.SugoCollectExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CollectService {
    @Resource
    private SugoCollectMapper collectMapper;

    /**
     * 返回用户收藏记录数
     * @param uid
     * @param vid
     * @return
     */
    public int count(int uid, Integer vid) {
        SugoCollectExample example = new SugoCollectExample();
        example.or().andUserIdEqualTo(uid).andValueIdEqualTo(vid).andDeletedEqualTo(false);
        return (int) collectMapper.countByExample(example);
    }

    /**
     * 根据类型查找用户收藏
     * @param userId
     * @param type
     * @param page
     * @param limit
     * @param sort
     * @param order
     * @return
     */
    public List<SugoCollect> queryByType(Integer userId, Byte type, Integer page, Integer limit, String sort, String order) {
        SugoCollectExample example = new SugoCollectExample();
        SugoCollectExample.Criteria criteria = example.createCriteria();

        if (type != null) {
            criteria.andTypeEqualTo(type);
        }
        criteria.andUserIdEqualTo(userId);
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, limit);
        return collectMapper.selectByExample(example);
    }

    public int countByType(Integer userId, Byte type) {
        SugoCollectExample example = new SugoCollectExample();
        example.or().andUserIdEqualTo(userId).andTypeEqualTo(type).andDeletedEqualTo(false);
        return (int) collectMapper.countByExample(example);
    }

    public SugoCollect queryByTypeAndValue(Integer userId, Byte type, Integer valueId) {
        SugoCollectExample example = new SugoCollectExample();
        example.or().andUserIdEqualTo(userId).andValueIdEqualTo(valueId).andTypeEqualTo(type).andDeletedEqualTo(false);
        return collectMapper.selectOneByExample(example);
    }

    public void deleteById(Integer id) {
        collectMapper.logicalDeleteByPrimaryKey(id);
    }

    public int add(SugoCollect collect) {
        collect.setAddTime(LocalDateTime.now());
        collect.setUpdateTime(LocalDateTime.now());
        return collectMapper.insertSelective(collect);
    }

    public List<SugoCollect> querySelective(String userId, String valueId, Integer page, Integer size, String sort, String order) {
        SugoCollectExample example = new SugoCollectExample();
        SugoCollectExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(userId)) {
            criteria.andUserIdEqualTo(Integer.valueOf(userId));
        }
        if (!StringUtils.isEmpty(valueId)) {
            criteria.andValueIdEqualTo(Integer.valueOf(valueId));
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, size);
        return collectMapper.selectByExample(example);
    }
}