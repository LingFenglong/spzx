package com.lingfenglong.spzx.feign.order;

import com.lingfenglong.spzx.model.entity.order.OrderInfo;
import com.lingfenglong.spzx.model.vo.common.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(path = "/api/order/orderInfo", name = "order-service")
public interface OrderFeignClient {

    @GetMapping("/auth/getOrderInfoByOrderNo/{orderNo}")
    Result<OrderInfo> getOrderInfoByOrderNo(@PathVariable("orderNo") String orderNo);

}
