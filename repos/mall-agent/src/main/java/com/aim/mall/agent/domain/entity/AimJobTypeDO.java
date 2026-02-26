package com.aim.mall.agent.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 岗位类型实体类
 *
 * @author Qoder
 * @since 2026/2/26
 */
@Data
@TableName("aim_job_type")
public class AimJobTypeDO implements Serializable {

    private static final long serialVersionUID = -1L;

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 岗位编码（全局唯一）
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
     * 显示排序
     */
    private Integer sortOrder;

    /**
     * 描述文本
     */
    private String description;

    /**
     * 默认标记：0-否，1-是
     */
    private Integer isDefault;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 逻辑删除标记：0-未删除，1-已删除
     */
    private Integer isDeleted;
}
