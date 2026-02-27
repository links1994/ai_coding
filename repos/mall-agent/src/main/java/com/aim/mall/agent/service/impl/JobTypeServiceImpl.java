package com.aim.mall.agent.service.impl;

import com.aim.mall.agent.api.dto.request.JobTypeCreateApiRequest;
import com.aim.mall.agent.api.dto.request.JobTypePageApiRequest;
import com.aim.mall.agent.api.dto.request.JobTypeStatusApiRequest;
import com.aim.mall.agent.api.dto.request.JobTypeUpdateApiRequest;
import com.aim.mall.agent.api.dto.response.JobTypeApiResponse;
import com.aim.mall.agent.domain.entity.AimJobTypeDO;
import com.aim.mall.agent.domain.enums.ErrorCodeEnum;
import com.aim.mall.agent.domain.exception.BusinessException;
import com.aim.mall.agent.mapper.JobTypeMapper;
import com.aim.mall.agent.service.JobTypeService;
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
    public List<JobTypeApiResponse> pageList(JobTypePageApiRequest request) {
        Integer offset = (request.getPageNum() - 1) * request.getPageSize();
        List<AimJobTypeDO> list = jobTypeMapper.selectPageByKeyword(
                request.getKeyword(), offset, request.getPageSize());
        return list.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Long countByKeyword(String keyword) {
        return jobTypeMapper.countByKeyword(keyword);
    }

    @Override
    public JobTypeApiResponse getById(Long jobTypeId) {
        AimJobTypeDO entity = jobTypeMapper.selectByIdExcludeDeleted(jobTypeId);
        if (entity == null) {
            throw new BusinessException(ErrorCodeEnum.AGENT_BUSINESS_ERROR, "岗位类型不存在");
        }
        return convertToResponse(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long create(JobTypeCreateApiRequest request) {
        // 校验编码唯一性
        validateCodeUnique(request.getCode(), null);

        // 处理默认标记逻辑
        if (Integer.valueOf(1).equals(request.getIsDefault())) {
            handleDefaultFlag(null);
        }

        // 构建实体
        AimJobTypeDO entity = new AimJobTypeDO();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setSortOrder(request.getSortOrder());
        entity.setDescription(request.getDescription());
        entity.setIsDefault(request.getIsDefault());
        entity.setStatus(1); // 默认启用

        jobTypeMapper.insert(entity);
        return entity.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(JobTypeUpdateApiRequest request) {
        // 校验岗位类型是否存在
        AimJobTypeDO existing = jobTypeMapper.selectByIdExcludeDeleted(request.getId());
        if (existing == null) {
            throw new BusinessException(ErrorCodeEnum.AGENT_BUSINESS_ERROR, "岗位类型不存在");
        }

        // 处理默认标记逻辑
        if (Integer.valueOf(1).equals(request.getIsDefault())) {
            handleDefaultFlag(request.getId());
        }

        // 更新实体
        AimJobTypeDO entity = new AimJobTypeDO();
        entity.setId(request.getId());
        entity.setName(request.getName());
        entity.setSortOrder(request.getSortOrder());
        entity.setDescription(request.getDescription());
        entity.setIsDefault(request.getIsDefault());

        jobTypeMapper.updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(JobTypeStatusApiRequest request) {
        // 校验岗位类型是否存在
        AimJobTypeDO existing = jobTypeMapper.selectByIdExcludeDeleted(request.getId());
        if (existing == null) {
            throw new BusinessException(ErrorCodeEnum.AGENT_BUSINESS_ERROR, "岗位类型不存在");
        }

        // 校验状态值
        if (request.getStatus() != 0 && request.getStatus() != 1) {
            throw new BusinessException(ErrorCodeEnum.AGENT_PARAM_ERROR, "状态值非法");
        }

        // 更新状态
        AimJobTypeDO entity = new AimJobTypeDO();
        entity.setId(request.getId());
        entity.setStatus(request.getStatus());

        jobTypeMapper.updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long jobTypeId) {
        // 校验岗位类型是否存在
        AimJobTypeDO existing = jobTypeMapper.selectByIdExcludeDeleted(jobTypeId);
        if (existing == null) {
            throw new BusinessException(ErrorCodeEnum.AGENT_BUSINESS_ERROR, "岗位类型不存在");
        }

        // 校验是否为默认岗位
        if (Integer.valueOf(1).equals(existing.getIsDefault())) {
            throw new BusinessException(ErrorCodeEnum.AGENT_BUSINESS_ERROR, "默认岗位类型不可删除");
        }

        // 校验是否有员工绑定（简化实现，实际应查询员工表）
        // TODO: 查询aim_employee表校验员工数量

        // 逻辑删除
        jobTypeMapper.deleteById(jobTypeId);
    }

    /**
     * 校验编码唯一性
     *
     * @param code      编码
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
     *
     * @param currentId 当前岗位ID（为null表示新增）
     */
    private void handleDefaultFlag(Long currentId) {
        jobTypeMapper.clearOtherDefaultFlag(currentId);
    }

    /**
     * 转换为响应对象
     *
     * @param entity 实体
     * @return 响应对象
     */
    private JobTypeApiResponse convertToResponse(AimJobTypeDO entity) {
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
        // TODO: 查询员工数量
        response.setEmployeeCount(0);
        return response;
    }
}
