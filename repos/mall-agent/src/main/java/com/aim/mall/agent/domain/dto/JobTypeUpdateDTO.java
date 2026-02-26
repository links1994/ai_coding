package com.aim.mall.agent.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

/**
 * 岗位类型更新DTO
 *
 * @author Qoder
 * @since 2026/2/26
 */
@Data
public class JobTypeUpdateDTO implements Serializable {

    private static final long serialVersionUID = -1L;

    /**
     * 岗位ID
     */
    @NotNull(message = "岗位ID不能为空")
    private Long id;

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
