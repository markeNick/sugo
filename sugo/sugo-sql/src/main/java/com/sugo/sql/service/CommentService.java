package com.sugo.sql.service;

import com.github.pagehelper.PageHelper;
import com.sugo.sql.dao.SugoCommentMapper;
import com.sugo.sql.entity.SugoComment;
import com.sugo.sql.entity.SugoCommentExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {
    @Resource
    private SugoCommentMapper commentMapper;

    public List<SugoComment> queryGoodsByGid(Integer id, int offset, int limit) {
        SugoCommentExample example = new SugoCommentExample();
        example.setOrderByClause(SugoComment.Column.addTime.desc());
        example.or().andValueIdEqualTo(id).andTypeEqualTo((byte) 0).andDeletedEqualTo(false);
        PageHelper.startPage(offset, limit);
        return commentMapper.selectByExample(example);
    }

    public List<SugoComment> query(Byte type, Integer valueId, Integer showType, Integer offset, Integer limit) {
        SugoCommentExample example = new SugoCommentExample();
        example.setOrderByClause(SugoComment.Column.addTime.desc());
        if (showType == 0) {
            example.or().andValueIdEqualTo(valueId).andTypeEqualTo(type).andDeletedEqualTo(false);
        } else if (showType == 1) {
            example.or().andValueIdEqualTo(valueId).andTypeEqualTo(type).andHasPictureEqualTo(true).andDeletedEqualTo(false);
        } else {
            throw new RuntimeException("showType不支持");
        }
        PageHelper.startPage(offset, limit);
        return commentMapper.selectByExample(example);
    }

    /**
     * 统计总记录数
     *
     * @param type
     * @param valueId
     * @param showType  0表示全部评论数， 1表示含有图片的评论数
     * @return
     */
    public int count(Byte type, Integer valueId, Integer showType) {
        SugoCommentExample example = new SugoCommentExample();
        if (showType == 0) {
            example.or().andValueIdEqualTo(valueId).andTypeEqualTo(type).andDeletedEqualTo(false);
        } else if (showType == 1) {
            example.or().andValueIdEqualTo(valueId).andTypeEqualTo(type).andHasPictureEqualTo(true).andDeletedEqualTo(false);
        } else {
            throw new RuntimeException("showType不支持");
        }
        return (int) commentMapper.countByExample(example);
    }

    public int save(SugoComment comment) {
        comment.setAddTime(LocalDateTime.now());
        comment.setUpdateTime(LocalDateTime.now());
        return commentMapper.insertSelective(comment);
    }

    public List<SugoComment> querySelective(String userId, String valueId, Integer page, Integer size, String sort, String order) {
        SugoCommentExample example = new SugoCommentExample();
        SugoCommentExample.Criteria criteria = example.createCriteria();

        // type=2 是订单商品回复，这里过滤
        criteria.andTypeNotEqualTo((byte) 2);

        if (!StringUtils.isEmpty(userId)) {
            criteria.andUserIdEqualTo(Integer.valueOf(userId));
        }
        if (!StringUtils.isEmpty(valueId)) {
            criteria.andValueIdEqualTo(Integer.valueOf(valueId)).andTypeEqualTo((byte) 0);
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, size);
        return commentMapper.selectByExample(example);
    }

    public void deleteById(Integer id) {
        commentMapper.logicalDeleteByPrimaryKey(id);
    }

    public SugoComment findById(Integer id) {
        return commentMapper.selectByPrimaryKey(id);
    }

    public int updateById(SugoComment comment) {
        return commentMapper.updateByPrimaryKeySelective(comment);
    }
}