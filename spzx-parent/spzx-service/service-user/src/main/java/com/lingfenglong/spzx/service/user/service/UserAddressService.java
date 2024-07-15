package com.lingfenglong.spzx.service.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lingfenglong.spzx.model.entity.user.UserAddress;

import java.util.List;

public interface UserAddressService extends IService<UserAddress> {
    List<UserAddress> findUserAddressList();
}
