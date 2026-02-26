package com.aim.mall.agent.service.impl;

import com.aim.mall.agent.domain.dto.JobTypeCreateDTO;
import com.aim.mall.agent.domain.dto.JobTypeListQuery;
import com.aim.mall.agent.domain.dto.JobTypeUpdateDTO;
import com.aim.mall.agent.domain.entity.JobTypeDO;
import com.aim.mall.agent.domain.enums.ErrorCodeEnum;
import com.aim.mall.agent.domain.exception.BusinessException;
import com.aim.mall.agent.mapper.JobTypeMapper;
import com.aim.mall.agent.service.JobTypeService;
import com.mall.common.api.CommonResult;
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
    public CommonResult<CommonResult.PageData<JobTypeDO>> list(JobTypeListQuery query) {
        log.info("查询岗位类型列表, keyword={}, pageNum={}, pageSize={}", 
                query.getKeyword(), query.getPageNum(), query.getPageSize());

        // 计算分页参数
        int offset = (query.getPageNum() - 1) * query.getPageSize();
        int limit = query.getPageSize();

        // 查询数据（单表查询，直接返回DO）
        List<JobTypeDO> records = jobTypeMapper.selectPageByKeyword(query.getKeyword(), offset, limit);
        Long total = jobTypeMapper.countByKeyword(query.getKeyword());

        return CommonResult.pageSuccess(records, total);
    }

    @Override
    public JobTypeDO getById(Long id) {
        log.info("查询岗位类型详情, id={}", id);

        // 单表查询，直接返回DO
        JobTypeDO entity = jobTypeMapper.selectByIdExcludeDeleted(id);
        if (entity == null) {
            throw new BusinessException(ErrorCodeEnum.AGENT_BUSINESS_ERROR, "岗位类型不存在");
        }

        return entity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JobTypeDO create(JobTypeCreateDTO dto) {
        log.info("创建岗位类型, code={}, name={}", dto.getCode(), dto.getName());

        // 校验编码唯一性
        validateCodeUnique(dto.getCode(), null);

        // 构建实体
        JobTypeDO entity = new JobTypeDO();
        entity.setCode(dto.getCode());
        entity.setName(dto.getName());
        entity.setStatus(1); // 默认启用
        entity.setSortOrder(dto.getSortOrder());
        entity.setDescription(dto.getDescription());
        entity.setIsDefault(dto.getIsDefault());
        entity.setIsDeleted(0);

        // 处理默认标记
        if (Integer.valueOf(1).equals(dto.getIsDefault())) {
            handleDefaultFlag(null);
        }

        // 插入数据
        jobTypeMapper.insert(entity);

        log.info("创建岗位类型成功, id={}", entity.getId());
        return entity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JobTypeDO update(JobTypeUpdateDTO dto) {
        log.info("更新岗位类型, id={}, name={}", dto.getId(), dto.getName());

        // 校验岗位类型是否存在
        JobTypeDO existing = jobTypeMapper.selectByIdExcludeDeleted(dto.getId());
        if (existing == null) {
            throw new BusinessException(ErrorCodeEnum.AGENT_BUSINESS_ERROR, "岗位类型不存在");
        }

        // 处理默认标记
        if (Integer.valueOf(1).equals(dto.getIsDefault())) {
            handleDefaultFlag(dto.getId());
        }

        // 更新字段
        existing.setName(dto.getName());
        existing.setSortOrder(dto.getSortOrder());
        existing.setDescription(dto.getDescription());
        existing.setIsDefault(dto.getIsDefault());

        jobTypeMapper.updateById(existing);

        log.info("更新岗位类型成功, id={}", dto.getId());
        return existing;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(Long id, Integer status) {
        log.info("更新岗位类型状态, id={}, status={}", id, status);

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
    public void delete(Long id) {
        log.info("删除岗位类型, id={}", id);

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
        // 暂时返回0，后续实现
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
