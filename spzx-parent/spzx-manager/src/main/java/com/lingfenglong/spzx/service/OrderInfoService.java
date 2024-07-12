package com.lingfenglong.spzx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lingfenglong.spzx.model.entity.order.OrderInfo;
import com.lingfenglong.spzx.model.entity.order.OrderStatistics;

public interface OrderInfoService extends IService<OrderInfo> {
    OrderStatistics getOrderStatisticsYesterday();
}
