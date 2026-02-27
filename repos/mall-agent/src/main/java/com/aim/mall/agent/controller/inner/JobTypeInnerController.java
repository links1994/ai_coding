package com.aim.mall.agent.controller.inner;

import com.aim.mall.agent.api.dto.request.JobTypeCreateApiRequest;
import com.aim.mall.agent.api.dto.request.JobTypePageApiRequest;
import com.aim.mall.agent.api.dto.request.JobTypeStatusApiRequest;
import com.aim.mall.agent.api.dto.request.JobTypeUpdateApiRequest;
import com.aim.mall.agent.api.dto.response.JobTypeApiResponse;
import com.aim.mall.agent.domain.entity.AimJobTypeDO;
import com.aim.mall.agent.service.JobTypeService;
import com.aim.mall.common.api.CommonResult;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 岗位类型内部接口（供Feign调用）
 *
 * @author Qoder
 * @since 2026/2/26
 */
@Slf4j
@RestController
@RequestMapping("/inner/api/v1/job-types")
@RequiredArgsConstructor
public class JobTypeInnerController {

    private final JobTypeService jobTypeService;

    /**
     * 分页查询岗位类型列表
     * <p>
     * 规范：Controller 层只做参数转换和调用 Service，不做业务逻辑。
     * 数据转换（DO → Response）在 Service 层完成。
     *
     * @param request 查询参数
     * @return 分页结果
     */
    @PostMapping("/list")
    public CommonResult<CommonResult.PageData<JobTypeApiResponse>> pageJobType(
            @RequestBody @Valid JobTypePageApiRequest request) {

        // 限制每页最大条数
        request.setPageSize(Math.min(request.getPageSize(), 100));

        // 直接返回 Service 结果（转换已在 Service 层完成）
        return jobTypeService.pageJobType(request);
    }

    /**
     * 根据ID查询岗位类型详情（单表查询，返回DO）
     *
     * @param jobTypeId 岗位ID
     * @return 岗位类型详情
     */
    @GetMapping("/detail")
    public CommonResult<JobTypeApiResponse> getJobTypeById(@RequestParam("jobTypeId") Long jobTypeId) {
        AimJobTypeDO entity = jobTypeService.getJobTypeById(jobTypeId);
        return CommonResult.success(convertToResponse(entity));
    }

    /**
     * 创建岗位类型
     *
     * @param dto 创建DTO
     * @return 新记录ID
     */
    @PostMapping("/create")
    public CommonResult<Long> createJobType(@RequestBody @Valid JobTypeCreateApiRequest request) {
        Long id = jobTypeService.createJobType(request);
        return CommonResult.success(id);
    }

    /**
     * 更新岗位类型
     *
     * @param dto 更新DTO
     * @return 成功响应
     */
    @PostMapping("/update")
    public CommonResult<Void> updateJobType(@RequestBody @Valid JobTypeUpdateApiRequest request) {
        jobTypeService.updateJobType(request);
        return CommonResult.success();
    }

    /**
     * 更新岗位类型状态
     *
     * @param dto 状态更新DTO
     * @return 成功响应
     */
    @PostMapping("/status")
    public CommonResult<Void> updateStatus(@RequestBody @Valid JobTypeStatusApiRequest request) {
        jobTypeService.updateStatus(request.getId(), request.getStatus());
        return CommonResult.success();
    }

    /**
     * 删除岗位类型
     *
     * @param jobTypeId 岗位ID
     * @return 成功响应
     */
    @PostMapping("/delete")
    public CommonResult<Void> deleteJobType(@RequestParam("jobTypeId") Long jobTypeId) {
        jobTypeService.deleteJobType(jobTypeId);
        return CommonResult.success();
    }

    /**
     * 将 DO 转换为 Response
     */
    private JobTypeApiResponse convertToResponse(AimJobTypeDO entity) {
        if (entity == null) {
            return null;
        }
        JobTypeApiResponse response = new JobTypeApiResponse();
        response.setId(entity.getId());
        response.setCode(entity.getCode());
        response.setName(entity.getName());
        response.setStatus(entity.getStatus());
        response.setSortOrder(entity.getSortOrder());
        response.setDescription(entity.getDescription());
        response.setIsDefault(entity.getIsDefault());
        response.setCreateTime(entity.getCreateTime());
        response.setUpdateTime(entity.getUpdateTime());
        response.setEmployeeCount(0);
        return response;
    }
}
