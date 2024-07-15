package com.lingfenglong.spzx.service.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingfenglong.spzx.model.entity.user.UserAddress;
import com.lingfenglong.spzx.service.user.mapper.UserAddressMapper;
import com.lingfenglong.spzx.service.user.service.UserAddressService;
import com.lingfenglong.spzx.util.AuthContextUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAddressServiceImpl
        extends ServiceImpl<UserAddressMapper, UserAddress>
        implements UserAddressService {

    @Override
    public List<UserAddress> findUserAddressList() {
        LambdaQueryWrapper<UserAddress> wrapper = new LambdaQueryWrapper<UserAddress>()
                .eq(UserAddress::getUserId, AuthContextUtil.getUserInfo().getId());
        return baseMapper.selectList(wrapper);
    }
}
