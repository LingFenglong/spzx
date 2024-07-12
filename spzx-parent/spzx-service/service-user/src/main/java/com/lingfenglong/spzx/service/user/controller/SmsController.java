package com.lingfenglong.spzx.service.user.controller;

import com.lingfenglong.spzx.model.vo.common.CommonResultCode;
import com.lingfenglong.spzx.model.vo.common.Result;
import com.lingfenglong.spzx.service.user.service.impl.SmsServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "发送消息")
@RestController
@RequestMapping("/api/user/sms")
public class SmsController {

    private final SmsServiceImpl smsServiceImpl;

    public SmsController(SmsServiceImpl smsServiceImpl) {
        this.smsServiceImpl = smsServiceImpl;
    }

    @GetMapping("/sendCode/{phone}")
    public Result<?> sendCode(@PathVariable String phone) {
        smsServiceImpl.sendCode(phone);
        return Result.build(null, CommonResultCode.SUCCESS);
    }
}
