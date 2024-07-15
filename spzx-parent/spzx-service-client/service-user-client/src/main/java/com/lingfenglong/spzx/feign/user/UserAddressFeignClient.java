package com.lingfenglong.spzx.feign.user;

import com.lingfenglong.spzx.model.entity.user.UserAddress;
import com.lingfenglong.spzx.model.vo.common.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(path = "/api/user/userAddress", name = "user-service")
public interface UserAddressFeignClient {

    @GetMapping("/getUserAddress/{id}")
    Result<UserAddress> getUserAddress(@PathVariable("id") Long id);
}
