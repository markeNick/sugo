package com.sugo.sql.service.admin;

import com.github.pagehelper.PageHelper;
import com.sugo.sql.dao.SugoNoticeAdminMapper;
import com.sugo.sql.entity.SugoNoticeAdmin;
import com.sugo.sql.entity.SugoNoticeAdminExample;
import org.springframework.stereotype.Service;

import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class NoticeAdminService {
    @Resource
    private SugoNoticeAdminMapper noticeAdminMapper;

    public List<SugoNoticeAdmin> querySelective(String title, String type, Integer adminId, Integer page, Integer limit, String sort, String order) {
        SugoNoticeAdminExample example = new SugoNoticeAdminExample();
        SugoNoticeAdminExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(title)){
            criteria.andNoticeTitleLike("%" + title + "%");
        }

        if(type.equals("read")){
            criteria.andReadTimeIsNotNull();
        }
        else if(type.equals("unread")){
            criteria.andReadTimeIsNull();
        }
        criteria.andAdminIdEqualTo(adminId);
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, limit);
        return noticeAdminMapper.selectByExample(example);
    }

    public SugoNoticeAdmin find(Integer noticeId, Integer adminId) {
        SugoNoticeAdminExample example = new SugoNoticeAdminExample();
        example.or().andNoticeIdEqualTo(noticeId).andAdminIdEqualTo(adminId).andDeletedEqualTo(false);
        return noticeAdminMapper.selectOneByExample(example);
    }

    public void add(SugoNoticeAdmin noticeAdmin) {
        noticeAdmin.setAddTime(LocalDateTime.now());
        noticeAdmin.setUpdateTime(LocalDateTime.now());
        noticeAdminMapper.insertSelective(noticeAdmin);
    }

    public void update(SugoNoticeAdmin noticeAdmin) {
        noticeAdmin.setUpdateTime(LocalDateTime.now());
        noticeAdminMapper.updateByPrimaryKeySelective(noticeAdmin);
    }

    public void markReadByIds(List<Integer> ids, Integer adminId) {
        SugoNoticeAdminExample example = new SugoNoticeAdminExample();
        example.or().andIdIn(ids).andAdminIdEqualTo(adminId).andDeletedEqualTo(false);
        SugoNoticeAdmin noticeAdmin = new SugoNoticeAdmin();
        LocalDateTime now = LocalDateTime.now();
        noticeAdmin.setReadTime(now);
        noticeAdmin.setUpdateTime(now);
        noticeAdminMapper.updateByExampleSelective(noticeAdmin, example);
    }

    public void deleteById(Integer id, Integer adminId) {
        SugoNoticeAdminExample example = new SugoNoticeAdminExample();
        example.or().andIdEqualTo(id).andAdminIdEqualTo(adminId).andDeletedEqualTo(false);
        SugoNoticeAdmin noticeAdmin = new SugoNoticeAdmin();
        noticeAdmin.setUpdateTime(LocalDateTime.now());
        noticeAdmin.setDeleted(true);
        noticeAdminMapper.updateByExampleSelective(noticeAdmin, example);
    }

    public void deleteByIds(List<Integer> ids, Integer adminId) {
        SugoNoticeAdminExample example = new SugoNoticeAdminExample();
        example.or().andIdIn(ids).andAdminIdEqualTo(adminId).andDeletedEqualTo(false);
        SugoNoticeAdmin noticeAdmin = new SugoNoticeAdmin();
        noticeAdmin.setUpdateTime(LocalDateTime.now());
        noticeAdmin.setDeleted(true);
        noticeAdminMapper.updateByExampleSelective(noticeAdmin, example);
    }

    public int countUnread(Integer adminId) {
        SugoNoticeAdminExample example = new SugoNoticeAdminExample();
        example.or().andAdminIdEqualTo(adminId).andReadTimeIsNull().andDeletedEqualTo(false);
        return (int)noticeAdminMapper.countByExample(example);
    }

    public List<SugoNoticeAdmin> queryByNoticeId(Integer noticeId) {
        SugoNoticeAdminExample example = new SugoNoticeAdminExample();
        example.or().andNoticeIdEqualTo(noticeId).andDeletedEqualTo(false);
        return noticeAdminMapper.selectByExample(example);
    }

    public void deleteByNoticeId(Integer id) {
        SugoNoticeAdminExample example = new SugoNoticeAdminExample();
        example.or().andNoticeIdEqualTo(id).andDeletedEqualTo(false);
        SugoNoticeAdmin noticeAdmin = new SugoNoticeAdmin();
        noticeAdmin.setUpdateTime(LocalDateTime.now());
        noticeAdmin.setDeleted(true);
        noticeAdminMapper.updateByExampleSelective(noticeAdmin, example);
    }

    public void deleteByNoticeIds(List<Integer> ids) {
        SugoNoticeAdminExample example = new SugoNoticeAdminExample();
        example.or().andNoticeIdIn(ids).andDeletedEqualTo(false);
        SugoNoticeAdmin noticeAdmin = new SugoNoticeAdmin();
        noticeAdmin.setUpdateTime(LocalDateTime.now());
        noticeAdmin.setDeleted(true);
        noticeAdminMapper.updateByExampleSelective(noticeAdmin, example);
    }

    public int countReadByNoticeId(Integer noticeId) {
        SugoNoticeAdminExample example = new SugoNoticeAdminExample();
        example.or().andNoticeIdEqualTo(noticeId).andReadTimeIsNotNull().andDeletedEqualTo(false);
        return (int)noticeAdminMapper.countByExample(example);
    }

    public void updateByNoticeId(SugoNoticeAdmin noticeAdmin, Integer noticeId) {
        SugoNoticeAdminExample example = new SugoNoticeAdminExample();
        example.or().andNoticeIdEqualTo(noticeId).andDeletedEqualTo(false);
        noticeAdmin.setUpdateTime(LocalDateTime.now());
        noticeAdminMapper.updateByExampleSelective(noticeAdmin, example);
    }
}