package com.lingfenglong.spzx.service.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.lingfenglong.spzx.model.dto.h5.OrderInfoDto;
import com.lingfenglong.spzx.model.entity.order.OrderInfo;
import com.lingfenglong.spzx.model.vo.h5.TradeVo;

public interface OrderInfoService extends IService<OrderInfo> {
    TradeVo trade();

    Long submitOrder(OrderInfoDto orderInfoDto);

    OrderInfo getOrderInfo(Long orderId);

    TradeVo buy(Long skuId);

    PageInfo<OrderInfo> getOrderInfoPage(Integer pageNum, Integer pageSize, Integer orderStatus);

    OrderInfo getOrderInfoByOrderNo(String orderNo);
}
