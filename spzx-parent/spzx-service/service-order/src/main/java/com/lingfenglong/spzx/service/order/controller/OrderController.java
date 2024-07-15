package com.lingfenglong.spzx.service.order.controller;

import com.lingfenglong.spzx.model.vo.common.CommonResultCode;
import com.lingfenglong.spzx.model.vo.common.Result;
import com.lingfenglong.spzx.model.vo.h5.TradeVo;
import com.lingfenglong.spzx.service.order.service.impl.OrderServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "订单接口")
@RestController
@RequestMapping("/api/order/orderInfo")
public class OrderController {

    private final OrderServiceImpl orderServiceImpl;

    public OrderController(OrderServiceImpl orderServiceImpl) {
        this.orderServiceImpl = orderServiceImpl;
    }

    @Operation(summary = "确认下单")
    @GetMapping("/auth/trade")
    public Result<TradeVo> trade() {
        TradeVo tradeVo = orderServiceImpl.trade();
        return Result.build(tradeVo, CommonResultCode.SUCCESS);
    }
}
