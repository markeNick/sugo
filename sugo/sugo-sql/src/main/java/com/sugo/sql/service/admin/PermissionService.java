package com.sugo.sql.service.admin;

import com.sugo.sql.dao.SugoPermissionMapper;
import com.sugo.sql.entity.SugoPermission;
import com.sugo.sql.entity.SugoPermissionExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PermissionService {
    @Resource
    private SugoPermissionMapper permissionMapper;

    /**
     * 通过角色ID批量查询角色的权限
     * @param roleIds
     * @return
     */
    public Set<String> queryByRoleIds(Integer[] roleIds) {
        Set<String> permissions = new HashSet<>();
        if(roleIds.length == 0) {
            return permissions;
        }

        SugoPermissionExample example = new SugoPermissionExample();
        example.or().andRoleIdIn(Arrays.asList(roleIds)).andDeletedEqualTo(false);
        List<SugoPermission> permissionList = permissionMapper.selectByExample(example);

        for (SugoPermission p : permissionList) {
            permissions.add(p.getPermission());
        }

        return permissions;
    }

    /**
     * 通过某一角色ID查询角色的权限
      * @param roleId
     * @return
     */
    public Set<String> queryByRoleId(Integer roleId) {
        Set<String> permissions = new HashSet<String>();
        if(roleId == null){
            return permissions;
        }

        SugoPermissionExample example = new SugoPermissionExample();
        example.or().andRoleIdEqualTo(roleId).andDeletedEqualTo(false);
        List<SugoPermission> permissionList = permissionMapper.selectByExample(example);

        for(SugoPermission p : permissionList){
            permissions.add(p.getPermission());
        }

        return permissions;
    }

    /**
     * 检查拥有权限
     * @param roleId
     * @return
     */
    public boolean checkSuperPermission(Integer roleId) {
        if(roleId == null){
            return false;
        }

        SugoPermissionExample example = new SugoPermissionExample();
        example.or().andRoleIdEqualTo(roleId).andPermissionEqualTo("*").andDeletedEqualTo(false);
        return permissionMapper.countByExample(example) != 0;
    }

    /**
     * 删除角色
     * @param roleId
     */
    public void deleteByRoleId(Integer roleId) {
        SugoPermissionExample example = new SugoPermissionExample();
        example.or().andRoleIdEqualTo(roleId).andDeletedEqualTo(false);
        permissionMapper.logicalDeleteByExample(example);
    }

    /**
     * 添加权限
     * @param SugoPermission
     */
    public void add(SugoPermission SugoPermission) {
        SugoPermission.setAddTime(LocalDateTime.now());
        SugoPermission.setUpdateTime(LocalDateTime.now());
        permissionMapper.insertSelective(SugoPermission);
    }
}