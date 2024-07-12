package com.lingfenglong.spzx.controller;

import com.lingfenglong.spzx.log.annotation.Log;
import com.lingfenglong.spzx.log.enums.BusinessType;
import com.lingfenglong.spzx.log.enums.OperatorType;
import com.lingfenglong.spzx.model.dto.order.OrderStatisticsDto;
import com.lingfenglong.spzx.model.vo.common.CommonResultCode;
import com.lingfenglong.spzx.model.vo.common.Result;
import com.lingfenglong.spzx.model.vo.order.OrderStatisticsVo;
import com.lingfenglong.spzx.service.impl.OrderStatisticsServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@Tag(name = "订单信息")
@RequestMapping("/admin/order/orderInfo")
@RestController
public class OrderInfoController {

    private final OrderStatisticsServiceImpl orderStatisticsServiceImpl;

    public OrderInfoController(OrderStatisticsServiceImpl orderStatisticsServiceImpl) {
        this.orderStatisticsServiceImpl = orderStatisticsServiceImpl;
    }

    @Log(title = "获得订单统计信息", operatorType = OperatorType.MANAGER, businessType = BusinessType.SELECT)
    @Operation(summary = "获得订单统计信息")
    @PostMapping("/getOrderStatisticsData")
    public Result<OrderStatisticsVo> getOrderStatisticsData(@RequestBody OrderStatisticsDto orderStatisticsDto) {
        OrderStatisticsVo orderStatisticsVo = orderStatisticsServiceImpl.getOrderStatisticsData(orderStatisticsDto);
        return Result.build(orderStatisticsVo, CommonResultCode.SUCCESS);
    }
}
