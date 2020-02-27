package com.sugo.sql.service;

import com.github.pagehelper.PageHelper;
import com.sugo.sql.dao.SugoAdMapper;
import com.sugo.sql.entity.SugoAd;
import com.sugo.sql.entity.SugoAdExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdService {

    @Resource
    private SugoAdMapper sugoAdMapper;

    public List<SugoAd> queryIndex() {
        SugoAdExample example = new SugoAdExample();
        example.or().andPositionEqualTo((byte) 1).andDeletedEqualTo(false).andEnabledEqualTo(true);
        return sugoAdMapper.selectByExample(example);
    }

    public List<SugoAd> querySelective(String name, String content, Integer page, Integer limit, String sort, String order) {
        SugoAdExample example = new SugoAdExample();
        SugoAdExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        if (!StringUtils.isEmpty(content)) {
            criteria.andContentLike("%" + content + "%");
        }

        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, limit);
        return sugoAdMapper.selectByExample(example);
    }

    public int updateById(SugoAd ad) {
        ad.setUpdateTime(LocalDateTime.now());
        return sugoAdMapper.updateByPrimaryKeySelective(ad);
    }

    public void add(SugoAd ad) {
        ad.setAddTime(LocalDateTime.now());
        ad.setUpdateTime(LocalDateTime.now());
        sugoAdMapper.insertSelective(ad);
    }

    public void deleteById(Integer id) {
        sugoAdMapper.logicalDeleteByPrimaryKey(id);
    }

    public SugoAd findById(Integer id) {
        return sugoAdMapper.selectByPrimaryKey(id);
    }

}