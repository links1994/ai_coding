package com.aim.mall.agent.service;

import com.aim.mall.agent.api.dto.request.JobTypeCreateApiRequest;
import com.aim.mall.agent.api.dto.request.JobTypePageApiRequest;
import com.aim.mall.agent.api.dto.request.JobTypeStatusApiRequest;
import com.aim.mall.agent.api.dto.request.JobTypeUpdateApiRequest;
import com.aim.mall.agent.api.dto.response.JobTypeApiResponse;

import java.util.List;

/**
 * 岗位类型服务接口
 *
 * @author Qoder
 * @since 2026/2/26
 */
public interface JobTypeService {

    /**
     * 分页查询岗位类型列表
     *
     * @param request 查询参数
     * @return 岗位类型列表
     */
    List<JobTypeApiResponse> pageList(JobTypePageApiRequest request);

    /**
     * 根据关键字查询总数
     *
     * @param keyword 关键字
     * @return 总数
     */
    Long countByKeyword(String keyword);

    /**
     * 根据ID查询岗位类型详情
     *
     * @param jobTypeId 岗位ID
     * @return 岗位类型详情
     */
    JobTypeApiResponse getById(Long jobTypeId);

    /**
     * 创建岗位类型
     *
     * @param request 创建请求
     * @return 新记录ID
     */
    Long create(JobTypeCreateApiRequest request);

    /**
     * 更新岗位类型
     *
     * @param request 更新请求
     */
    void update(JobTypeUpdateApiRequest request);

    /**
     * 更新岗位类型状态
     *
     * @param request 状态更新请求
     */
    void updateStatus(JobTypeStatusApiRequest request);

    /**
     * 删除岗位类型
     *
     * @param jobTypeId 岗位ID
     */
    void delete(Long jobTypeId);
}
