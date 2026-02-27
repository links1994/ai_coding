package com.aim.mall.agent.controller.inner;

import com.aim.mall.agent.api.dto.request.JobTypeCreateApiRequest;
import com.aim.mall.agent.api.dto.request.JobTypePageApiRequest;
import com.aim.mall.agent.api.dto.request.JobTypeStatusApiRequest;
import com.aim.mall.agent.api.dto.request.JobTypeUpdateApiRequest;
import com.aim.mall.agent.api.dto.response.JobTypeApiResponse;
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

import java.util.List;

/**
 * 岗位类型内部接口控制器
 * <p>
 * 供其他服务通过Feign调用
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
     *
     * @param request 查询参数
     * @return 分页结果
     */
    @PostMapping("/list")
    public CommonResult<CommonResult.PageData<JobTypeApiResponse>> pageList(
            @Valid @RequestBody JobTypePageApiRequest request) {
        List<JobTypeApiResponse> list = jobTypeService.pageList(request);
        Long totalCount = jobTypeService.countByKeyword(request.getKeyword());
        return CommonResult.pageSuccess(list, totalCount);
    }

    /**
     * 根据ID查询岗位类型详情
     *
     * @param jobTypeId 岗位ID
     * @return 岗位类型详情
     */
    @GetMapping("/detail")
    public CommonResult<JobTypeApiResponse> getById(@RequestParam("jobTypeId") Long jobTypeId) {
        JobTypeApiResponse response = jobTypeService.getById(jobTypeId);
        return CommonResult.success(response);
    }

    /**
     * 创建岗位类型
     *
     * @param request 创建请求
     * @return 新记录ID
     */
    @PostMapping("/create")
    public CommonResult<Long> create(@Valid @RequestBody JobTypeCreateApiRequest request) {
        Long id = jobTypeService.create(request);
        return CommonResult.success(id);
    }

    /**
     * 更新岗位类型
     *
     * @param request 更新请求
     * @return 成功响应
     */
    @PostMapping("/update")
    public CommonResult<Void> update(@Valid @RequestBody JobTypeUpdateApiRequest request) {
        jobTypeService.update(request);
        return CommonResult.success();
    }

    /**
     * 更新岗位类型状态
     *
     * @param request 状态更新请求
     * @return 成功响应
     */
    @PostMapping("/status")
    public CommonResult<Void> updateStatus(@Valid @RequestBody JobTypeStatusApiRequest request) {
        jobTypeService.updateStatus(request);
        return CommonResult.success();
    }

    /**
     * 删除岗位类型
     *
     * @param jobTypeId 岗位ID
     * @return 成功响应
     */
    @PostMapping("/delete")
    public CommonResult<Void> delete(@RequestParam("jobTypeId") Long jobTypeId) {
        jobTypeService.delete(jobTypeId);
        return CommonResult.success();
    }
}
