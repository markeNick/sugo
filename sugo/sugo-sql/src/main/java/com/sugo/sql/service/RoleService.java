package com.sugo.sql.service;

import com.github.pagehelper.PageHelper;
import com.sugo.sql.dao.SugoRoleMapper;
import com.sugo.sql.entity.SugoRole;
import com.sugo.sql.entity.SugoRoleExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RoleService {
    @Resource
    private SugoRoleMapper roleMapper;

    /**
     * 通过角色ID批量查询角色信息
     * @param roleIds
     * @return
     */
    public Set<String> queryByIds(Integer[] roleIds) {
        Set<String> roles = new HashSet<String>();
        if(roleIds.length == 0){
            return roles;
        }

        SugoRoleExample example = new SugoRoleExample();
        example.or().andIdIn(Arrays.asList(roleIds)).andEnabledEqualTo(true).andDeletedEqualTo(false);
        List<SugoRole> roleList = roleMapper.selectByExample(example);

        for(SugoRole r : roleList){
            roles.add(r.getName());
        }

        return roles;

    }

    /**
     * 分页查询名称含有name的角色信息
     * @param name
     * @param page
     * @param limit
     * @param sort
     * @param order
     * @return
     */
    public List<SugoRole> querySelective(String name, Integer page, Integer limit, String sort, String order) {
        SugoRoleExample example = new SugoRoleExample();
        SugoRoleExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, limit);
        return roleMapper.selectByExample(example);
    }

    /**
     * 通过id查询角色信息
     * @param id
     * @return
     */
    public SugoRole findById(Integer id) {
        return roleMapper.selectByPrimaryKey(id);
    }

    /**
     * 添加角色
     * @param role
     */
    public void add(SugoRole role) {
        role.setAddTime(LocalDateTime.now());
        role.setUpdateTime(LocalDateTime.now());
        roleMapper.insertSelective(role);
    }

    /**
     * 删除角色
     * @param id
     */
    public void deleteById(Integer id) {
        roleMapper.logicalDeleteByPrimaryKey(id);
    }

    /**
     * 更新角色
     * @param role
     */
    public void updateById(SugoRole role) {
        role.setUpdateTime(LocalDateTime.now());
        roleMapper.updateByPrimaryKeySelective(role);
    }

    /**
     * 检查角色是否存在
     * @param name
     * @return
     */
    public boolean checkExist(String name) {
        SugoRoleExample example = new SugoRoleExample();
        example.or().andNameEqualTo(name).andDeletedEqualTo(false);
        return roleMapper.countByExample(example) != 0;
    }

    /**
     * 查询所有角色信息
     * @return
     */
    public List<SugoRole> queryAll() {
        SugoRoleExample example = new SugoRoleExample();
        example.or().andDeletedEqualTo(false);
        return roleMapper.selectByExample(example);
    }
}