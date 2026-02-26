package com.aim.mall.agent.mapper;

import com.aim.mall.agent.domain.entity.JobTypeDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 岗位类型数据访问层
 *
 * @author Qoder
 * @since 2026/2/26
 */
@Mapper
public interface JobTypeMapper extends BaseMapper<JobTypeDO> {

    /**
     * 根据编码查询岗位类型（排除已删除）
     *
     * @param code 岗位编码
     * @return 岗位类型
     */
    @Select("SELECT * FROM aim_job_type WHERE code = #{code} AND is_deleted = 0 LIMIT 1")
    JobTypeDO selectByCode(@Param("code") String code);

    /**
     * 根据编码查询数量（排除指定ID，用于唯一性校验）
     *
     * @param code 岗位编码
     * @param excludeId 排除的ID
     * @return 数量
     */
    @Select("SELECT COUNT(*) FROM aim_job_type WHERE code = #{code} AND is_deleted = 0 AND id != #{excludeId}")
    Long countByCodeExcludeId(@Param("code") String code, @Param("excludeId") Long excludeId);

    /**
     * 根据编码查询数量
     *
     * @param code 岗位编码
     * @return 数量
     */
    @Select("SELECT COUNT(*) FROM aim_job_type WHERE code = #{code} AND is_deleted = 0")
    Long countByCode(@Param("code") String code);

    /**
     * 查询所有启用的岗位类型（按排序号升序）
     *
     * @return 岗位类型列表
     */
    @Select("SELECT * FROM aim_job_type WHERE status = 1 AND is_deleted = 0 ORDER BY sort_order ASC, create_time DESC")
    List<JobTypeDO> selectEnabledList();

    /**
     * 根据ID查询岗位类型（排除已删除）
     *
     * @param id 岗位ID
     * @return 岗位类型
     */
    @Select("SELECT * FROM aim_job_type WHERE id = #{id} AND is_deleted = 0 LIMIT 1")
    JobTypeDO selectByIdExcludeDeleted(@Param("id") Long id);

    /**
     * 查询默认岗位类型
     *
     * @return 岗位类型
     */
    @Select("SELECT * FROM aim_job_type WHERE is_default = 1 AND is_deleted = 0 LIMIT 1")
    JobTypeDO selectDefaultJobType();

    /**
     * 清除其他岗位的默认标记
     *
     * @param excludeId 排除的ID
     * @return 影响行数
     */
    @Select("UPDATE aim_job_type SET is_default = 0, update_time = NOW() WHERE id != #{excludeId} AND is_default = 1 AND is_deleted = 0")
    int clearOtherDefaultFlag(@Param("excludeId") Long excludeId);

    /**
     * 根据关键字分页查询岗位类型
     *
     * @param keyword 关键字
     * @param offset 偏移量
     * @param limit 限制数
     * @return 岗位类型列表
     */
    @Select("<script>" +
            "SELECT * FROM aim_job_type WHERE is_deleted = 0 " +
            "<if test='keyword != null and keyword != \"\"'>" +
            "AND (name LIKE CONCAT('%', #{keyword}, '%') OR description LIKE CONCAT('%', #{keyword}, '%')) " +
            "</if>" +
            "ORDER BY sort_order ASC, create_time DESC " +
            "LIMIT #{limit} OFFSET #{offset}" +
            "</script>")
    List<JobTypeDO> selectPageByKeyword(@Param("keyword") String keyword, @Param("offset") Integer offset, @Param("limit") Integer limit);

    /**
     * 根据关键字查询总数
     *
     * @param keyword 关键字
     * @return 总数
     */
    @Select("<script>" +
            "SELECT COUNT(*) FROM aim_job_type WHERE is_deleted = 0 " +
            "<if test='keyword != null and keyword != \"\"'>" +
            "AND (name LIKE CONCAT('%', #{keyword}, '%') OR description LIKE CONCAT('%', #{keyword}, '%')) " +
            "</if>" +
            "</script>")
    Long countByKeyword(@Param("keyword") String keyword);
}
