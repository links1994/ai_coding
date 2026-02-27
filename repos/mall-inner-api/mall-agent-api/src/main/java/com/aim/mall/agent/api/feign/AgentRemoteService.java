package com.aim.mall.agent.api.feign;

import com.aim.mall.agent.api.dto.request.JobTypeCreateApiRequest;
import com.aim.mall.agent.api.dto.request.JobTypeListApiRequest;
import com.aim.mall.agent.api.dto.request.JobTypeStatusApiRequest;
import com.aim.mall.agent.api.dto.request.JobTypeUpdateApiRequest;
import com.aim.mall.agent.api.dto.response.JobTypeApiResponse;
import com.aim.mall.common.api.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 智能员工模块远程服务接口
 * <p>
 * 注意：RemoteService直接对接mall-agent的InnerController，
 * 数据转换在mall-agent的Service层完成，本层只做转发。
 *
 * @author Qoder
 * @since 2026/2/26
 */
@FeignClient(name = "mall-agent", path = "/inner/api/v1/job-types")
public interface AgentRemoteService {

    /**
     * 分页查询岗位类型列表
     *
     * @param request 查询参数
     * @return 分页结果
     */
    @PostMapping("/list")
    CommonResult<CommonResult.PageData<JobTypeApiResponse>> pageJobType(
            @RequestBody JobTypeListApiRequest request);

    /**
     * 根据ID查询岗位类型详情
     *
     * @param jobTypeId 岗位ID
     * @return 岗位类型详情
     */
    @GetMapping("/detail")
    CommonResult<JobTypeApiResponse> getJobTypeById(@RequestParam("jobTypeId") Long jobTypeId);

    /**
     * 创建岗位类型
     *
     * @param request 创建请求
     * @return 新记录ID
     */
    @PostMapping("/create")
    CommonResult<Long> createJobType(@RequestBody JobTypeCreateApiRequest request);

    /**
     * 更新岗位类型
     *
     * @param request 更新请求（包含ID）
     * @return 成功响应
     */
    @PostMapping("/update")
    CommonResult<Void> updateJobType(@RequestBody JobTypeUpdateApiRequest request);

    /**
     * 更新岗位类型状态
     *
     * @param request 状态更新请求
     * @return 成功响应
     */
    @PostMapping("/status")
    CommonResult<Void> updateStatus(@RequestBody JobTypeStatusApiRequest request);

    /**
     * 删除岗位类型
     *
     * @param jobTypeId 岗位ID
     * @return 成功响应
     */
    @PostMapping("/delete")
    CommonResult<Void> deleteJobType(@RequestParam("jobTypeId") Long jobTypeId);
}
