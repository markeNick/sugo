package com.sugo.sql.service.goods;

import com.sugo.sql.dao.SugoGoodsSpecificationMapper;
import com.sugo.sql.entity.SugoGoodsSpecification;
import com.sugo.sql.entity.SugoGoodsSpecificationExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GoodsSpecificationService {

    @Resource
    private SugoGoodsSpecificationMapper goodsSpecificationMapper;

    public List<SugoGoodsSpecification> queryByGid(Integer gid) {
        SugoGoodsSpecificationExample example = new SugoGoodsSpecificationExample();
        example.or().andGoodsIdEqualTo(gid).andDeletedEqualTo(false);
        return goodsSpecificationMapper.selectByExample(example);
    }

    public SugoGoodsSpecification findById(Integer id) {
        return goodsSpecificationMapper.selectByPrimaryKey(id);
    }

    public void deleteByGid(Integer gid) {
        SugoGoodsSpecificationExample example = new SugoGoodsSpecificationExample();
        example.or().andGoodsIdEqualTo(gid);
        goodsSpecificationMapper.logicalDeleteByExample(example);
    }

    public void add(SugoGoodsSpecification goodsSpecification) {
        goodsSpecification.setAddTime(LocalDateTime.now());
        goodsSpecification.setUpdateTime(LocalDateTime.now());
        goodsSpecificationMapper.insertSelective(goodsSpecification);
    }

    public Object getSpecificationVoList(Integer id) {
        List<SugoGoodsSpecification> goodsSpecificationList = queryByGid(id);

        Map<String, VO> map = new HashMap<>();
        List<VO> specificationVoList = new ArrayList<>();

        for (SugoGoodsSpecification goodsSpecification : goodsSpecificationList) {
            String specification = goodsSpecification.getSpecification();
            VO goodsSpecificationVo = map.get(specification);
            if (goodsSpecificationVo == null) {
                goodsSpecificationVo = new VO();
                goodsSpecificationVo.setName(specification);
                List<SugoGoodsSpecification> valueList = new ArrayList<>();
                valueList.add(goodsSpecification);
                goodsSpecificationVo.setValueList(valueList);
                map.put(specification, goodsSpecificationVo);
                specificationVoList.add(goodsSpecificationVo);
            } else {
                List<SugoGoodsSpecification> valueList = goodsSpecificationVo.getValueList();
                valueList.add(goodsSpecification);
            }
        }

        return specificationVoList;
    }

    public void updateById(SugoGoodsSpecification goodsSpecification) {
        goodsSpecification.setUpdateTime(LocalDateTime.now());
        goodsSpecificationMapper.updateByPrimaryKeySelective(goodsSpecification);
    }

    private class VO {
        private String name;
        private List<SugoGoodsSpecification> valueList;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<SugoGoodsSpecification> getValueList() {
            return valueList;
        }

        public void setValueList(List<SugoGoodsSpecification> valueList) {
            this.valueList = valueList;
        }
    }

}