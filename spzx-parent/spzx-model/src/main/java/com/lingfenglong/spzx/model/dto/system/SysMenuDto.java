package com.lingfenglong.spzx.model.dto.system;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "请求参数实体类")
public class SysMenuDto {
    @Schema(description = "节点id")
    private Long menuId;

    @Schema(description = "父节点id")
    private Long parentId;

    @Schema(description = "菜单标题")
    private String title;

    @Schema(description = "路由名称")
    private String component;

    @Schema(description = "排序")
    private Integer sortValue;

    @Schema(description = "状态(0:禁止,1:正常)")
    private Integer status;
}
