package com.lingfenglong.spzx.service.order.controller;

import com.github.pagehelper.PageInfo;
import com.lingfenglong.spzx.model.dto.h5.OrderInfoDto;
import com.lingfenglong.spzx.model.entity.order.OrderInfo;
import com.lingfenglong.spzx.model.vo.common.CommonResultCode;
import com.lingfenglong.spzx.model.vo.common.Result;
import com.lingfenglong.spzx.model.vo.h5.TradeVo;
import com.lingfenglong.spzx.service.order.service.impl.OrderServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@Tag(name = "订单接口")
@RestController
@RequestMapping("/api/order/orderInfo")
public class OrderInfoController {

    private final OrderServiceImpl orderServiceImpl;

    public OrderInfoController(OrderServiceImpl orderServiceImpl) {
        this.orderServiceImpl = orderServiceImpl;
    }

    @Operation(summary = "确认下单")
    @GetMapping("/auth/trade")
    public Result<TradeVo> trade() {
        TradeVo tradeVo = orderServiceImpl.trade();
        return Result.build(tradeVo, CommonResultCode.SUCCESS);
    }
    
    @PostMapping("/auth/submitOrder")
    public Result<Long> submitOrder(@RequestBody OrderInfoDto orderInfoDto) {
        Long orderId = orderServiceImpl.submitOrder(orderInfoDto);
        return Result.build(orderId, CommonResultCode.SUCCESS);
    }

    @GetMapping("/auth/{orderId}")
    public Result<OrderInfo> getOrderInfo(@PathVariable("orderId") Long orderId) {
        OrderInfo orderInfo = orderServiceImpl.getOrderInfo(orderId);
        return Result.build(orderInfo, CommonResultCode.SUCCESS);
    }

    @GetMapping("/auth/buy/{skuId}")
    public Result<TradeVo> buy(@PathVariable("skuId") Long skuId) {
        TradeVo tradeVo = orderServiceImpl.buy(skuId);
        return Result.build(tradeVo, CommonResultCode.SUCCESS);
    }

    @GetMapping("/auth/{pageNum}/{pageSize}")
    public Result<PageInfo<OrderInfo>> getOrderInfoPage(
            @PathVariable("pageNum") Integer pageNum,
            @PathVariable("pageSize") Integer pageSize,
            Integer orderStatus) {
        PageInfo<OrderInfo> orderInfoPage = orderServiceImpl.getOrderInfoPage(pageNum, pageSize, orderStatus);
        return Result.build(orderInfoPage, CommonResultCode.SUCCESS);
    }
    
    @GetMapping("/auth/getOrderInfoByOrderNo/{orderNo}")
    public Result<OrderInfo> getOrderInfoByOrderNo(@PathVariable("orderNo") String orderNo) {
        OrderInfo orderInfo = orderServiceImpl.getOrderInfoByOrderNo(orderNo);
        return Result.build(orderInfo, CommonResultCode.SUCCESS);
    }
}
