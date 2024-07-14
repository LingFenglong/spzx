package com.lingfenglong.spzx.service.cart.controller;

import com.lingfenglong.spzx.model.entity.h5.CartInfo;
import com.lingfenglong.spzx.model.vo.common.CommonResultCode;
import com.lingfenglong.spzx.model.vo.common.Result;
import com.lingfenglong.spzx.service.cart.service.impl.CartServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "购物车接口")
@RestController
@RequestMapping("/api/order/cart")
public class CartController {

    private final CartServiceImpl cartServiceImpl;

    public CartController(CartServiceImpl cartServiceImpl) {
        this.cartServiceImpl = cartServiceImpl;
    }

    @GetMapping("/auth/addToCart/{skuId}/{skuNum}")
    public Result<?> addToCart(
            @PathVariable("skuId") Long skuId,
            @PathVariable("skuNum") Integer num) {
        cartServiceImpl.addToCart(skuId, num);
        return Result.build(null, CommonResultCode.SUCCESS);
    }

    @GetMapping("/auth/cartList")
    public Result<List<CartInfo>> cartList() {
        List<CartInfo> cartInfoList = cartServiceImpl.cartList();
        return Result.build(cartInfoList, CommonResultCode.SUCCESS);
    }

    @DeleteMapping("/auth/deleteCart/{skuId}")
    public Result<?> deleteCart(@PathVariable("skuId") Long skuId) {
        cartServiceImpl.deleteCart(skuId);
        return Result.build(null, CommonResultCode.SUCCESS);
    }

    @GetMapping("/auth/checkCart/{skuId}/{isChecked}")
    public Result<?> checkCart(
            @PathVariable("skuId") Long skuId,
            @PathVariable("isChecked") Integer isChecked) {
        cartServiceImpl.checkCart(skuId, isChecked);
        return Result.build(null, CommonResultCode.SUCCESS);
    }

    @GetMapping("/auth/allCheckCart/{isChecked}")
    public Result<?> allCheckCart(@PathVariable Integer isChecked) {
        cartServiceImpl.allCheckCart(isChecked);
        return Result.build(null, CommonResultCode.SUCCESS);
    }

    @GetMapping("/auth/clearCart")
    public Result<?> clearCart() {
        cartServiceImpl.clearCart();
        return Result.build(null, CommonResultCode.SUCCESS);
    }
}
