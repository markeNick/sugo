package com.sugo.sql.service;

import com.github.pagehelper.PageHelper;
import com.sugo.sql.dao.SugoAdminMapper;
import com.sugo.sql.entity.SugoAdmin;
import com.sugo.sql.entity.SugoAdminExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sugo.sql.entity.SugoAdmin.Column;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdminService {
    private final Column[] result = new Column[] {
            Column.id,
            Column.username,
            Column.avatar,
            Column.roleIds
    };

    @Resource
    private SugoAdminMapper adminMapper;

    /**
     * 根据id主键查找管理员信息
     * @param id
     * @return
     */
    public SugoAdmin findAdmin(Integer id) {
        return adminMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据username查询管理员信息，结果可能有重复数据
     * @param username
     * @return
     */
    public List<SugoAdmin> findAdmin(String username) {
        SugoAdminExample example = new SugoAdminExample();
        example.or().andUsernameEqualTo(username).andDeletedEqualTo(false);
        return adminMapper.selectByExample(example);
    }

    /**
     * 分页查询含有username关键字的所有管理员信息
     * @param username
     * @param page
     * @param limit
     * @param sort
     * @param order
     * @return
     */
    public List<SugoAdmin> querySelective(String username, Integer page, Integer limit, String sort, String order) {
        SugoAdminExample example = new SugoAdminExample();
        SugoAdminExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(username)) {
            criteria.andUsernameLike("%" + username + "%");
        }

        criteria.andDeletedEqualTo(false);

        if(!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, limit);
        return adminMapper.selectByExampleSelective(example, result);
    }


    /**
     * 更新管理最后上线时间
     * @param admin
     * @return
     */
    public int updateById(SugoAdmin admin) {
        admin.setUpdateTime(LocalDateTime.now());
        return adminMapper.updateByPrimaryKeySelective(admin);
    }

    /**
     * 根据id软删除管理员
     * @param id
     */
    public void deleteById(Integer id) {
        adminMapper.logicalDeleteByPrimaryKey(id);
    }

    /**
     * 添加管理员
     * @param admin
     */
    public void add(SugoAdmin admin) {
        admin.setAddTime(LocalDateTime.now());
        admin.setUpdateTime(LocalDateTime.now());
        adminMapper.insertSelective(admin);
    }

    /**
     * 根据id查找管理员，并返回指定字段的信息
     * @param id
     * @return
     */
    public SugoAdmin findById(Integer id) {
        return adminMapper.selectByPrimaryKeySelective(id, result);
    }

    /**
     * 查询所有管理员信息
     * @return
     */
    public List<SugoAdmin> all() {
        SugoAdminExample example = new SugoAdminExample();
        example.or().andDeletedEqualTo(false);
        return adminMapper.selectByExample(example);
    }



}
















