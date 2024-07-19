package com.lingfenglong.spzx.service.pay.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingfenglong.spzx.feign.order.OrderFeignClient;
import com.lingfenglong.spzx.model.entity.order.OrderInfo;
import com.lingfenglong.spzx.model.entity.order.OrderItem;
import com.lingfenglong.spzx.model.entity.pay.PaymentInfo;
import com.lingfenglong.spzx.service.pay.mapper.PaymentInfoMapper;
import com.lingfenglong.spzx.service.pay.service.PaymentInfoService;
import org.springframework.stereotype.Service;

@Service
public class PaymentInfoServiceImpl
        extends ServiceImpl<PaymentInfoMapper, PaymentInfo>
        implements PaymentInfoService {
    private final OrderFeignClient orderFeignClient;

    public PaymentInfoServiceImpl(OrderFeignClient orderFeignClient) {
        this.orderFeignClient = orderFeignClient;
    }

    @Override
    public PaymentInfo savePaymentInfo(String orderNo) {
        PaymentInfo paymentInfoByOrderNo = baseMapper.getPaymentInfoByOrderNo(orderNo);
        if (paymentInfoByOrderNo == null) {
            OrderInfo orderInfo = orderFeignClient.getOrderInfoByOrderNo(orderNo).getData();
            PaymentInfo paymentInfo = new PaymentInfo();

            paymentInfo.setUserId(orderInfo.getUserId());
            paymentInfo.setPayType(orderInfo.getPayType());
            StringBuilder content = new StringBuilder();
            for(OrderItem item : orderInfo.getOrderItemList()) {
                content.append(item.getSkuName()).append(" ");
            }
            paymentInfo.setContent(content.toString());
            paymentInfo.setAmount(orderInfo.getTotalAmount());
            paymentInfo.setOrderNo(orderNo);
            paymentInfo.setPaymentStatus(0);

            baseMapper.insert(paymentInfo);
        }
        return paymentInfoByOrderNo;
    }
}
