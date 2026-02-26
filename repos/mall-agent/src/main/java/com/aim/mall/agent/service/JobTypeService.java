package com.aim.mall.agent.service;

import com.aim.mall.agent.api.dto.request.JobTypeCreateApiRequest;
import com.aim.mall.agent.api.dto.request.JobTypeListApiRequest;
import com.aim.mall.agent.api.dto.request.JobTypeUpdateApiRequest;
import com.aim.mall.agent.api.dto.response.JobTypeApiResponse;
import com.aim.mall.agent.domain.entity.AimJobTypeDO;
import com.mall.common.api.CommonResult;

import java.util.List;

/**
 * 岗位类型服务接口
 *
 * @author Qoder
 * @since 2026/2/26
 */
public interface JobTypeService {

    /**
     * 分页查询岗位类型列表（Service层完成DO到Response的转换）
     *
     * @param query 查询参数
     * @return 分页结果（返回Response）
     */
    CommonResult<CommonResult.PageData<JobTypeApiResponse>> pageJobType(JobTypeListApiRequest request);

    /**
     * 根据ID查询岗位类型详情（单表查询，返回DO）
     *
     * @param id 岗位ID
     * @return 岗位类型详情
     */
    AimJobTypeDO getJobTypeById(Long id);

    /**
     * 创建岗位类型（单表操作）
     *
     * @param request 创建请求
     * @return 新记录ID
     */
    Long createJobType(JobTypeCreateApiRequest request);

    /**
     * 更新岗位类型（单表操作）
     *
     * @param request 更新请求
     * @return 是否成功
     */
    boolean updateJobType(JobTypeUpdateApiRequest request);

    /**
     * 更新岗位类型状态
     *
     * @param id 岗位ID
     * @param status 状态（0-禁用，1-启用）
     */
    void updateStatus(Long id, Integer status);

    /**
     * 删除岗位类型
     *
     * @param id 岗位ID
     */
    void deleteJobType(Long id);

    /**
     * 查询岗位类型关联的员工数量
     *
     * @param jobTypeId 岗位类型ID
     * @return 员工数量
     */
    Integer countEmployeesByJobTypeId(Long jobTypeId);
}
