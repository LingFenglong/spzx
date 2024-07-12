package com.lingfenglong.spzx.scheduler;

import com.lingfenglong.spzx.service.impl.OrderInfoServiceImpl;
import com.lingfenglong.spzx.service.impl.OrderStatisticsServiceImpl;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class OrderScheduler {

    private final OrderStatisticsServiceImpl orderStatisticsServiceImpl;
    private final OrderInfoServiceImpl orderInfoServiceImpl;

    public OrderScheduler(OrderStatisticsServiceImpl orderStatisticsServiceImpl, OrderInfoServiceImpl orderInfoServiceImpl) {
        this.orderStatisticsServiceImpl = orderStatisticsServiceImpl;
        this.orderInfoServiceImpl = orderInfoServiceImpl;
    }

    /**
     * 每天凌晨两点统计昨天的订单
     */
    @Scheduled(cron = "0 0 2 * * ?")
    public void orderStatisticsScheduler() {
        orderStatisticsServiceImpl.save(
                orderInfoServiceImpl.getOrderStatisticsYesterday()
        );
    }
}
