package com.sugo.wx.task;

import com.sugo.common.system.SystemConfig;
import com.sugo.common.task.Task;
import com.sugo.common.util.BeanUtil;
import com.sugo.sql.dao.SugoOrderMapper;
import com.sugo.sql.entity.SugoOrder;
import com.sugo.sql.entity.SugoOrderGoods;
import com.sugo.sql.service.goods.GoodsProductService;
import com.sugo.sql.service.order.OrderGoodsService;
import com.sugo.sql.service.order.OrderService;
import com.sugo.sql.util.OrderUtil;
import io.swagger.models.auth.In;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class OrderUnpaidTask extends Task {
    private final Log logger = LogFactory.getLog(OrderUnpaidTask.class);

    private int orderId = -1;

    public OrderUnpaidTask(Integer orderId, long delayInMilliseconds) {
        super("OrderUnpaidTask->" + orderId, delayInMilliseconds);
        this.orderId = orderId;
    }

    public OrderUnpaidTask(Integer orderId) {
        super("OrderUnpaidTask->" + orderId, SystemConfig.getOrderUnpaid() * 60 * 1000);
        this.orderId = orderId;
    }

    @Override
    public void run() {
        logger.info("系统开始处理延时任务-->订单超时未付款-->取消 " + this.orderId);

        OrderService orderService = BeanUtil.getBean(OrderService.class);
        OrderGoodsService  orderGoodsService = BeanUtil.getBean(OrderGoodsService.class);
        GoodsProductService goodsProductService = BeanUtil.getBean(GoodsProductService.class);

        SugoOrder order = orderService.findById(this.orderId);
        if(order == null) {
            return;
        }

        if(!OrderUtil.isCreateStatus(order)) {
            return;
        }

        // 修改订单状态为取消
        order.setOrderStatus(OrderUtil.STATUS_AUTO_CANCEL);
        order.setEndTime(LocalDateTime.now());

        if(orderService.updateWithOptimisticLocker(order) == 0) {
            throw new RuntimeException("更新数据失效!");
        }

        // 增加商品的库存
        Integer orderId = order.getId();
        List<SugoOrderGoods> orderGoodsList = orderGoodsService.queryByOid(orderId);
        for (SugoOrderGoods orderGoods : orderGoodsList) {
            Integer productId = orderGoods.getProductId();
            Short num = orderGoods.getNumber();
            if(goodsProductService.addStock(productId, num) == 0) {
                throw new RuntimeException("商品库存增加失败");
            }
        }
        logger.info("系统结束处理延时任务-->订单超时未付款-->取消 " + this.orderId);
    }

}