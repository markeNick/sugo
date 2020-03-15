package com.sugo.sql.dao;

import org.apache.ibatis.annotations.Param;

public interface GoodsMapper {
    long countGoodsByAdCode(@Param("adcode")String adcode);
}
