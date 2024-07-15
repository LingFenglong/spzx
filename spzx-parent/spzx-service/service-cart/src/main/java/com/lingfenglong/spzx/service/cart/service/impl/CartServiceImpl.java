package com.lingfenglong.spzx.service.cart.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson2.JSON;
import com.lingfenglong.spzx.feign.product.ProductFeignClient;
import com.lingfenglong.spzx.model.entity.h5.CartInfo;
import com.lingfenglong.spzx.model.entity.product.ProductSku;
import com.lingfenglong.spzx.service.cart.service.CartInfoService;
import com.lingfenglong.spzx.util.AuthContextUtil;
import com.lingfenglong.spzx.util.constant.RedisConstant;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CartServiceImpl implements CartInfoService {

    private final RedisTemplate<String, JSONObject> redisTemplate;
    private final ProductFeignClient productFeignClient;

    public CartServiceImpl(RedisTemplate<String, JSONObject> redisTemplate, ProductFeignClient productFeignClient) {
        this.redisTemplate = redisTemplate;
        this.productFeignClient = productFeignClient;
    }

    @Override
    public void addToCart(Long skuId, Integer skuNum) {
        Long userId = AuthContextUtil.getUserInfo().getId();
        Object cartObj = redisTemplate.opsForHash()
                .get(RedisConstant.KEY_USER_CART + userId, skuId.toString());

        CartInfo cartInfo;

        if (cartObj != null) {
            cartInfo = JSON.parseObject(cartObj.toString(), CartInfo.class);
            cartInfo.setIsChecked(1);
            cartInfo.setSkuNum(cartInfo.getSkuNum() + skuNum);
            cartInfo.setCreateTime(new Date());
        } else {
            // Open Feign 远程调用，获取 productSku
            ProductSku productSku = productFeignClient.getBySkuId(skuId).getData();

            cartInfo = CartInfo.builder()
                    .cartPrice(productSku.getSalePrice())
                    .skuNum(skuNum)
                    .skuId(skuId)
                    .userId(userId)
                    .imgUrl(productSku.getThumbImg())
                    .skuName(productSku.getSkuName())
                    .isChecked(1)
                    .build();

            cartInfo.setCreateTime(new Date());
            cartInfo.setUpdateTime(new Date());
        }

        redisTemplate.opsForHash()
                .put(RedisConstant.KEY_USER_CART + userId, skuId.toString(), cartInfo);
    }

    @Override
    public List<CartInfo> cartList() {
        Long userId = AuthContextUtil.getUserInfo().getId();
        return redisTemplate.opsForHash()
                .values(RedisConstant.KEY_USER_CART + userId)
                .stream()
                .map(cartInfoObj -> JSON.parseObject(cartInfoObj.toString(), CartInfo.class))
                .sorted(Comparator.comparing(CartInfo::getCreateTime).reversed())
                .toList();
    }

    @Override
    public void deleteCart(Long skuId) {
        Long userId = AuthContextUtil.getUserInfo().getId();
        redisTemplate.opsForHash()
                .delete(RedisConstant.KEY_USER_CART + userId, skuId.toString());
    }

    @Override
    public void checkCart(Long skuId, Integer isChecked) {
        Long userId = AuthContextUtil.getUserInfo().getId();
        Optional.ofNullable(redisTemplate.opsForHash()
                .get(RedisConstant.KEY_USER_CART + userId, skuId.toString()))
                .map(cartInfoObj -> JSON.parseObject(cartInfoObj.toString(), CartInfo.class))
                .ifPresent(cartInfo -> {
                    cartInfo.setIsChecked(isChecked);
                    redisTemplate.opsForHash()
                            .put(RedisConstant.KEY_USER_CART + userId, skuId.toString(), cartInfo);
                });
    }

    @Override
    public void allCheckCart(Integer isChecked) {
        Long userId = AuthContextUtil.getUserInfo().getId();
        redisTemplate.opsForHash()
                .values(RedisConstant.KEY_USER_CART + userId)
                .stream()
                .map(cartInfoObj -> JSON.parseObject(cartInfoObj.toString(), CartInfo.class))
                .forEach(cartInfo -> {
                    cartInfo.setIsChecked(isChecked);
                    redisTemplate.opsForHash()
                            .put(RedisConstant.KEY_USER_CART + userId, cartInfo.toString(), cartInfo);
                });
    }

    @Override
    public void clearCart() {
        Long userId = AuthContextUtil.getUserInfo().getId();
        redisTemplate.delete(RedisConstant.KEY_USER_CART + userId);
    }

    @Override
    public List<CartInfo> getAllChecked() {
        return redisTemplate.opsForHash()
                .values(RedisConstant.KEY_USER_CART + AuthContextUtil.getUserInfo().getId())
                .stream()
                .map(cartInfoObj -> JSON.parseObject(cartInfoObj.toString(), CartInfo.class))
                .filter(cartInfo -> cartInfo.getIsChecked() == 1)
                .sorted(Comparator.comparing(CartInfo::getCreateTime).reversed())
                .toList();
    }

    @Override
    public void deleteChecked() {
        getAllChecked().forEach(cartInfo ->
                redisTemplate.opsForHash()
                        .delete(RedisConstant.KEY_USER_CART + AuthContextUtil.getUserInfo().getId(), cartInfo.getSkuId().toString())
        );
    }
}
