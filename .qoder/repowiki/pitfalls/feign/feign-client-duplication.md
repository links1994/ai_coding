# Feign 客户端重复创建

## 基本信息

- **分类**: Feign 相关
- **严重程度**: 高
- **发现时间**: 2026-02-27
- **发现人**: Qoder
- **相关 Program**: P-2026-001-REQ-018

## 问题描述

在门面服务（mall-admin/mall-app/mall-chat）中创建 Feign 客户端，而不是使用 API 模块（mall-agent-api）中已定义的 RemoteService。

这违反了架构规范：
1. 门面服务禁止创建 `feign/` 目录
2. Feign 客户端应当统一在 API 模块中定义
3. 命名应当遵循 `{Module}RemoteService` 格式

## 违规模式

检测特征：
- 文件路径包含 `mall-admin/src/main/java/com/aim/mall/admin/feign/`
- 使用了 `@FeignClient` 注解
- 类名以 `FeignClient` 结尾而非 `RemoteService`

## 违规示例

### 错误代码

```java
// 文件: mall-admin/src/main/java/com/aim/mall/admin/feign/JobTypeFeignClient.java
@FeignClient(name = "mall-agent", path = "/inner/api/v1/job-types")
public interface JobTypeFeignClient {
    @PostMapping("/list")
    CommonResult<PageData<JobTypeApiResponse>> pageList(@RequestBody JobTypePageApiRequest request);
    // ...
}
```

### 错误用法

```java
@Service
@RequiredArgsConstructor
public class JobTypeAdminService {
    private final JobTypeFeignClient jobTypeFeignClient;  // 错误：使用了门面服务中定义的 Feign 客户端
}
```

## 正确方案

### 方案 1: 使用 API 模块的 RemoteService（推荐）

```java
// 引用 mall-agent-api 中的 AgentRemoteService
@Service
@RequiredArgsConstructor
public class JobTypeAdminService {
    private final AgentRemoteService agentRemoteService;  // 正确：使用 API 模块的 RemoteService
}
```

### 修改步骤

1. 删除门面服务中的 `feign/` 目录
2. 在 pom.xml 中添加 API 模块依赖（如果尚未添加）
3. 注入 API 模块的 RemoteService 替代自定义 Feign 客户端
4. 更新 Controller 中的方法调用

## 相关规范

- [架构规范 - Feign 客户端命名](../../../../rules/05-architecture-standards.md)
- [架构规范 - 门面服务禁止 feign 目录](../../../../rules/05-architecture-standards.md)

## 预防措施

1. **代码审查**: 审查时检查门面服务是否创建了 feign/ 目录
2. **知识库查询**: 代码质量分析时查询本坑点档案
3. **架构评审**: 新功能设计时确认 Feign 客户端位置

## 参考链接

- 相关文件:
  - `repos/mall-admin/src/main/java/com/aim/mall/admin/feign/JobTypeFeignClient.java`
  - `repos/mall-admin/src/main/java/com/aim/mall/admin/controller/JobTypeAdminController.java`
