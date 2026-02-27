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
public class JobTypeListApiRequest implements Serializable {

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
     * 岗位编码（模糊查询）
     */
    private String code;

    /**
     * 岗位名称（模糊查询）
     */
    private String name;

    /**
     * 状态：0-禁用，1-启用
     */
    private Integer status;
}
