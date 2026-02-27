package com.aim.mall.agent.api.dto.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 岗位类型列表查询请求（远程接口）
 *
 * @author Qoder
 * @since 2026/2/26
 */
@Data
public class JobTypePageApiRequest implements Serializable {

    private static final long serialVersionUID = -1L;

    /**
     * 页码，从1开始
     */
    private Integer pageNum = 1;

    /**
     * 每页条数
     */
    private Integer pageSize = 10;

    /**
     * 关键字（名称和描述模糊搜索）
     */
    private String keyword;
}
