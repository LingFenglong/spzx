package com.lingfenglong.spzx.service.pay.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lingfenglong.spzx.model.entity.pay.PaymentInfo;

public interface PaymentInfoService extends IService<PaymentInfo> {
    PaymentInfo savePaymentInfo(String orderNo);
}
