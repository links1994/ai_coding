package com.aim.mall.agent.controller.inner;

import com.aim.mall.agent.domain.dto.JobTypeCreateDTO;
import com.aim.mall.agent.domain.dto.JobTypeListQuery;
import com.aim.mall.agent.domain.dto.JobTypeStatusDTO;
import com.aim.mall.agent.domain.dto.JobTypeUpdateDTO;
import com.aim.mall.agent.domain.entity.JobTypeDO;
import com.aim.mall.agent.service.JobTypeService;
import com.mall.common.api.CommonResult;
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
     * 分页查询岗位类型列表（单表查询，返回DO）
     *
     * @param keyword 关键字
     * @param pageNum 页码
     * @param pageSize 每页条数
     * @return 分页结果
     */
    @GetMapping("/list")
    public CommonResult<CommonResult.PageData<JobTypeDO>> list(
            @RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        
        JobTypeListQuery query = new JobTypeListQuery();
        query.setKeyword(keyword);
        query.setPageNum(pageNum);
        query.setPageSize(Math.min(pageSize, 100));
        
        return jobTypeService.list(query);
    }

    /**
     * 根据ID查询岗位类型详情（单表查询，返回DO）
     *
     * @param jobTypeId 岗位ID
     * @return 岗位类型详情
     */
    @GetMapping("/detail")
    public CommonResult<JobTypeDO> getById(@RequestParam("jobTypeId") Long jobTypeId) {
        JobTypeDO entity = jobTypeService.getById(jobTypeId);
        return CommonResult.success(entity);
    }

    /**
     * 创建岗位类型（单表操作，返回DO）
     *
     * @param dto 创建DTO
     * @return 创建的岗位类型
     */
    @PostMapping("/create")
    public CommonResult<JobTypeDO> create(@RequestBody @Valid JobTypeCreateDTO dto) {
        JobTypeDO entity = jobTypeService.create(dto);
        return CommonResult.success(entity);
    }

    /**
     * 更新岗位类型（单表操作，返回DO）
     *
     * @param dto 更新DTO
     * @return 更新后的岗位类型
     */
    @PostMapping("/update")
    public CommonResult<JobTypeDO> update(@RequestBody @Valid JobTypeUpdateDTO dto) {
        JobTypeDO entity = jobTypeService.update(dto);
        return CommonResult.success(entity);
    }

    /**
     * 更新岗位类型状态
     *
     * @param dto 状态更新DTO
     * @return 成功响应
     */
    @PostMapping("/status")
    public CommonResult<Void> updateStatus(@RequestBody @Valid JobTypeStatusDTO dto) {
        jobTypeService.updateStatus(dto.getId(), dto.getStatus());
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
