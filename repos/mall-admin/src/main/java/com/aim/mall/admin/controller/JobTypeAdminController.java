package com.aim.mall.admin.controller;

import com.aim.mall.admin.domain.dto.JobTypeCreateRequest;
import com.aim.mall.admin.domain.dto.JobTypeListRequest;
import com.aim.mall.admin.domain.dto.JobTypeResponse;
import com.aim.mall.admin.domain.dto.JobTypeStatusRequest;
import com.aim.mall.admin.domain.dto.JobTypeUpdateRequest;
import com.aim.mall.agent.api.dto.request.JobTypeCreateApiRequest;
import com.aim.mall.agent.api.dto.request.JobTypePageApiRequest;
import com.aim.mall.agent.api.dto.request.JobTypeStatusApiRequest;
import com.aim.mall.agent.api.dto.request.JobTypeUpdateApiRequest;
import com.aim.mall.agent.api.dto.response.JobTypeApiResponse;
import com.aim.mall.agent.api.feign.AgentRemoteService;
import com.aim.mall.common.api.CommonResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 岗位类型管理后台控制器
 * <p>
 * 提供管理后台岗位类型管理相关接口
 *
 * @author Qoder
 * @since 2026/2/26
 */
@Tag(name = "岗位类型管理", description = "管理后台岗位类型管理接口")
@RestController
@RequestMapping("/admin/api/v1/job-types")
@RequiredArgsConstructor
public class JobTypeAdminController {

    private final AgentRemoteService agentRemoteService;

    /**
     * 岗位类型列表查询（分页）
     *
     * @param request 查询参数
     * @return 分页结果
     */
    @Operation(summary = "岗位类型列表", description = "分页查询岗位类型列表，支持关键字搜索")
    @GetMapping
    public CommonResult<CommonResult.PageData<JobTypeResponse>> list(JobTypeListRequest request) {
        JobTypePageApiRequest apiRequest = new JobTypePageApiRequest();
        apiRequest.setKeyword(request.getKeyword());
        apiRequest.setPageNum(request.getPageNum());
        apiRequest.setPageSize(request.getPageSize());

        CommonResult<CommonResult.PageData<JobTypeApiResponse>> result = agentRemoteService.pageJobType(apiRequest);

        if (!result.isSuccess() || result.getData() == null) {
            return CommonResult.failed(() -> result);
        }

        List<JobTypeResponse> records = result.getData().getItems().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());

        return CommonResult.pageSuccess(records, result.getData().getTotalCount());
    }

    /**
     * 新增岗位类型
     *
     * @param request 创建请求
     * @return 创建的岗位类型
     */
    @Operation(summary = "新增岗位类型", description = "创建新的岗位类型")
    @PostMapping
    public CommonResult<JobTypeResponse> create(@Valid @RequestBody JobTypeCreateRequest request) {
        JobTypeCreateApiRequest apiRequest = new JobTypeCreateApiRequest();
        BeanUtils.copyProperties(request, apiRequest);

        CommonResult<Long> result = agentRemoteService.createJobType(apiRequest);
        if (!result.isSuccess()) {
            return CommonResult.failed(() -> result);
        }

        // 查询新创建的岗位类型详情
        CommonResult<JobTypeApiResponse> detailResult = agentRemoteService.getJobTypeById(result.getData());
        if (!detailResult.isSuccess() || detailResult.getData() == null) {
            return CommonResult.failed(() -> detailResult);
        }

        return CommonResult.success(convertToResponse(detailResult.getData()));
    }

    /**
     * 编辑岗位类型
     *
     * @param jobTypeId 岗位类型ID
     * @param request   更新请求
     * @return 更新后的岗位类型
     */
    @Operation(summary = "编辑岗位类型", description = "更新岗位类型信息")
    @PutMapping("/{jobTypeId}")
    public CommonResult<JobTypeResponse> update(
            @Parameter(description = "岗位类型ID") @PathVariable Long jobTypeId,
            @Valid @RequestBody JobTypeUpdateRequest request) {
        JobTypeUpdateApiRequest apiRequest = new JobTypeUpdateApiRequest();
        BeanUtils.copyProperties(request, apiRequest);
        apiRequest.setId(jobTypeId);

        CommonResult<Void> result = agentRemoteService.updateJobType(apiRequest);
        if (!result.isSuccess()) {
            return CommonResult.failed(() -> result);
        }

        // 查询更新后的岗位类型详情
        CommonResult<JobTypeApiResponse> detailResult = jobTypeFeignClient.getById(jobTypeId);
        if (!detailResult.isSuccess() || detailResult.getData() == null) {
            return CommonResult.failed(() -> detailResult);
        }

        return CommonResult.success(convertToResponse(detailResult.getData()));
    }

    /**
     * 启用/禁用岗位类型
     *
     * @param jobTypeId 岗位类型ID
     * @param request   状态更新请求
     * @return 成功响应
     */
    @Operation(summary = "更新岗位类型状态", description = "启用或禁用岗位类型")
    @PutMapping("/{jobTypeId}/status")
    public CommonResult<Void> updateStatus(
            @Parameter(description = "岗位类型ID") @PathVariable Long jobTypeId,
            @Valid @RequestBody JobTypeStatusRequest request) {
        JobTypeStatusApiRequest apiRequest = new JobTypeStatusApiRequest();
        apiRequest.setId(jobTypeId);
        apiRequest.setStatus(request.getStatus());

        return agentRemoteService.updateStatus(apiRequest);
    }

    /**
     * 删除岗位类型
     *
     * @param jobTypeId 岗位类型ID
     * @return 成功响应
     */
    @Operation(summary = "删除岗位类型", description = "删除岗位类型（仅当无员工绑定时可删除）")
    @DeleteMapping("/{jobTypeId}")
    public CommonResult<Void> delete(
            @Parameter(description = "岗位类型ID") @PathVariable Long jobTypeId) {
        return agentRemoteService.deleteJobType(jobTypeId);
    }

    /**
     * 转换为响应对象
     *
     * @param apiResponse API响应对象
     * @return 响应对象
     */
    private JobTypeResponse convertToResponse(JobTypeApiResponse apiResponse) {
        JobTypeResponse response = new JobTypeResponse();
        BeanUtils.copyProperties(apiResponse, response);
        return response;
    }
}
