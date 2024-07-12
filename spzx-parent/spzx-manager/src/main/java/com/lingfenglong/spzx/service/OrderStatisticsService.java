package com.lingfenglong.spzx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lingfenglong.spzx.model.dto.order.OrderStatisticsDto;
import com.lingfenglong.spzx.model.entity.order.OrderStatistics;
import com.lingfenglong.spzx.model.vo.order.OrderStatisticsVo;

public interface OrderStatisticsService extends IService<OrderStatistics> {
    OrderStatisticsVo getOrderStatisticsData(OrderStatisticsDto orderStatisticsDto);
}
