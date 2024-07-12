package com.lingfenglong.spzx.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingfenglong.spzx.mapperplus.OrderInfoMapper;
import com.lingfenglong.spzx.model.entity.order.OrderInfo;
import com.lingfenglong.spzx.model.entity.order.OrderStatistics;
import com.lingfenglong.spzx.service.OrderInfoService;
import org.springframework.stereotype.Service;

@Service
public class OrderInfoServiceImpl
        extends ServiceImpl<OrderInfoMapper, OrderInfo>
        implements OrderInfoService {

    @Override
    public OrderStatistics getOrderStatisticsYesterday() {
        return baseMapper.getOrderStatisticsYesterday();
    }
}
