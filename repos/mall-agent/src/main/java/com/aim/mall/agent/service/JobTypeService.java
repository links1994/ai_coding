package com.aim.mall.agent.service;

import com.aim.mall.agent.domain.dto.JobTypeCreateDTO;
import com.aim.mall.agent.domain.dto.JobTypeListQuery;
import com.aim.mall.agent.domain.dto.JobTypeUpdateDTO;
import com.aim.mall.agent.domain.entity.JobTypeDO;
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
     * 分页查询岗位类型列表（单表查询，返回DO）
     *
     * @param query 查询参数
     * @return 分页结果
     */
    CommonResult<CommonResult.PageData<JobTypeDO>> list(JobTypeListQuery query);

    /**
     * 根据ID查询岗位类型详情（单表查询，返回DO）
     *
     * @param id 岗位ID
     * @return 岗位类型详情
     */
    JobTypeDO getById(Long id);

    /**
     * 创建岗位类型（单表操作，返回DO）
     *
     * @param dto 创建DTO
     * @return 创建的岗位类型
     */
    JobTypeDO create(JobTypeCreateDTO dto);

    /**
     * 更新岗位类型（单表操作，返回DO）
     *
     * @param dto 更新DTO
     * @return 更新后的岗位类型
     */
    JobTypeDO update(JobTypeUpdateDTO dto);

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
    void delete(Long id);

    /**
     * 查询岗位类型关联的员工数量
     *
     * @param jobTypeId 岗位类型ID
     * @return 员工数量
     */
    Integer countEmployeesByJobTypeId(Long jobTypeId);
}
