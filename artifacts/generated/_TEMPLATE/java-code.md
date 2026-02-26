# Java 代码生成模板

本文件包含 Java 代码生成的标准模板，供 `java-code-generation.md` Skill 使用。

---

## Controller 模板

### 门面服务 Controller（AI 对话 - mall-chat）

```java
package com.aim.mall.chat.controller;

import com.mall.common.api.CommonResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.validation.Valid;

/**
 * AI 对话接口
 *
 * @author {author}
 * @since {date}
 */
@RestController
@RequestMapping("/app/api/v1/chat")
@Tag(name = "AI 对话接口", description = "提供 AI 智能对话能力")
@Slf4j
public class ChatController {

    @Autowired
    private ChatService chatService;

    /**
     * 发送消息（非流式）
     */
    @PostMapping("/message")
    @Operation(summary = "发送消息")
    public CommonResult<ChatResponse> sendMessage(
            @RequestBody @Valid ChatRequest request) {
        log.info("发送消息: {}", request);
        try {
            ChatResponse response = chatService.sendMessage(request);
            return CommonResult.success(response);
        } catch (Exception e) {
            log.error("发送消息失败", e);
            return CommonResult.failed("CHAT_ERROR", "对话失败");
        }
    }

    /**
     * 发送消息（流式 SSE）
     */
    @PostMapping("/stream")
    @Operation(summary = "流式对话")
    public SseEmitter streamChat(@RequestBody @Valid ChatRequest request) {
        log.info("流式对话: {}", request);
        return chatService.streamChat(request);
    }

    /**
     * 获取对话历史
     */
    @GetMapping("/history/{sessionId}")
    @Operation(summary = "获取对话历史")
    public CommonResult<List<ChatMessageVO>> getHistory(
            @PathVariable("sessionId") String sessionId) {
        log.info("获取对话历史, sessionId: {}", sessionId);
        try {
            List<ChatMessageVO> history = chatService.getHistory(sessionId);
            return CommonResult.success(history);
        } catch (Exception e) {
            log.error("获取对话历史失败", e);
            return CommonResult.failed("SYSTEM_ERROR", "查询失败");
        }
    }

    /**
     * 清空对话历史
     */
    @DeleteMapping("/history/{sessionId}")
    @Operation(summary = "清空对话历史")
    public CommonResult<Void> clearHistory(
            @PathVariable("sessionId") String sessionId) {
        log.info("清空对话历史, sessionId: {}", sessionId);
        try {
            chatService.clearHistory(sessionId);
            return CommonResult.success();
        } catch (Exception e) {
            log.error("清空对话历史失败", e);
            return CommonResult.failed("SYSTEM_ERROR", "操作失败");
        }
    }
}
```

### 门面服务 Controller（管理端）

```java
package com.aim.mall.{module}.controller.admin;

import com.mall.common.api.CommonResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * {功能描述}管理接口
 *
 * @author {author}
 * @since {date}
 */
@RestController
@RequestMapping("/admin/api/v1/{module}")
@Tag(name = "{功能描述}管理接口", description = "{功能描述}相关接口")
@Slf4j
public class {Module}AdminController {

    @Autowired
    private {Module}AdminService {module}AdminService;

    /**
     * 查询{功能描述}列表
     */
    @GetMapping("/list")
    @Operation(summary = "查询{功能描述}列表")
    public CommonResult<List<{Module}VO>> list() {
        log.info("查询{功能描述}列表");
        try {
            List<{Module}VO> list = {module}AdminService.list();
            return CommonResult.success(list);
        } catch (Exception e) {
            log.error("查询{功能描述}列表失败", e);
            return CommonResult.failed("SYSTEM_ERROR", "查询失败");
        }
    }

    /**
     * 获取{功能描述}详情
     */
    @GetMapping("/{id}")
    @Operation(summary = "获取{功能描述}详情")
    public CommonResult<{Module}VO> getById(@PathVariable("id") Long id) {
        log.info("获取{功能描述}详情, id: {}", id);
        try {
            {Module}VO vo = {module}AdminService.getById(id);
            return CommonResult.success(vo);
        } catch (Exception e) {
            log.error("获取{功能描述}详情失败, id: {}", id, e);
            return CommonResult.failed("SYSTEM_ERROR", "查询失败");
        }
    }

    /**
     * 创建{功能描述}
     */
    @PostMapping("/create")
    @Operation(summary = "创建{功能描述}")
    public CommonResult<Long> create(
            @RequestBody @Valid {Module}CreateRequest request) {
        log.info("创建{功能描述}: {}", request);
        try {
            Long id = {module}AdminService.create(request);
            return CommonResult.success(id);
        } catch (Exception e) {
            log.error("创建{功能描述}失败", e);
            return CommonResult.failed("SYSTEM_ERROR", "创建失败");
        }
    }

    /**
     * 更新{功能描述}
     */
    @PutMapping("/{id}")
    @Operation(summary = "更新{功能描述}")
    public CommonResult<Void> update(
            @PathVariable("id") Long id,
            @RequestBody @Valid {Module}UpdateRequest request) {
        log.info("更新{功能描述}, id: {}, request: {}", id, request);
        try {
            {module}AdminService.update(id, request);
            return CommonResult.success();
        } catch (Exception e) {
            log.error("更新{功能描述}失败, id: {}", id, e);
            return CommonResult.failed("SYSTEM_ERROR", "更新失败");
        }
    }

    /**
     * 删除{功能描述}
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "删除{功能描述}")
    public CommonResult<Void> delete(@PathVariable("id") Long id) {
        log.info("删除{功能描述}, id: {}", id);
        try {
            {module}AdminService.delete(id);
            return CommonResult.success();
        } catch (Exception e) {
            log.error("删除{功能描述}失败, id: {}", id, e);
            return CommonResult.failed("SYSTEM_ERROR", "删除失败");
        }
    }
}
```

### 应用服务 Controller（内部接口）

```java
package com.aim.mall.{module}.controller.inner;

import com.mall.common.api.CommonResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * {功能描述}内部接口
 *
 * @author {author}
 * @since {date}
 */
@RestController
@RequestMapping("/inner/api/v1/{module}")
@Tag(name = "{功能描述}内部接口", description = "供其他服务调用的内部接口")
@Slf4j
public class {Module}InnerController {

    @Autowired
    private {Module}Service {module}Service;

    /**
     * 获取{功能描述}详情
     */
    @GetMapping("/detail")
    @Operation(summary = "获取{功能描述}详情")
    public CommonResult<{Module}DTO> getById(@RequestParam("{module}Id") Long {module}Id) {
        log.info("获取{功能描述}详情, {module}Id: {}", {module}Id);
        try {
            {Module}DTO dto = {module}Service.getById({module}Id);
            return CommonResult.success(dto);
        } catch (Exception e) {
            log.error("获取{功能描述}详情失败", e);
            return CommonResult.failed("SYSTEM_ERROR", "查询失败");
        }
    }

    /**
     * 查询{功能描述}列表
     */
    @GetMapping("/list")
    @Operation(summary = "查询{功能描述}列表")
    public CommonResult<List<{Module}DTO>> listByStatus(
            @RequestParam(name = "status", required = false) Integer status) {
        log.info("查询{功能描述}列表, status: {}", status);
        try {
            List<{Module}DTO> list = {module}Service.listByStatus(status);
            return CommonResult.success(list);
        } catch (Exception e) {
            log.error("查询{功能描述}列表失败", e);
            return CommonResult.failed("SYSTEM_ERROR", "查询失败");
        }
    }

    /**
     * 创建{功能描述}
     */
    @PostMapping("/create")
    @Operation(summary = "创建{功能描述}")
    public CommonResult<Long> create(@RequestBody {Module}CreateDTO dto) {
        log.info("创建{功能描述}: {}", dto);
        try {
            Long id = {module}Service.create(dto);
            return CommonResult.success(id);
        } catch (Exception e) {
            log.error("创建{功能描述}失败", e);
            return CommonResult.failed("SYSTEM_ERROR", "创建失败");
        }
    }

    /**
     * 更新{功能描述}
     */
    @PostMapping("/update")
    @Operation(summary = "更新{功能描述}")
    public CommonResult<Void> update(@RequestBody {Module}UpdateDTO dto) {
        log.info("更新{功能描述}: {}", dto);
        try {
            {module}Service.update(dto);
            return CommonResult.success();
        } catch (Exception e) {
            log.error("更新{功能描述}失败", e);
            return CommonResult.failed("SYSTEM_ERROR", "更新失败");
        }
    }

    /**
     * 删除{功能描述}
     */
    @PostMapping("/delete")
    @Operation(summary = "删除{功能描述}")
    public CommonResult<Void> delete(@RequestBody {Module}DeleteDTO dto) {
        log.info("删除{功能描述}: {}", dto);
        try {
            {module}Service.delete(dto.getId());
            return CommonResult.success();
        } catch (Exception e) {
            log.error("删除{功能描述}失败", e);
            return CommonResult.failed("SYSTEM_ERROR", "删除失败");
        }
    }
}
```

---

## Service 模板

### Service 接口

```java
package com.aim.mall.{module}.service;

import java.util.List;

/**
 * {功能描述}服务接口
 *
 * @author {author}
 * @since {date}
 */
public interface {Module}Service {

    /**
     * 根据ID获取{功能描述}
     */
    {Module}DTO getById(Long id);

    /**
     * 根据状态查询列表
     */
    List<{Module}DTO> listByStatus(Integer status);

    /**
     * 创建{功能描述}
     */
    Long create({Module}CreateDTO dto);

    /**
     * 更新{功能描述}
     */
    void update({Module}UpdateDTO dto);

    /**
     * 删除{功能描述}
     */
    void delete(Long id);
}
```

### Service 实现

```java
package com.aim.mall.{module}.service.impl;

import com.aim.mall.{module}.domain.entity.{Module}DO;
import com.aim.mall.{module}.mapper.{Module}Mapper;
import com.aim.mall.{module}.service.{Module}Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * {功能描述}服务实现
 *
 * @author {author}
 * @since {date}
 */
@Service
@Slf4j
public class {Module}ServiceImpl implements {Module}Service {

    @Autowired
    private {Module}Mapper {module}Mapper;

    @Override
    public {Module}DTO getById(Long id) {
        {Module}DO {module}DO = {module}Mapper.selectById(id);
        if ({module}DO == null || {module}DO.getIsDeleted() == 1) {
            return null;
        }
        return convertToDTO({module}DO);
    }

    @Override
    public List<{Module}DTO> listByStatus(Integer status) {
        List<{Module}DO> list = {module}Mapper.selectByStatus(status);
        return list.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long create({Module}CreateDTO dto) {
        // 1. 参数校验
        validateCreateDTO(dto);

        // 2. 构造实体
        {Module}DO {module}DO = new {Module}DO();
        {module}DO.setName(dto.getName());
        {module}DO.setStatus(1); // 默认启用
        {module}DO.setCreateTime(LocalDateTime.now());
        {module}DO.setUpdateTime(LocalDateTime.now());
        {module}DO.setIsDeleted(0);

        // 3. 保存数据
        {module}Mapper.insert({module}DO);

        log.info("创建{功能描述}成功, id: {}", {module}DO.getId());
        return {module}DO.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update({Module}UpdateDTO dto) {
        // 1. 参数校验
        validateUpdateDTO(dto);

        // 2. 查询原数据
        {Module}DO existing = {module}Mapper.selectById(dto.getId());
        if (existing == null || existing.getIsDeleted() == 1) {
            throw new BusinessException("{MODULE}_NOT_FOUND", "{功能描述}不存在");
        }

        // 3. 更新字段
        existing.setName(dto.getName());
        existing.setUpdateTime(LocalDateTime.now());

        // 4. 保存数据
        {module}Mapper.updateById(existing);

        log.info("更新{功能描述}成功, id: {}", dto.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        // 1. 查询原数据
        {Module}DO existing = {module}Mapper.selectById(id);
        if (existing == null || existing.getIsDeleted() == 1) {
            throw new BusinessException("{MODULE}_NOT_FOUND", "{功能描述}不存在");
        }

        // 2. 逻辑删除
        existing.setIsDeleted(1);
        existing.setUpdateTime(LocalDateTime.now());
        {module}Mapper.updateById(existing);

        log.info("删除{功能描述}成功, id: {}", id);
    }

    // ============ 私有方法 ============

    private void validateCreateDTO({Module}CreateDTO dto) {
        if (dto == null) {
            throw new MethodArgumentValidationException("请求参数不能为空");
        }
        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
            throw new MethodArgumentValidationException("名称不能为空");
        }
        if (dto.getName().length() > 100) {
            throw new MethodArgumentValidationException("名称长度不能超过100");
        }
    }

    private void validateUpdateDTO({Module}UpdateDTO dto) {
        if (dto == null || dto.getId() == null) {
            throw new MethodArgumentValidationException("ID不能为空");
        }
        if (dto.getName() != null && dto.getName().length() > 100) {
            throw new MethodArgumentValidationException("名称长度不能超过100");
        }
    }

    private {Module}DTO convertToDTO({Module}DO {module}DO) {
        {Module}DTO dto = new {Module}DTO();
        dto.setId({module}DO.getId());
        dto.setName({module}DO.getName());
        dto.setStatus({module}DO.getStatus());
        dto.setCreateTime({module}DO.getCreateTime());
        dto.setUpdateTime({module}DO.getUpdateTime());
        return dto;
    }
}
```

---

## Mapper 模板

```java
package com.aim.mall.{module}.mapper;

import com.aim.mall.{module}.domain.entity.{Module}DO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * {功能描述}数据访问层
 *
 * @author {author}
 * @since {date}
 */
@Mapper
public interface {Module}Mapper {

    /**
     * 插入记录
     */
    int insert({Module}DO record);

    /**
     * 根据ID查询
     */
    {Module}DO selectById(@Param("id") Long id);

    /**
     * 根据状态查询列表
     */
    List<{Module}DO> selectByStatus(@Param("status") Integer status);

    /**
     * 更新记录
     */
    int updateById({Module}DO record);

    /**
     * 批量插入
     */
    int batchInsert(@Param("list") List<{Module}DO> list);
}
```

### Mapper XML

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.aim.mall.{module}.mapper.{Module}Mapper">

    <resultMap id="BaseResultMap" type="com.aim.mall.{module}.domain.entity.{Module}DO">
        <id column="id" property="id"/>
        <result column="name" property="name"/>
        <result column="status" property="status"/>
        <result column="create_time" property="createTime"/>
        <result column="update_time" property="updateTime"/>
        <result column="is_deleted" property="isDeleted"/>
    </resultMap>

    <sql id="Base_Column_List">
        id, name, status, create_time, update_time, is_deleted
    </sql>

    <insert id="insert" useGeneratedKeys="true" keyProperty="id">
        INSERT INTO {table_name} (
            name, status, create_time, update_time, is_deleted
        ) VALUES (
            #{name}, #{status}, #{createTime}, #{updateTime}, #{isDeleted}
        )
    </insert>

    <select id="selectById" resultMap="BaseResultMap">
        SELECT <include refid="Base_Column_List"/>
        FROM {table_name}
        WHERE id = #{id}
        AND is_deleted = 0
    </select>

    <select id="selectByStatus" resultMap="BaseResultMap">
        SELECT <include refid="Base_Column_List"/>
        FROM {table_name}
        WHERE is_deleted = 0
        <if test="status != null">
            AND status = #{status}
        </if>
        ORDER BY create_time DESC
    </select>

    <update id="updateById">
        UPDATE {table_name}
        <set>
            <if test="name != null">name = #{name},</if>
            <if test="status != null">status = #{status},</if>
            update_time = #{updateTime},
            is_deleted = #{isDeleted}
        </set>
        WHERE id = #{id}
    </update>

    <insert id="batchInsert">
        INSERT INTO {table_name} (
            name, status, create_time, update_time, is_deleted
        ) VALUES
        <foreach collection="list" item="item" separator=",">
            (#{item.name}, #{item.status}, #{item.createTime}, #{item.updateTime}, #{item.isDeleted})
        </foreach>
    </insert>

</mapper>
```

---

## DTO/Request/Response 模板

### Create Request

```java
package com.aim.mall.{module}.api.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 创建{功能描述}请求
 *
 * @author {author}
 * @since {date}
 */
@Data
public class {Module}CreateRequest implements Serializable {

    private static final long serialVersionUID = -1L;

    /**
     * 名称
     */
    @NotBlank(message = "名称不能为空")
    @Size(max = 100, message = "名称长度不能超过100")
    private String name;

    /**
     * 描述
     */
    @Size(max = 500, message = "描述长度不能超过500")
    private String description;
}
```

### Update Request

```java
package com.aim.mall.{module}.api.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 更新{功能描述}请求
 *
 * @author {author}
 * @since {date}
 */
@Data
public class {Module}UpdateRequest implements Serializable {

    private static final long serialVersionUID = -1L;

    /**
     * ID
     */
    @NotNull(message = "ID不能为空")
    private Long id;

    /**
     * 名称
     */
    @Size(max = 100, message = "名称长度不能超过100")
    private String name;

    /**
     * 描述
     */
    @Size(max = 500, message = "描述长度不能超过500")
    private String description;
}
```

### VO/Response

```java
package com.aim.mall.{module}.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * {功能描述}视图对象
 *
 * @author {author}
 * @since {date}
 */
@Data
public class {Module}VO implements Serializable {

    private static final long serialVersionUID = -1L;

    /**
     * ID
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;
}
```

---

## Feign Client 模板

```java
package com.aim.mall.{consumer}.feign;

import com.mall.common.api.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * {功能描述} Feign 客户端
 *
 * @author {author}
 * @since {date}
 */
@FeignClient(name = "{provider-service}", path = "/inner/api/v1/{module}")
public interface {Module}FeignService {

    /**
     * 获取{功能描述}详情
     */
    @GetMapping("/detail")
    CommonResult<{Module}DTO> getById(@RequestParam("{module}Id") Long {module}Id);

    /**
     * 查询{功能描述}列表
     */
    @GetMapping("/list")
    CommonResult<List<{Module}DTO>> listByStatus(@RequestParam("status") Integer status);

    /**
     * 创建{功能描述}
     */
    @PostMapping("/create")
    CommonResult<Long> create(@RequestBody {Module}CreateDTO dto);

    /**
     * 更新{功能描述}
     */
    @PostMapping("/update")
    CommonResult<Void> update(@RequestBody {Module}UpdateDTO dto);

    /**
     * 删除{功能描述}
     */
    @PostMapping("/delete")
    CommonResult<Void> delete(@RequestBody {Module}DeleteDTO dto);
}
```

---

## Entity/DO 模板

```java
package com.aim.mall.{module}.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * {功能描述}实体
 *
 * @author {author}
 * @since {date}
 */
@Data
@TableName("{table_name}")
public class {Module}DO {

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 是否删除
     */
    private Integer isDeleted;
}
```

---

## 模板变量说明

| 变量名 | 说明 | 示例 |
|--------|------|------|
| `{module}` | 模块名（小写） | `agent`, `user` |
| `{Module}` | 模块名（大驼峰） | `Agent`, `User` |
| `{MODULE}` | 模块名（大写下划线） | `AGENT`, `USER` |
| `{function}` | 功能描述 | `智能员工`, `用户` |
| `{service}` | 服务名 | `mall-agent` |
| `{table_name}` | 表名 | `agent`, `user_info` |
| `{author}` | 作者 | `zhangsan` |
| `{date}` | 日期 | `2026-02-25` |
