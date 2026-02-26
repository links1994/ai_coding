package com.aim.mall.admin.domain.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

/**
 * 岗位类型状态更新请求（管理端）
 *
 * @author Qoder
 * @since 2026/2/26
 */
@Data
public class JobTypeStatusRequest implements Serializable {

    private static final long serialVersionUID = -1L;

    /**
     * 岗位类型ID
     */
    @NotNull(message = "岗位类型ID不能为空")
    private Long jobTypeId;

    /**
     * 状态：0-禁用，1-启用
     */
    @NotNull(message = "状态不能为空")
    private Integer status;
}
