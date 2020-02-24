package com.sugo.sql.service.admin;

import com.sugo.sql.dao.SugoSystemMapper;
import com.sugo.sql.entity.SugoSystem;
import com.sugo.sql.entity.SugoSystemExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SystemConfigService {
    @Resource
    private SugoSystemMapper systemMapper;

    /**
     * 查询所有配置信息
     * @return
     */
    public Map<String, String> queryAll() {
        SugoSystemExample example = new SugoSystemExample();
        example.or().andDeletedEqualTo(false);

        List<SugoSystem> systemList = systemMapper.selectByExample(example);
        Map<String, String> systemConfigs = new HashMap<>();
        for (SugoSystem item : systemList) {
            systemConfigs.put(item.getKeyName(), item.getKeyValue());
        }

        return systemConfigs;
    }

    /**
     * 查询商城的基本信息配置项
     * @return
     */
    public Map<String, String> listMail() {
        SugoSystemExample example = new SugoSystemExample();
        example.or().andKeyNameLike("sugo_mall_%").andDeletedEqualTo(false);
        List<SugoSystem> systemList = systemMapper.selectByExample(example);
        Map<String, String> data = new HashMap<>();
        for(SugoSystem system : systemList){
            data.put(system.getKeyName(), system.getKeyValue());
        }
        return data;
    }

    /**
     * 查询微信配置项
     * @return
     */
    public Map<String, String> listWx() {
        SugoSystemExample example = new SugoSystemExample();
        example.or().andKeyNameLike("sugo_wx_%").andDeletedEqualTo(false);
        List<SugoSystem> systemList = systemMapper.selectByExample(example);
        Map<String, String> data = new HashMap<>();
        for(SugoSystem system : systemList){
            data.put(system.getKeyName(), system.getKeyValue());
        }
        return data;
    }

    /**
     * 查询订单配置项
     * @return
     */
    public Map<String, String> listOrder() {
        SugoSystemExample example = new SugoSystemExample();
        example.or().andKeyNameLike("sugo_order_%").andDeletedEqualTo(false);
        List<SugoSystem> systemList = systemMapper.selectByExample(example);
        Map<String, String> data = new HashMap<>();
        for(SugoSystem system : systemList){
            data.put(system.getKeyName(), system.getKeyValue());
        }
        return data;
    }

    /**
     * 查询物流配置项
     * @return
     */
    public Map<String, String> listExpress() {
        SugoSystemExample example = new SugoSystemExample();
        example.or().andKeyNameLike("sugo_express_%").andDeletedEqualTo(false);
        List<SugoSystem> systemList = systemMapper.selectByExample(example);
        Map<String, String> data = new HashMap<>();
        for(SugoSystem system : systemList){
            data.put(system.getKeyName(), system.getKeyValue());
        }
        return data;
    }

    /**
     * 修改配置
     * @param data
     */
    public void updateConfig(Map<String, String> data) {
        for (Map.Entry<String, String> entry : data.entrySet()) {
            SugoSystemExample example = new SugoSystemExample();
            example.or().andKeyNameEqualTo(entry.getKey()).andDeletedEqualTo(false);

            SugoSystem system = new SugoSystem();
            system.setKeyName(entry.getKey());
            system.setKeyValue(entry.getValue());
            system.setUpdateTime(LocalDateTime.now());
            systemMapper.updateByExampleSelective(system, example);
        }

    }

    /**
     * 添加配置
     * @param key
     * @param value
     */
    public void addConfig(String key, String value) {
        SugoSystem system = new SugoSystem();
        system.setKeyName(key);
        system.setKeyValue(value);
        system.setAddTime(LocalDateTime.now());
        system.setUpdateTime(LocalDateTime.now());
        systemMapper.insertSelective(system);
    }
}