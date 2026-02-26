package com.aim.mall.admin.feign;

import com.aim.mall.admin.api.dto.request.JobTypeCreateRequest;
import com.aim.mall.admin.api.dto.request.JobTypeUpdateRequest;
import com.aim.mall.admin.api.dto.response.JobTypeResponse;
import com.mall.common.api.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 岗位类型Feign客户端
 *
 * @author Qoder
 * @since 2026/2/26
 */
@FeignClient(name = "mall-agent", path = "/inner/api/v1/job-types")
public interface JobTypeFeignClient {

    /**
     * 分页查询岗位类型列表
     *
     * @param keyword 关键字
     * @param pageNum 页码
     * @param pageSize 每页条数
     * @return 分页结果
     */
    @GetMapping("/list")
    CommonResult<CommonResult.PageData<JobTypeResponse>> list(
            @RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize);

    /**
     * 根据ID查询岗位类型详情
     *
     * @param jobTypeId 岗位ID
     * @return 岗位类型详情
     */
    @GetMapping("/detail")
    CommonResult<JobTypeResponse> getById(@RequestParam("jobTypeId") Long jobTypeId);

    /**
     * 创建岗位类型
     *
     * @param request 创建请求
     * @return 创建的岗位类型
     */
    @PostMapping("/create")
    CommonResult<JobTypeResponse> create(@RequestBody JobTypeCreateRequest request);

    /**
     * 更新岗位类型
     *
     * @param request 更新请求（包含ID）
     * @return 更新后的岗位类型
     */
    @PostMapping("/update")
    CommonResult<JobTypeResponse> update(@RequestBody JobTypeUpdateRequest request);

    /**
     * 更新岗位类型状态
     *
     * @param jobTypeId 岗位ID
     * @param status 状态
     * @return 成功响应
     */
    @PostMapping("/status")
    CommonResult<Void> updateStatus(@RequestParam("jobTypeId") Long jobTypeId, 
                                     @RequestParam("status") Integer status);

    /**
     * 删除岗位类型
     *
     * @param jobTypeId 岗位ID
     * @return 成功响应
     */
    @PostMapping("/delete")
    CommonResult<Void> delete(@RequestParam("jobTypeId") Long jobTypeId);
}
