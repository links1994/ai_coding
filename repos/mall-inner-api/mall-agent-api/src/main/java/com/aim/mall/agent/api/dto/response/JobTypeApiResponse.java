package com.aim.mall.agent.api.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 岗位类型响应（远程接口返回）
 *
 * @author Qoder
 * @since 2026/2/26
 */
@Data
public class JobTypeApiResponse implements Serializable {

    private static final long serialVersionUID = -1L;

    /**
     * 岗位ID
     */
    private Long id;

    /**
     * 岗位编码
     */
    private String code;

    /**
     * 显示名称
     */
    private String name;

    /**
     * 状态：0-禁用，1-启用
     */
    private Integer status;

    /**
     * 排序
     */
    private Integer sortOrder;

    /**
     * 描述
     */
    private String description;

    /**
     * 默认标记：0-否，1-是
     */
    private Integer isDefault;

    /**
     * 关联员工数量
     */
    private Integer employeeCount;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;
}
