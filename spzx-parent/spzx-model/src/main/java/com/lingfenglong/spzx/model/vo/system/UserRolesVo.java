package com.lingfenglong.spzx.model.vo.system;

import com.lingfenglong.spzx.model.entity.system.SysRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "用户所具有角色的响应结果实体类")
public class UserRolesVo {

    @Schema(description = "所有系统角色")
    private List<SysRole> all;

    @Schema(description = "已分配的系统角色")
    private List<Long> assigned;
}
