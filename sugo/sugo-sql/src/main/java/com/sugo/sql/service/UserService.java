package com.sugo.sql.service;

import com.github.pagehelper.PageHelper;
import com.sugo.sql.dao.SugoUserMapper;
import com.sugo.sql.entity.SugoUser;
import com.sugo.sql.entity.SugoUserExample;
import com.sugo.sql.entity.UserVo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {
    @Resource
    private SugoUserMapper userMapper;

    /**
     * 根据用户id查询用户信息
     * @param uid
     * @return
     */
    public SugoUser findById(Integer uid) {
        return userMapper.selectByPrimaryKey(uid);
    }

    /**
     * 给回小程序端的用户信息
     * @param uid
     * @return
     */
    public UserVo findUserVoById(Integer uid) {
        SugoUser sugoUser = findById(uid);
        UserVo userVo = new UserVo();
        userVo.setNickname(sugoUser.getNickname());
        userVo.setAvatar(sugoUser.getAvatar());
        return userVo;
    }

    /**
     * 通过openID查询用户信息
     * @param openID
     * @return
     */
    public SugoUser queryByOpenId(String openID) {
        SugoUserExample example = new SugoUserExample();
        example.or().andWeixinOpenidEqualTo(openID).andDeletedEqualTo(false);
        return userMapper.selectOneByExample(example);
    }

    /**
     * 注册用户
     * @param user
     */
    public void add(SugoUser user) {
        user.setAddTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        userMapper.insertSelective(user);
    }

    /**
     * 更新用户更新时间
     * @param user
     * @return
     */
    public int updateById(SugoUser user) {
        user.setUpdateTime(LocalDateTime.now());
        return userMapper.updateByPrimaryKeySelective(user);
    }

    /**
     * 统计用户数
     * @return
     */
    public int count() {
        SugoUserExample example = new SugoUserExample();
        example.or().andDeletedEqualTo(false);

        return (int) userMapper.countByExample(example);
    }

    public List<SugoUser> querySelective(String username, String mobile, Integer page, Integer size, String sort, String order) {
        SugoUserExample example = new SugoUserExample();
        SugoUserExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(username)) {
            criteria.andUsernameLike("%" + username + "%");
        }
        if (!StringUtils.isEmpty(mobile)) {
            criteria.andMobileEqualTo(mobile);
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, size);
        return userMapper.selectByExample(example);
    }

}