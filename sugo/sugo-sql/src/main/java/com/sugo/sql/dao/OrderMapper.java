package com.sugo.sql.dao;

import com.sugo.sql.entity.SugoOrder;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

public interface OrderMapper {
    int updateWithOptimisticLocker(@Param("lastUpdateTime") LocalDateTime lastUpdateTime,
                                   @Param("order") SugoOrder order);

    int updateOrderPayStatus(@Param("id") Integer id,
                             @Param("orderStatus") Short orderStatus,
                             @Param("updateTime") LocalDateTime updateTime);
}