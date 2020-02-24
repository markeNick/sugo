package com.sugo.sql.service.user;

import com.github.pagehelper.PageHelper;
import com.sugo.sql.dao.SugoSearchHistoryMapper;
import com.sugo.sql.entity.SugoSearchHistory;
import com.sugo.sql.entity.SugoSearchHistoryExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SearchHistoryService {
    @Resource
    private SugoSearchHistoryMapper searchHistoryMapper;

    /**
     * 保存搜索历史
     * @param searchHistory
     */
    public void save(SugoSearchHistory searchHistory) {
        searchHistory.setAddTime(LocalDateTime.now());
        searchHistory.setUpdateTime(LocalDateTime.now());
        searchHistoryMapper.insertSelective(searchHistory);
    }

    /**
     * 查询某用户的搜索记录
     * @param uid
     * @return
     */
    public List<SugoSearchHistory> queryByUid(Integer uid) {
        SugoSearchHistoryExample example = new SugoSearchHistoryExample();
        example.or().andUserIdEqualTo(uid).andDeletedEqualTo(false);
        // 去重
        example.setDistinct(true);
        return searchHistoryMapper.selectByExampleSelective(example, SugoSearchHistory.Column.keyword);
    }

    /**
     * 删除用户的搜索历史
     * @param uid
     */
    public void deleteByUid(Integer uid) {
        SugoSearchHistoryExample example = new SugoSearchHistoryExample();
        example.or().andUserIdEqualTo(uid);
        searchHistoryMapper.logicalDeleteByExample(example);
    }

    public List<SugoSearchHistory> querySelective(String userId, String keyword, Integer page, Integer size, String sort, String order) {
        SugoSearchHistoryExample example = new SugoSearchHistoryExample();
        SugoSearchHistoryExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(userId)) {
            criteria.andUserIdEqualTo(Integer.valueOf(userId));
        }
        if (!StringUtils.isEmpty(keyword)) {
            criteria.andKeywordLike("%" + keyword + "%");
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, size);
        return searchHistoryMapper.selectByExample(example);
    }

}