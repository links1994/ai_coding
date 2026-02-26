package com.aim.mall.agent.domain.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 岗位类型列表查询参数
 *
 * @author Qoder
 * @since 2026/2/26
 */
@Data
public class JobTypeListQuery implements Serializable {

    private static final long serialVersionUID = -1L;

    /**
     * 关键字（名称和描述模糊搜索）
     */
    private String keyword;

    /**
     * 页码（默认1）
     */
    private Integer pageNum = 1;

    /**
     * 每页条数（默认10，最大100）
     */
    private Integer pageSize = 10;
}
