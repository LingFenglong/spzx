package com.lingfenglong.spzx.model.dto.h5;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "用户登录请求参数")
public class UserLoginDto {

    @NotBlank
    @Schema(description = "用户名")
    private String username ;

    @NotBlank
    @Schema(description = "密码")
    private String password ;
}