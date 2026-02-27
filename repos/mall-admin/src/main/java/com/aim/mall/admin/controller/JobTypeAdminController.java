package com.aim.mall.admin.controller;

import com.aim.mall.admin.domain.dto.request.JobTypeCreateRequest;
import com.aim.mall.admin.domain.dto.request.JobTypeStatusRequest;
import com.aim.mall.admin.domain.dto.request.JobTypeUpdateRequest;
import com.aim.mall.agent.api.dto.request.JobTypeListApiRequest;
import com.aim.mall.agent.api.dto.response.JobTypeApiResponse;
import com.aim.mall.agent.api.feign.AgentRemoteService;
import com.mall.common.api.CommonResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 岗位类型管理接口（管理后台）
 *
 * @author Qoder
 * @since 2026/2/26
 */
@Slf4j
@RestController
@RequestMapping("/admin/api/v1/job-types")
@Tag(name = "岗位类型管理", description = "智能员工岗位类型管理接口")
@RequiredArgsConstructor
public class JobTypeAdminController {

    private final AgentRemoteService agentRemoteService;

    /**
     * 分页查询岗位类型列表
     * <p>
     * 注意：Controller层只做参数接收和转发，不做任何业务逻辑处理。
     * 数据转换在mall-agent的Service层完成。
     *
     * @param keyword 关键字（名称和描述模糊搜索）
     * @param pageNum 页码
     * @param pageSize 每页条数
     * @return 分页结果
     */
    @GetMapping
    @Operation(summary = "岗位类型列表", description = "分页查询岗位类型列表，支持关键字搜索")
    public CommonResult<CommonResult.PageData<JobTypeApiResponse>> pageJobType(
            @Parameter(description = "关键字（名称和描述模糊搜索）")
            @RequestParam(name = "keyword", required = false) String keyword,
            @Parameter(description = "页码，默认1")
            @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
            @Parameter(description = "每页条数，默认10，最大100")
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {

        log.debug("查询岗位类型列表开始, keyword={}, pageNum={}, pageSize={}", keyword, pageNum, pageSize);

        // 构建查询参数
        JobTypeListApiRequest apiRequest = new JobTypeListApiRequest();
        apiRequest.setKeyword(keyword);
        apiRequest.setPageNum(pageNum);
        apiRequest.setPageSize(pageSize);

        // Controller层直接转发RemoteService调用结果，不做业务逻辑处理
        return agentRemoteService.pageJobType(apiRequest);
    }

    /**
     * 根据ID查询岗位类型详情
     *
     * @param jobTypeId 岗位ID
     * @return 岗位类型详情
     */
    @GetMapping("/{jobTypeId}")
    @Operation(summary = "岗位类型详情", description = "根据ID查询岗位类型详情")
    public CommonResult<JobTypeApiResponse> getJobTypeById(
            @Parameter(description = "岗位类型ID", required = true)
            @PathVariable("jobTypeId") Long jobTypeId) {

        log.debug("查询岗位类型详情开始, jobTypeId={}", jobTypeId);
        return agentRemoteService.getJobTypeById(jobTypeId);
    }

    /**
     * 创建岗位类型
     *
     * @param request 创建请求
     * @return 新记录ID
     */
    @PostMapping
    @Operation(summary = "新增岗位类型", description = "创建新的岗位类型")
    public CommonResult<Long> createJobType(
            @Parameter(description = "创建请求", required = true)
            @RequestBody @Valid JobTypeCreateRequest request) {

        log.debug("创建岗位类型开始, code={}, name={}", request.getCode(), request.getName());
        return agentRemoteService.createJobType(request);
    }

    /**
     * 更新岗位类型
     *
     * @param jobTypeId 岗位ID
     * @param request 更新请求
     * @return 成功响应
     */
    @PutMapping("/{jobTypeId}")
    @Operation(summary = "编辑岗位类型", description = "更新岗位类型信息（编码不可修改）")
    public CommonResult<Void> updateJobType(
            @Parameter(description = "岗位类型ID", required = true)
            @PathVariable("jobTypeId") Long jobTypeId,
            @Parameter(description = "更新请求", required = true)
            @RequestBody @Valid JobTypeUpdateRequest request) {

        log.debug("更新岗位类型开始, jobTypeId={}, name={}", jobTypeId, request.getName());

        // 构造带ID的更新请求
        JobTypeUpdateRequest updateRequest = new JobTypeUpdateRequest();
        updateRequest.setId(jobTypeId);
        updateRequest.setName(request.getName());
        updateRequest.setSortOrder(request.getSortOrder());
        updateRequest.setDescription(request.getDescription());
        updateRequest.setIsDefault(request.getIsDefault());

        return agentRemoteService.updateJobType(updateRequest);
    }

    /**
     * 更新岗位类型状态
     *
     * @param jobTypeId 岗位ID
     * @param request 状态更新请求
     * @return 成功响应
     */
    @PutMapping("/{jobTypeId}/status")
    @Operation(summary = "启用/禁用岗位类型", description = "更新岗位类型状态")
    public CommonResult<Void> updateStatus(
            @Parameter(description = "岗位类型ID", required = true)
            @PathVariable("jobTypeId") Long jobTypeId,
            @Parameter(description = "状态请求", required = true)
            @RequestBody @Valid JobTypeStatusRequest request) {

        log.debug("更新岗位类型状态开始, jobTypeId={}, status={}", jobTypeId, request.getStatus());

        // 设置ID到请求对象
        request.setJobTypeId(jobTypeId);

        return agentRemoteService.updateStatus(request);
    }

    /**
     * 删除岗位类型
     *
     * @param jobTypeId 岗位ID
     * @return 成功响应
     */
    @DeleteMapping("/{jobTypeId}")
    @Operation(summary = "删除岗位类型", description = "删除岗位类型（仅当无员工绑定时可删除）")
    public CommonResult<Void> deleteJobType(
            @Parameter(description = "岗位类型ID", required = true)
            @PathVariable("jobTypeId") Long jobTypeId) {

        log.debug("删除岗位类型开始, jobTypeId={}", jobTypeId);
        return agentRemoteService.deleteJobType(jobTypeId);
    }
}
