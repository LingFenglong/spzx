package com.lingfenglong.spzx.service.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lingfenglong.spzx.model.entity.order.OrderInfo;
import com.lingfenglong.spzx.model.vo.h5.TradeVo;

public interface OrderInfoService extends IService<OrderInfo> {
    TradeVo trade();
}
