package com.sugo.sql.service.user;

import com.github.pagehelper.PageHelper;
import com.sugo.sql.dao.SugoFootprintMapper;
import com.sugo.sql.entity.SugoFootprint;
import com.sugo.sql.entity.SugoFootprintExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class FootprintService {
    @Resource
    private SugoFootprintMapper footprintMapper;

    /**
     * 根据添加时间分页查询用户足迹
     * @param uid
     * @param page
     * @param size
     * @return
     */
    public List<SugoFootprint> queryByAddTime(Integer uid, Integer page, Integer size) {
        SugoFootprintExample example = new SugoFootprintExample();
        example.or().andUserIdEqualTo(uid).andDeletedEqualTo(false);
        example.setOrderByClause(SugoFootprint.Column.addTime.desc());
        PageHelper.startPage(page, size);
        return footprintMapper.selectByExample(example);
    }

    /**
     * 根据id查询足迹信息
     * @param id
     * @return
     */
    public SugoFootprint findById(Integer id) {
        return footprintMapper.selectByPrimaryKey(id);
    }

    public SugoFootprint findById(Integer uid, Integer id) {
        SugoFootprintExample example = new SugoFootprintExample();
        example.or().andUserIdEqualTo(uid).andIdEqualTo(id).andDeletedEqualTo(false);
        return footprintMapper.selectOneByExample(example);
    }

    /**
     * 删除某一id足迹
     * @param id
     */
    public void deleteById(Integer id) {
        footprintMapper.logicalDeleteByPrimaryKey(id);
    }

    /**
     * 添加足迹
     * @param footprint
     */
    public void add(SugoFootprint footprint) {
        footprint.setAddTime(LocalDateTime.now());
        footprint.setUpdateTime(LocalDateTime.now());
        footprintMapper.insertSelective(footprint);
    }

    public List<SugoFootprint> querySelective(String userId, String goodsId, Integer page, Integer size, String sort, String order) {
        SugoFootprintExample example = new SugoFootprintExample();
        SugoFootprintExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(userId)) {
            criteria.andUserIdEqualTo(Integer.valueOf(userId));
        }
        if (!StringUtils.isEmpty(goodsId)) {
            criteria.andGoodsIdEqualTo(Integer.valueOf(goodsId));
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, size);
        return footprintMapper.selectByExample(example);
    }


}











