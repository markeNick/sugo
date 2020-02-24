package com.sugo.wx.task;

import com.sugo.common.system.SystemConfig;
import com.sugo.common.task.TaskService;
import com.sugo.sql.entity.SugoOrder;
import com.sugo.sql.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class TaskStartupRunner implements ApplicationRunner {

    @Autowired
    private OrderService orderService;

    @Autowired
    private TaskService taskService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<SugoOrder> orderList = orderService.queryUnpaid(SystemConfig.getOrderUnpaid());
        for (SugoOrder order : orderList) {
            LocalDateTime add = order.getAddTime();
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime expire = add.plusMinutes(SystemConfig.getOrderUnpaid());

            if(expire.isBefore(now)) {
                // 过期，则加入延迟队列
                taskService.addTask(new OrderUnpaidTask(order.getId(), 0));
            } else {
                long delay = ChronoUnit.MILLIS.between(now, expire);
                taskService.addTask(new OrderUnpaidTask(order.getId(), delay));
            }
        }
    }
}