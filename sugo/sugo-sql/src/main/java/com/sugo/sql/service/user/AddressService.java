package com.sugo.sql.service.user;

import com.github.pagehelper.PageHelper;
import com.sugo.sql.dao.SugoAddressMapper;
import com.sugo.sql.entity.SugoAddress;
import com.sugo.sql.entity.SugoAddressExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
@Service
public class AddressService {
    @Resource
    private SugoAddressMapper addressMapper;

    /**
     * 通过uid查找收货地址
     * @param uid
     * @return
     */
    public List<SugoAddress> queryByUid(Integer uid) {
        SugoAddressExample example = new SugoAddressExample();
        example.or().andUserIdEqualTo(uid).andDeletedEqualTo(false);
        return addressMapper.selectByExample(example);
    }

    public SugoAddress query(Integer userId, Integer id) {
        SugoAddressExample example = new SugoAddressExample();
        example.or().andIdEqualTo(id).andUserIdEqualTo(userId).andDeletedEqualTo(false);
        return addressMapper.selectOneByExample(example);
    }

    /**
     * 添加收货地址
     * @param address
     * @return
     */
    public int add(SugoAddress address) {
        address.setAddTime(LocalDateTime.now());
        address.setUpdateTime(LocalDateTime.now());
        return addressMapper.insertSelective(address);
    }

    /**
     * 修改地址
     * @param address
     * @return
     */
    public int update(SugoAddress address) {
        address.setUpdateTime(LocalDateTime.now());
        return addressMapper.updateByPrimaryKeySelective(address);
    }

    /**
     * 删除地址
     * @param id
     */
    public void delete(Integer id) {
        addressMapper.logicalDeleteByPrimaryKey(id);
    }

    /**
     * 获取默认收货地址
     * @param userId
     * @return
     */
    public SugoAddress findDefault(Integer userId) {
        SugoAddressExample example = new SugoAddressExample();
        example.or().andUserIdEqualTo(userId).andIsDefaultEqualTo(true).andDeletedEqualTo(false);
        return addressMapper.selectOneByExample(example);
    }

    /**
     * 重置默认地址
     * @param userId
     */
    public void resetDefault(Integer userId) {
        SugoAddress address = new SugoAddress();
        address.setIsDefault(false);
        address.setUpdateTime(LocalDateTime.now());

        SugoAddressExample example = new SugoAddressExample();
        example.or().andUserIdEqualTo(userId).andDeletedEqualTo(false);
        addressMapper.updateByExampleSelective(address, example);
    }

    public List<SugoAddress> querySelective(Integer userId, String name, Integer page, Integer limit, String sort, String order) {
        SugoAddressExample example = new SugoAddressExample();
        SugoAddressExample.Criteria criteria = example.createCriteria();

        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (!StringUtils.isEmpty(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, limit);
        return addressMapper.selectByExample(example);
    }

}