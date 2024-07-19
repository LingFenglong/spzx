package com.lingfenglong.spzx.service.pay.service;

import com.lingfenglong.spzx.model.entity.pay.PaymentInfo;

public interface AlipayService {
    String submitAlipay(String orderNo);

    PaymentInfo submitMyAlipay(String orderNo);

    void updatePaymentStatus();
}
