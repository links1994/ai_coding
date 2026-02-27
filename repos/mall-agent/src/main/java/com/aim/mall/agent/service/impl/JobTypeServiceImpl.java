package com.aim.mall.agent.service.impl;

import com.aim.mall.agent.api.dto.request.JobTypeCreateApiRequest;
import com.aim.mall.agent.api.dto.request.JobTypeListApiRequest;
import com.aim.mall.agent.api.dto.request.JobTypeUpdateApiRequest;
import com.aim.mall.agent.api.dto.response.JobTypeApiResponse;
import com.aim.mall.agent.domain.entity.AimJobTypeDO;
import com.aim.mall.agent.domain.enums.ErrorCodeEnum;
import com.aim.mall.agent.domain.exception.BusinessException;
import com.aim.mall.agent.mapper.JobTypeMapper;
import com.aim.mall.agent.service.JobTypeService;
import com.aim.mall.common.api.CommonResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 岗位类型服务实现类
 *
 * @author Qoder
 * @since 2026/2/26
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class JobTypeServiceImpl implements JobTypeService {

    private final JobTypeMapper jobTypeMapper;

    @Override
    public CommonResult<CommonResult.PageData<JobTypeApiResponse>> pageJobType(JobTypeListApiRequest request) {
        log.debug("查询岗位类型列表开始, keyword={}, pageNum={}, pageSize={}",
                request.getKeyword(), request.getPageNum(), request.getPageSize());

        // 计算分页参数
        int offset = (request.getPageNum() - 1) * request.getPageSize();
        int limit = request.getPageSize();

        // 查询数据（单表查询）
        List<AimJobTypeDO> records = jobTypeMapper.selectPageByKeyword(request.getKeyword(), offset, limit);
        Long total = jobTypeMapper.countByKeyword(request.getKeyword());

        // Service层完成DO到Response的转换（Controller层不做业务逻辑）
        List<JobTypeApiResponse> responses = records.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());

        return CommonResult.pageSuccess(responses, total);
    }

    /**
     * 将DO转换为Response（Service层内部转换）
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
        response.setEmployeeCount(0); // TODO: 查询员工数量
        return response;
    }

    @Override
    public AimJobTypeDO getJobTypeById(Long id) {
        log.debug("查询岗位类型详情开始, id={}", id);

        // 单表查询，直接返回DO
        AimJobTypeDO entity = jobTypeMapper.selectByIdExcludeDeleted(id);
        if (entity == null) {
            throw new BusinessException(ErrorCodeEnum.AGENT_BUSINESS_ERROR, "岗位类型不存在");
        }

        return entity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createJobType(JobTypeCreateApiRequest request) {
        log.debug("创建岗位类型开始, code={}, name={}", request.getCode(), request.getName());

        // 校验编码唯一性
        validateCodeUnique(request.getCode(), null);

        // 构建实体
        AimJobTypeDO entity = new AimJobTypeDO();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setStatus(1); // 默认启用
        entity.setSortOrder(request.getSortOrder());
        entity.setDescription(request.getDescription());
        entity.setIsDefault(request.getIsDefault());
        entity.setIsDeleted(0);

        // 处理默认标记
        if (Integer.valueOf(1).equals(request.getIsDefault())) {
            handleDefaultFlag(null);
        }

        // 插入数据
        jobTypeMapper.insert(entity);

        log.info("创建岗位类型成功, id={}", entity.getId());
        return entity.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateJobType(JobTypeUpdateApiRequest request) {
        log.debug("更新岗位类型开始, id={}, name={}", request.getId(), request.getName());

        // 校验岗位类型是否存在
        AimJobTypeDO existing = jobTypeMapper.selectByIdExcludeDeleted(request.getId());
        if (existing == null) {
            throw new BusinessException(ErrorCodeEnum.AGENT_BUSINESS_ERROR, "岗位类型不存在");
        }

        // 处理默认标记
        if (Integer.valueOf(1).equals(request.getIsDefault())) {
            handleDefaultFlag(request.getId());
        }

        // 更新字段
        existing.setName(request.getName());
        existing.setSortOrder(request.getSortOrder());
        existing.setDescription(request.getDescription());
        existing.setIsDefault(request.getIsDefault());

        int affectedRows = jobTypeMapper.updateById(existing);

        log.info("更新岗位类型成功, id={}, affectedRows={}", request.getId(), affectedRows);
        return affectedRows > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(Long id, Integer status) {
        log.debug("更新岗位类型状态开始, id={}, status={}", id, status);

        // 校验岗位类型是否存在
        JobTypeDO existing = jobTypeMapper.selectByIdExcludeDeleted(id);
        if (existing == null) {
            throw new BusinessException(ErrorCodeEnum.AGENT_BUSINESS_ERROR, "岗位类型不存在");
        }

        // 更新状态
        existing.setStatus(status);
        jobTypeMapper.updateById(existing);

        log.info("更新岗位类型状态成功, id={}, status={}", id, status);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteJobType(Long id) {
        log.debug("删除岗位类型开始, id={}", id);

        // 校验岗位类型是否存在
        JobTypeDO existing = jobTypeMapper.selectByIdExcludeDeleted(id);
        if (existing == null) {
            throw new BusinessException(ErrorCodeEnum.AGENT_BUSINESS_ERROR, "岗位类型不存在");
        }

        // 校验是否为默认岗位
        if (Integer.valueOf(1).equals(existing.getIsDefault())) {
            throw new BusinessException(ErrorCodeEnum.AGENT_BUSINESS_ERROR, "默认岗位类型不可删除");
        }

        // 校验是否有员工绑定
        int employeeCount = countEmployeesByJobTypeId(id);
        if (employeeCount > 0) {
            throw new BusinessException(ErrorCodeEnum.AGENT_BUSINESS_ERROR, "存在绑定员工，无法删除");
        }

        // 逻辑删除
        existing.setIsDeleted(1);
        jobTypeMapper.updateById(existing);

        log.info("删除岗位类型成功, id={}", id);
    }

    @Override
    public Integer countEmployeesByJobTypeId(Long jobTypeId) {
        // TODO: 实现员工数量统计，需要注入 EmployeeMapper
        log.warn("员工数量统计功能暂未实现, jobTypeId: {}", jobTypeId);
        return 0;
    }

    /**
     * 校验编码唯一性
     *
     * @param code 编码
     * @param excludeId 排除的ID（更新时使用）
     */
    private void validateCodeUnique(String code, Long excludeId) {
        Long count;
        if (excludeId != null) {
            count = jobTypeMapper.countByCodeExcludeId(code, excludeId);
        } else {
            count = jobTypeMapper.countByCode(code);
        }

        if (count > 0) {
            throw new BusinessException(ErrorCodeEnum.AGENT_BUSINESS_ERROR, "岗位编码已存在");
        }
    }

    /**
     * 处理默认标记逻辑
     * 当设置某个岗位为默认时，清除其他岗位的默认标记
     *
     * @param currentId 当前岗位ID
     */
    private void handleDefaultFlag(Long currentId) {
        jobTypeMapper.clearOtherDefaultFlag(currentId);
    }

}
