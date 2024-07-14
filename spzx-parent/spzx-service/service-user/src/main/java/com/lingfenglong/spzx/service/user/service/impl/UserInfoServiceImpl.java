package com.lingfenglong.spzx.service.user.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingfenglong.spzx.common.exception.CommonGlobalRuntimeException;
import com.lingfenglong.spzx.model.dto.h5.UserLoginDto;
import com.lingfenglong.spzx.model.dto.h5.UserRegisterDto;
import com.lingfenglong.spzx.model.entity.user.UserInfo;
import com.lingfenglong.spzx.model.vo.common.SysUserResultCode;
import com.lingfenglong.spzx.model.vo.h5.UserInfoVo;
import com.lingfenglong.spzx.service.user.mapper.UserInfoMapper;
import com.lingfenglong.spzx.service.user.service.UserInfoService;
import com.lingfenglong.spzx.util.AuthContextUtil;
import com.lingfenglong.spzx.util.constant.RedisConstant;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class UserInfoServiceImpl
        extends ServiceImpl<UserInfoMapper, UserInfo>
        implements UserInfoService {

    private final StringRedisTemplate stringRedisTemplate;
    private final PasswordEncoder passwordEncoder;

    public UserInfoServiceImpl(StringRedisTemplate stringRedisTemplate, PasswordEncoder passwordEncoder) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void register(UserRegisterDto userRegisterDto) {
        String code = stringRedisTemplate
                .opsForValue()
                .get(RedisConstant.KEY_USER_PHONE_CODE + userRegisterDto.getUsername());

        if (!StringUtils.hasText(code) || !code.equals(userRegisterDto.getCode())) {
            throw new RuntimeException("验证码不正确！");
        }

        UserInfo userInfo = UserInfo.builder()
                .username(userRegisterDto.getUsername())
                .password(passwordEncoder.encode(userRegisterDto.getPassword()))
                .nickName(userRegisterDto.getNickName())
                .status(0)
                .build();

        save(userInfo);

        stringRedisTemplate
                .delete(RedisConstant.KEY_USER_PHONE_CODE + userRegisterDto.getUsername());
    }

    @Override
    public String login(UserLoginDto userLoginDto) {
        LambdaQueryWrapper<UserInfo> wrapper = new LambdaQueryWrapper<UserInfo>()
                .eq(UserInfo::getUsername, userLoginDto.getUsername());
        UserInfo userInfo = baseMapper.selectOne(wrapper);

        if (userInfo.getStatus() == 0) {
            throw new CommonGlobalRuntimeException(SysUserResultCode.ACCOUNT_STOP);
        }

        if (!passwordEncoder.matches(userLoginDto.getPassword(), userInfo.getPassword())) {
            throw new CommonGlobalRuntimeException(SysUserResultCode.LOGIN_ERROR);
        }

        String token = UUID.randomUUID().toString();

        stringRedisTemplate.opsForValue()
                .set(RedisConstant.KEY_USER_TOKEN + token, JSON.toJSONString(userInfo), 7, TimeUnit.DAYS);
        return token;
    }

    @Override
    public UserInfoVo getCurrentUserInfo() {
        // HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        // String token = request.getHeader("token");
        //
        // String userInfoToken = stringRedisTemplate.opsForValue()
        //         .get(RedisConstant.KEY_USER_TOKEN + token);
        //
        // if (!StringUtils.hasText(userInfoToken)) {
        //     throw new CommonGlobalRuntimeException(SysUserResultCode.LOGIN_AUTH);
        // }
        //
        // UserInfo userInfo = JSON.parseObject(userInfoToken, UserInfo.class);
        //
        // UserInfoVo userInfoVo = new UserInfoVo();
        // userInfoVo.setNickName(userInfo.getNickName());
        // userInfoVo.setAvatar(userInfo.getAvatar());

        UserInfo userInfo = AuthContextUtil.getUserInfo();
        UserInfoVo userInfoVo = new UserInfoVo();
        BeanUtils.copyProperties(userInfo, userInfoVo);

        return userInfoVo;
    }
}
