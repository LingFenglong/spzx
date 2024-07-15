package com.lingfenglong.spzx.service.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingfenglong.spzx.feign.cart.CartFeignClient;
import com.lingfenglong.spzx.model.entity.h5.CartInfo;
import com.lingfenglong.spzx.model.entity.order.OrderInfo;
import com.lingfenglong.spzx.model.entity.order.OrderItem;
import com.lingfenglong.spzx.model.vo.h5.TradeVo;
import com.lingfenglong.spzx.service.order.mapper.OrderInfoMapper;
import com.lingfenglong.spzx.service.order.service.OrderInfoService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class OrderServiceImpl
        extends ServiceImpl<OrderInfoMapper, OrderInfo>
        implements OrderInfoService {

    private final CartFeignClient cartFeignClient;

    public OrderServiceImpl(CartFeignClient cartFeignClient) {
        this.cartFeignClient = cartFeignClient;
    }

    @Override
    public TradeVo trade() {
        AtomicReference<BigDecimal> totalAmount = new AtomicReference<>();
        totalAmount.set(BigDecimal.ZERO);

        List<OrderItem> orderItemList = cartFeignClient.getAllChecked().getData()
                .stream()
                .peek(cartInfo -> totalAmount
                        .accumulateAndGet(
                                cartInfo.getCartPrice().multiply(new BigDecimal(cartInfo.getSkuNum())),
                                BigDecimal::add
                        )
                )
                .map(cartInfo -> {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setSkuId(cartInfo.getSkuId());
                    orderItem.setSkuName(cartInfo.getSkuName());
                    orderItem.setThumbImg(cartInfo.getImgUrl());
                    orderItem.setSkuPrice(cartInfo.getCartPrice());
                    orderItem.setSkuNum(cartInfo.getSkuNum());
                    return orderItem;
                })
                .toList();

        TradeVo tradeVo = new TradeVo();
        tradeVo.setTotalAmount(totalAmount.get());
        tradeVo.setOrderItemList(orderItemList);

        return tradeVo;
    }
}
