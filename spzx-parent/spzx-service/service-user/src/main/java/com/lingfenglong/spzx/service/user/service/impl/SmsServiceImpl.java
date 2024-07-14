package com.lingfenglong.spzx.service.user.service.impl;

import com.lingfenglong.spzx.util.constant.RedisConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class SmsServiceImpl {

    private final StringRedisTemplate stringRedisTemplate;

    public SmsServiceImpl(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public void sendCode(String phone) {
        String code = RandomStringUtils.randomNumeric(4);
        stringRedisTemplate
                .opsForValue()
                .set(RedisConstant.KEY_USER_PHONE_CODE + phone, code, 15, TimeUnit.MINUTES);

        sendCodeMessage(code);
    }

    /**
     * 发送手机验证码
     * @param code 随机生成的手机验证码
     */
    private void sendCodeMessage(String code) {
        log.debug("验证码为：{}", code);
    }
}
