package com.lingfenglong.spzx.model.dto.system;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "请求参数实体类")
public class SysMenuDto {
    private Long menuId;
}
