package com.aim.mall.agent.mapper;

import com.aim.mall.agent.domain.entity.AimJobTypeDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 岗位类型数据访问层
 *
 * @author Qoder
 * @since 2026/2/26
 */
@Mapper
public interface JobTypeMapper extends BaseMapper<AimJobTypeDO> {

    /**
     * 根据编码查询岗位类型（排除已删除）
     *
     * @param code 岗位编码
     * @return 岗位类型
     */
    AimJobTypeDO selectByCode(@Param("code") String code);

    /**
     * 根据编码查询数量（排除指定ID，用于唯一性校验）
     *
     * @param code 岗位编码
     * @param excludeId 排除的ID
     * @return 数量
     */
    Long countByCodeExcludeId(@Param("code") String code, @Param("excludeId") Long excludeId);

    /**
     * 根据编码查询数量
     *
     * @param code 岗位编码
     * @return 数量
     */
    Long countByCode(@Param("code") String code);

    /**
     * 查询所有启用的岗位类型（按排序号升序）
     *
     * @return 岗位类型列表
     */
    List<AimJobTypeDO> selectEnabledList();

    /**
     * 根据ID查询岗位类型（排除已删除）
     *
     * @param id 岗位ID
     * @return 岗位类型
     */
    AimJobTypeDO selectByIdExcludeDeleted(@Param("id") Long id);

    /**
     * 查询默认岗位类型
     *
     * @return 岗位类型
     */
    AimJobTypeDO selectDefaultJobType();

    /**
     * 清除其他岗位的默认标记
     *
     * @param excludeId 排除的ID
     * @return 影响行数
     */
    int clearOtherDefaultFlag(@Param("excludeId") Long excludeId);

    /**
     * 根据关键字分页查询岗位类型
     *
     * @param keyword 关键字
     * @param offset 偏移量
     * @param limit 限制数
     * @return 岗位类型列表
     */
    List<AimJobTypeDO> selectPageByKeyword(@Param("keyword") String keyword, @Param("offset") Integer offset, @Param("limit") Integer limit);

    /**
     * 根据关键字查询总数
     *
     * @param keyword 关键字
     * @return 总数
     */
    Long countByKeyword(@Param("keyword") String keyword);
}
