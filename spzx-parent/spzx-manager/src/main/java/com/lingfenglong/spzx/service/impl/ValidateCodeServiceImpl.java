package com.lingfenglong.spzx.service.impl;

import cn.hutool.captcha.ShearCaptcha;
import com.lingfenglong.spzx.common.RedisPrefix;
import com.lingfenglong.spzx.model.vo.system.ValidateCodeVo;
import com.lingfenglong.spzx.service.ValidateCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class ValidateCodeServiceImpl implements ValidateCodeService {
    private final StringRedisTemplate redisTemplate;

    @Autowired
    public ValidateCodeServiceImpl(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public ValidateCodeVo generateValidateCode() {
        // 使用 hutool 工具包生成验证码图片
        ShearCaptcha shearCaptcha = new ShearCaptcha(150, 48, 4, 4);

        // 生成对应的 UUID，存入 redis
        String id = UUID.randomUUID().toString().replaceAll("-", "");
        redisTemplate.opsForValue().set(RedisPrefix.VALIDATE_CODE + id, shearCaptcha.getCode(), 1, TimeUnit.MINUTES);

        // 返回验证码对象
        ValidateCodeVo validateCodeVo = new ValidateCodeVo();
        validateCodeVo.setCodeKey(id);
        validateCodeVo.setCodeValue(shearCaptcha.getImageBase64());
        return validateCodeVo;
    }
}
