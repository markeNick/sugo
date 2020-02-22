package com.sugo.sql.service;

import com.sugo.sql.dao.SugoAdMapper;
import com.sugo.sql.entity.SugoAd;
import com.sugo.sql.entity.SugoAdExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AdService {

    @Resource
    private SugoAdMapper sugoAdMapper;

    public List<SugoAd> queryIndex() {
        SugoAdExample example = new SugoAdExample();
        example.or().andPositionEqualTo((byte) 1).andDeletedEqualTo(false).andEnabledEqualTo(true);
        return sugoAdMapper.selectByExample(example);
    }

}