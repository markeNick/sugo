package com.sugo.sql.service;

import com.github.pagehelper.PageHelper;
import com.sugo.sql.dao.SugoAdMapper;
import com.sugo.sql.entity.SugoAd;
import com.sugo.sql.entity.SugoAdExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
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


}