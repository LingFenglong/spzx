package com.lingfenglong.spzx.service.cart.service;

import com.lingfenglong.spzx.model.entity.h5.CartInfo;

import java.util.List;

public interface CartInfoService {
    void addToCart(Long skuId, Integer skuNum);

    List<CartInfo> cartList();

    void deleteCart(Long skuId);

    void checkCart(Long skuId, Integer isChecked);

    void allCheckCart(Integer isChecked);

    void clearCart();

    List<CartInfo> getAllChecked();
}
