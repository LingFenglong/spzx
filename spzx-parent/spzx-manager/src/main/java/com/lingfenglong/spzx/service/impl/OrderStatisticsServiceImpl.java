package com.lingfenglong.spzx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingfenglong.spzx.mapperplus.OrderStatisticsMapper;
import com.lingfenglong.spzx.model.dto.order.OrderStatisticsDto;
import com.lingfenglong.spzx.model.entity.order.OrderStatistics;
import com.lingfenglong.spzx.model.vo.order.OrderStatisticsVo;
import com.lingfenglong.spzx.service.OrderStatisticsService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

@Service
public class OrderStatisticsServiceImpl
        extends ServiceImpl<OrderStatisticsMapper, OrderStatistics>
        implements OrderStatisticsService {

    @Override
    public OrderStatisticsVo getOrderStatisticsData(OrderStatisticsDto orderStatisticsDto) {
        ArrayList<String> dateList = new ArrayList<>();
        ArrayList<BigDecimal> amountList = new ArrayList<>();
        LambdaQueryWrapper<OrderStatistics> wrapper = new LambdaQueryWrapper<OrderStatistics>()
                .select(OrderStatistics::getOrderDate, OrderStatistics::getTotalNum, OrderStatistics::getTotalAmount)
                .ge(orderStatisticsDto.getCreateTimeBegin() != null, OrderStatistics::getOrderDate, orderStatisticsDto.getCreateTimeBegin())
                .le(orderStatisticsDto.getCreateTimeEnd() != null, OrderStatistics::getOrderDate, orderStatisticsDto.getCreateTimeEnd());

        baseMapper.selectList(wrapper)
                .forEach(orderStatistics -> {
                    dateList.add(SimpleDateFormat.getDateInstance().format(orderStatistics.getOrderDate()));
                    amountList.add(orderStatistics.getTotalAmount());
                });
        OrderStatisticsVo orderStatisticsVo = new OrderStatisticsVo();
        orderStatisticsVo.setDateList(dateList);
        orderStatisticsVo.setAmountList(amountList);
        return orderStatisticsVo;
    }
}
