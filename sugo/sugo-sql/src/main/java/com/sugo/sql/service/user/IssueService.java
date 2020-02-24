package com.sugo.sql.service.user;

import com.github.pagehelper.PageHelper;
import com.sugo.sql.dao.SugoIssueMapper;
import com.sugo.sql.entity.SugoIssue;
import com.sugo.sql.entity.SugoIssueExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class IssueService {
    @Resource
    private SugoIssueMapper issueMapper;
    
    public void deleteById(Integer id) {
        issueMapper.logicalDeleteByPrimaryKey(id);
    }
    
    public void add(SugoIssue issue) {
        issue.setAddTime(LocalDateTime.now());
        issue.setUpdateTime(LocalDateTime.now());
        issueMapper.insertSelective(issue);
    }

    public List<SugoIssue> querySelective(String question, Integer page, Integer limit, String sort, String order) {
        SugoIssueExample example = new SugoIssueExample();
        SugoIssueExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(question)) {
            criteria.andQuestionLike("%" + question + "%");
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, limit);
        return issueMapper.selectByExample(example);
    }

    public int updateById(SugoIssue issue) {
        issue.setUpdateTime(LocalDateTime.now());
        return issueMapper.updateByPrimaryKeySelective(issue);
    }

    public SugoIssue findById(Integer id) {
        return issueMapper.selectByPrimaryKey(id);
    }
}