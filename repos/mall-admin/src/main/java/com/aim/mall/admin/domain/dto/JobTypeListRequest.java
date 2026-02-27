package com.aim.mall.admin.domain.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

import java.io.Serializable;

/**
 * 岗位类型列表查询请求
 *
 * @author Qoder
 * @since 2026/2/26
 */
@Data
public class JobTypeListRequest implements Serializable {

    private static final long serialVersionUID = -1L;

    /**
     * 关键字（名称和描述模糊搜索）
     */
    private String keyword;

    /**
     * 页码，从1开始
     */
    @Min(value = 1, message = "页码最小为1")
    private Integer pageNum = 1;

    /**
     * 每页条数
     */
    @Min(value = 1, message = "每页条数最小为1")
    @Max(value = 100, message = "每页条数最大为100")
    private Integer pageSize = 10;
}
