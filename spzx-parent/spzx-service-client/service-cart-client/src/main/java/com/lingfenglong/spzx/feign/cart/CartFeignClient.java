package com.lingfenglong.spzx.feign.cart;

import com.lingfenglong.spzx.model.entity.h5.CartInfo;
import com.lingfenglong.spzx.model.vo.common.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
@FeignClient(path = "/api/order/cart", name = "cart-service")
public interface CartFeignClient {

    @GetMapping("/auth/getAllCkecked")
    Result<List<CartInfo>> getAllChecked();

    @GetMapping("/auth/deleteChecked")
    Result<?> deleteChecked();
}
