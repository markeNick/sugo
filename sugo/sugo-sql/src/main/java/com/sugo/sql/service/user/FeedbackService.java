package com.sugo.sql.service.user;

import com.github.pagehelper.PageHelper;
import com.sugo.sql.dao.SugoFeedbackMapper;
import com.sugo.sql.entity.SugoFeedback;
import com.sugo.sql.entity.SugoFeedbackExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class FeedbackService {
    @Resource
    private SugoFeedbackMapper feedbackMapper;

    /**
     * 添加反馈信息
     * @param feedback
     * @return
     */
    public Integer add(SugoFeedback feedback) {
        feedback.setAddTime(LocalDateTime.now());
        feedback.setUpdateTime(LocalDateTime.now());
        return feedbackMapper.insertSelective(feedback);
    }

    /**
     * 查询反馈信息
     * @param uid
     * @param username
     * @param page
     * @param limit
     * @param sort
     * @param order
     * @return
     */
    public List<SugoFeedback> querySelective(Integer uid, String username, Integer page, Integer limit, String sort, String order) {
        SugoFeedbackExample example = new SugoFeedbackExample();
        SugoFeedbackExample.Criteria criteria = example.createCriteria();

        if(uid != null) {
            criteria.andUserIdEqualTo(uid);
        }

        if(!StringUtils.isEmpty(username)) {
            criteria.andUsernameEqualTo("%" + username + "%");
        }

        criteria.andDeletedEqualTo(false);

        if(!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, limit);
        return feedbackMapper.selectByExample(example);
    }
}