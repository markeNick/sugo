package com.sugo.sql.service;

import com.github.pagehelper.PageHelper;
import com.sugo.sql.dao.SugoKeywordMapper;
import com.sugo.sql.entity.SugoKeyword;
import com.sugo.sql.entity.SugoKeywordExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class KeywordService {
    @Resource
    private SugoKeywordMapper keywordMapper;

    /**
     * 查询默认关键词
     * @return
     */
    public SugoKeyword queryDefault() {
        SugoKeywordExample example = new SugoKeywordExample();
        example.or().andDeletedEqualTo(false).andIsDefaultEqualTo(true);
        return keywordMapper.selectOneByExample(example);
    }

    /**
     * 查询热搜关键词
     * @return
     */
    public List<SugoKeyword> queryHots() {
        SugoKeywordExample example = new SugoKeywordExample();
        example.or().andIsHotEqualTo(true).andDeletedEqualTo(false);
        return keywordMapper.selectByExample(example);
    }

    /**
     * 通过部分关键字查询出关键词
     * @param keyword
     * @param page
     * @param limit
     * @return
     */
    public List<SugoKeyword> queryByKeyword(String keyword, Integer page, Integer limit) {
        SugoKeywordExample example = new SugoKeywordExample();
        example.setDistinct(true);
        example.or().andKeywordLike("%" + keyword + "%").andDeletedEqualTo(false);
        PageHelper.startPage(page, limit);
        return keywordMapper.selectByExampleSelective(example, SugoKeyword.Column.keyword);
    }

    public List<SugoKeyword> querySelective(String keyword, String url, Integer page, Integer limit, String sort, String order) {
        SugoKeywordExample example = new SugoKeywordExample();
        SugoKeywordExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(keyword)) {
            criteria.andKeywordLike("%" + keyword + "%");
        }
        if (!StringUtils.isEmpty(url)) {
            criteria.andUrlLike("%" + url + "%");
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, limit);
        return keywordMapper.selectByExample(example);
    }

    public void add(SugoKeyword keywords) {
        keywords.setAddTime(LocalDateTime.now());
        keywords.setUpdateTime(LocalDateTime.now());
        keywordMapper.insertSelective(keywords);
    }

    public SugoKeyword findById(Integer id) {
        return keywordMapper.selectByPrimaryKey(id);
    }

    public int updateById(SugoKeyword keywords) {
        keywords.setUpdateTime(LocalDateTime.now());
        return keywordMapper.updateByPrimaryKeySelective(keywords);
    }

    public void deleteById(Integer id) {
        keywordMapper.logicalDeleteByPrimaryKey(id);
    }

}