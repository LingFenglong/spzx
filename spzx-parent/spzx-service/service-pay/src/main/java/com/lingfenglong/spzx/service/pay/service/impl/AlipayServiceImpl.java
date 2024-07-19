package com.lingfenglong.spzx.service.pay.service.impl;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradePayRequest;
import com.alipay.api.response.AlipayTradePayResponse;
import com.lingfenglong.spzx.common.exception.CommonGlobalRuntimeException;
import com.lingfenglong.spzx.model.entity.pay.PaymentInfo;
import com.lingfenglong.spzx.model.vo.common.CommonResultCode;
import com.lingfenglong.spzx.service.pay.properties.AlipayProperties;
import com.lingfenglong.spzx.service.pay.service.AlipayService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;

@Slf4j
@Service
public class AlipayServiceImpl implements AlipayService {

    private final PaymentInfoServiceImpl paymentInfoServiceImpl;
    private final AlipayProperties alipayProperties;
    private final AlipayClient alipayClient;

    public AlipayServiceImpl(PaymentInfoServiceImpl paymentInfoServiceImpl, AlipayProperties alipayProperties, AlipayClient alipayClient) {
        this.paymentInfoServiceImpl = paymentInfoServiceImpl;
        this.alipayProperties = alipayProperties;
        this.alipayClient = alipayClient;
    }

    @SneakyThrows
    @Override
    public String submitAlipay(String orderNo) {
        // 保存支付记录
        PaymentInfo paymentInfo = paymentInfoServiceImpl.savePaymentInfo(orderNo);

        // 创建Api对应的request
        AlipayTradePayRequest alipayTradePayRequest = new AlipayTradePayRequest();

        alipayTradePayRequest.setReturnUrl(alipayProperties.returnPaymentUrl);

        alipayTradePayRequest.setNotifyUrl(alipayProperties.notifyPaymentUrl);

        // 准备请求参数 ，声明一个map 集合
        HashMap<String, Object> map = new HashMap<>();
        map.put("out_trade_no", paymentInfo.getOrderNo());
        map.put("product_code", "QUICK_WAP_WAY");
        // map.put("total_amount",paymentInfo.getAmount());
        map.put("total_amount", new BigDecimal("0.01"));
        map.put("subject", paymentInfo.getContent());
        alipayTradePayRequest.setBizContent(JSON.toJSONString(map));

        // 发送请求
        AlipayTradePayResponse response = alipayClient.pageExecute(alipayTradePayRequest);
        if (response.isSuccess()) {
            log.info("调用成功");
            return response.getBody();
        } else {
            log.info("调用失败");
            throw new CommonGlobalRuntimeException(CommonResultCode.DATA_ERROR);
        }

    }


    @Override
    public PaymentInfo submitMyAlipay(String orderNo) {
        // 保存支付记录
        return paymentInfoServiceImpl.savePaymentInfo(orderNo);
    }

    @Override
    public void updatePaymentStatus() {

    }
}
