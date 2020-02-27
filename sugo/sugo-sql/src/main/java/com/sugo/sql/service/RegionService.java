package com.sugo.sql.service;

import com.github.pagehelper.PageHelper;
import com.sugo.sql.dao.SugoRegionMapper;
import com.sugo.sql.entity.SugoRegion;
import com.sugo.sql.entity.SugoRegionExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RegionService {
    @Resource
    private SugoRegionMapper regionMapper;

    /**
     * 查询所有行政区域信息
     * @return
     */
    public List<SugoRegion> getAll() {
        SugoRegionExample example = new SugoRegionExample();

        example.or().andTypeNotEqualTo((byte) 4);
        return regionMapper.selectByExample(example);
    }

    /**
     * 查询含有行政区域父ID
     * @param parentId
     * @return
     */
    public List<SugoRegion> queryByPid(Integer parentId) {
        SugoRegionExample example = new SugoRegionExample();
        example.or().andPidEqualTo(parentId);
        return regionMapper.selectByExample(example);
    }

    public SugoRegion findById(Integer id) {
        return regionMapper.selectByPrimaryKey(id);
    }

    public List<SugoRegion> querySelective(String name, Integer code, Integer page, Integer size, String sort, String order) {
        SugoRegionExample example = new SugoRegionExample();
        SugoRegionExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        if (!StringUtils.isEmpty(code)) {
            criteria.andCodeEqualTo(code);
        }

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, size);
        return regionMapper.selectByExample(example);
    }
}