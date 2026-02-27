package com.aim.mall.admin.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

/**
 * 岗位类型更新请求
 *
 * @author Qoder
 * @since 2026/2/26
 */
@Data
public class JobTypeUpdateRequest implements Serializable {

    private static final long serialVersionUID = -1L;

    /**
     * 显示名称
     */
    @NotBlank(message = "岗位名称不能为空")
    @Size(min = 1, max = 64, message = "岗位名称长度必须在1-64之间")
    private String name;

    /**
     * 排序
     */
    private Integer sortOrder = 0;

    /**
     * 描述
     */
    @Size(max = 255, message = "描述长度不能超过255")
    private String description;

    /**
     * 默认标记：0-否，1-是
     */
    private Integer isDefault = 0;
}
