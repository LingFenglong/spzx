package com.lingfenglong.spzx.service.pay.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lingfenglong.spzx.model.entity.pay.PaymentInfo;

public interface PaymentInfoMapper extends BaseMapper<PaymentInfo> {

    default PaymentInfo getPaymentInfoByOrderNo(String orderNo) {
        LambdaQueryWrapper<PaymentInfo> wrapper = new LambdaQueryWrapper<PaymentInfo>()
                .eq(PaymentInfo::getOrderNo, orderNo);
        return this.selectOne(wrapper);
    }
}
