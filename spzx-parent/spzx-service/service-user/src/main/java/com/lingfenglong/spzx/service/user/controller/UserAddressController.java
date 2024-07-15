package com.lingfenglong.spzx.service.user.controller;

import com.lingfenglong.spzx.model.entity.user.UserAddress;
import com.lingfenglong.spzx.model.vo.common.CommonResultCode;
import com.lingfenglong.spzx.model.vo.common.Result;
import com.lingfenglong.spzx.service.user.service.impl.UserAddressServiceImpl;
import com.lingfenglong.spzx.service.user.service.impl.UserInfoServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "用户地址接口")
@RestController
@RequestMapping("/api/user/userAddress")
public class UserAddressController {
    private final UserAddressServiceImpl userAddressServiceImpl;

    public UserAddressController(UserAddressServiceImpl userAddressServiceImpl) {
        this.userAddressServiceImpl = userAddressServiceImpl;
    }

    @GetMapping("/auth/findUserAddressList")
    public Result<List<UserAddress>> findUserAddressList() {
        List<UserAddress> userAddressList = userAddressServiceImpl.findUserAddressList();
        return Result.build(userAddressList, CommonResultCode.SUCCESS);
    }
    
    @GetMapping("/getUserAddress/{id}")
    public Result<UserAddress> getUserAddress(@PathVariable("id") Long id) {
        UserAddress userAddress = userAddressServiceImpl.getUserAddress(id);
        return Result.build(userAddress, CommonResultCode.SUCCESS);
    }
}
