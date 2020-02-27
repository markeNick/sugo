package com.sugo.sql.service;

import com.github.pagehelper.PageHelper;
import com.sugo.sql.dao.SugoTopicMapper;
import com.sugo.sql.entity.SugoTopic;
import com.sugo.sql.entity.SugoTopicExample;
import org.springframework.stereotype.Service;
import com.sugo.sql.entity.SugoTopic.Column;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TopicService {
    @Resource
    private SugoTopicMapper topicMapper;
    private Column[] columns = new Column[]{
            Column.id, Column.title, Column.subtitle,
            Column.price, Column.picUrl, Column.readCount
    };

    public List<SugoTopic> queryList(int offset, int limit) {
        return queryList(offset, limit, "add_time", "desc");
    }

    public List<SugoTopic> queryList(int offset, int limit, String sort, String order) {
        SugoTopicExample example = new SugoTopicExample();
        example.or().andDeletedEqualTo(false);
        example.setOrderByClause(sort + " " + order);
        PageHelper.startPage(offset, limit);
        return topicMapper.selectByExampleSelective(example, columns);
    }

    public int queryTotal() {
        SugoTopicExample example = new SugoTopicExample();
        example.or().andDeletedEqualTo(false);
        return (int) topicMapper.countByExample(example);
    }

    public SugoTopic findById(Integer id) {
        SugoTopicExample example = new SugoTopicExample();
        example.or().andIdEqualTo(id).andDeletedEqualTo(false);
        return topicMapper.selectOneByExampleWithBLOBs(example);
    }

    public List<SugoTopic> queryRelatedList(Integer id, int offset, int limit) {
        SugoTopicExample example = new SugoTopicExample();
        example.or().andIdEqualTo(id).andDeletedEqualTo(false);
        List<SugoTopic> topics = topicMapper.selectByExample(example);
        if (topics.size() == 0) {
            return queryList(offset, limit, "add_time", "desc");
        }
        SugoTopic topic = topics.get(0);

        example = new SugoTopicExample();
        example.or().andIdNotEqualTo(topic.getId()).andDeletedEqualTo(false);
        PageHelper.startPage(offset, limit);
        List<SugoTopic> relateds = topicMapper.selectByExampleWithBLOBs(example);
        if (relateds.size() != 0) {
            return relateds;
        }

        return queryList(offset, limit, "add_time", "desc");
    }

    public List<SugoTopic> querySelective(String title, String subtitle, Integer page, Integer limit, String sort, String order) {
        SugoTopicExample example = new SugoTopicExample();
        SugoTopicExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(title)) {
            criteria.andTitleLike("%" + title + "%");
        }
        if (!StringUtils.isEmpty(subtitle)) {
            criteria.andSubtitleLike("%" + subtitle + "%");
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, limit);
        return topicMapper.selectByExampleWithBLOBs(example);
    }

    public int updateById(SugoTopic topic) {
        topic.setUpdateTime(LocalDateTime.now());
        SugoTopicExample example = new SugoTopicExample();
        example.or().andIdEqualTo(topic.getId());
        return topicMapper.updateByExampleSelective(topic, example);
    }

    public void deleteById(Integer id) {
        topicMapper.logicalDeleteByPrimaryKey(id);
    }

    public void add(SugoTopic topic) {
        topic.setAddTime(LocalDateTime.now());
        topic.setUpdateTime(LocalDateTime.now());
        topicMapper.insertSelective(topic);
    }


    public void deleteByIds(List<Integer> ids) {
        SugoTopicExample example = new SugoTopicExample();
        example.or().andIdIn(ids).andDeletedEqualTo(false);
        SugoTopic topic = new SugoTopic();
        topic.setUpdateTime(LocalDateTime.now());
        topic.setDeleted(true);
        topicMapper.updateByExampleSelective(topic, example);
    }
}