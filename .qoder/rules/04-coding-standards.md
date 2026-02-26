---
trigger: model_decision
description: Java 编码规范
---

# Java 编码规范

本规范定义 Java 代码的编写标准，所有生成的代码必须遵循。

---

## 1. 代码质量要求

### 1.1 Java 21 新特性

推荐使用以下 Java 21 新特性：

- **记录类（Record）**：用于 DTO、VO 等数据传输对象
- **密封类（Sealed Class）**：用于定义受限的类层次结构
- **模式匹配**：用于 instanceof 和 switch 表达式
- **文本块**：用于多行字符串（SQL、JSON 等）

### 1.2 Lombok 注解

合理使用 Lombok 注解减少样板代码：

| 注解 | 用途 | 使用场景 |
|------|------|----------|
| `@Data` | 生成 getter/setter/toString/equals/hashCode | 实体类、DTO |
| `@Getter` | 只生成 getter | 只读对象 |
| `@Setter` | 只生成 setter | 可变对象 |
| `@Builder` | 生成建造者模式 | 复杂对象的创建 |
| `@Slf4j` | 生成日志对象 | 需要日志的类 |
| `@NoArgsConstructor` | 无参构造 | 需要无参构造的类 |
| `@AllArgsConstructor` | 全参构造 | 需要全参构造的类 |

### 1.3 序列化规范

Request/Response 对象必须实现 `Serializable` 接口：

```java
@Data
public class AgentCreateRequest implements Serializable {
    private static final long serialVersionUID = -1L;
    // ...
}
```

**注意**：`serialVersionUID` 统一设置为 `-1L`。

### 1.4 时间格式化

`LocalDateTime` 字段必须使用 `@JsonFormat` 注解：

```java
@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
private LocalDateTime createdAt;
```

---

## 2. 实现细节规范

### 2.1 DO 转换方式

**禁止使用** `BeanUtils.copyProperties`，使用手动 convert 方法。

### 2.2 数据库访问规范

**禁止使用的特性**：

- `QueryWrapper` / `LambdaQueryWrapper`
- `SELECT *`
- MyBatis 的 `<script>` 标签

### 2.3 批量操作规范

**禁止在循环中单条 CRUD**，必须使用批量操作。

### 2.4 Excel 处理规范

- 使用流式读取（Streaming）
- 监控内存使用
- 分批处理（推荐批次大小 1000 条）

---

## 3. 编码规范

### 3.1 命名规范

#### 类命名

| 类型 | 命名规则 | 示例 |
|------|----------|------|
| 实体类 | 大驼峰 + DO 后缀 | `AgentDO`、`UserDO` |
| DTO | 大驼峰 + DTO 后缀 | `AgentDTO`、`AgentCreateDTO` |
| VO | 大驼峰 + VO 后缀 | `AgentVO` |
| Request | 大驼峰 + Request 后缀 | `AgentCreateRequest` |
| Response | 大驼峰 + Response 后缀 | `AgentResponse` |
| Service | 大驼峰 + Service 后缀 | `AgentService` |
| ServiceImpl | 大驼峰 + ServiceImpl 后缀 | `AgentServiceImpl` |
| Mapper | 大驼峰 + Mapper 后缀 | `AgentMapper` |
| Controller | 大驼峰 + Controller 后缀 | `AgentController` |
| FeignClient | 大驼峰 + FeignClient 后缀 | `AgentFeignClient` |
| Config | 大驼峰 + Config 后缀 | `FeignConfig` |
| Exception | 大驼峰 + Exception 后缀 | `BusinessException` |
| Enum | 大驼峰 + Enum 后缀 | `AgentStatusEnum` |

#### 方法命名

| 操作 | 命名规则 | 示例 |
|------|----------|------|
| 查询单个 | `getById`、`getByXXX` | `getById(Long id)` |
| 查询列表 | `listXXX`、`queryXXX` | `listByStatus(Integer status)` |
| 分页查询 | `pageXXX` | `pageAgents(AgentQueryRequest request)` |
| 创建 | `createXXX`、`save` | `createAgent(AgentCreateRequest request)` |
| 更新 | `updateXXX` | `updateAgent(AgentUpdateRequest request)` |
| 删除 | `deleteXXX`、`remove` | `deleteById(Long id)` |
| 转换 | `convertToXXX` | `convertToVO(Agent agent)` |
| 校验 | `validateXXX` | `validateCreateRequest(AgentCreateRequest request)` |

#### 变量命名

- 使用驼峰命名法
- **禁止**使用拼音或不规范缩写
- 布尔变量使用 `is`、`has`、`can` 等前缀

```java
// 正确
private String agentName;
private boolean isActive;
private List<Agent> agentList;

// 错误
private String agent_name;  // 下划线命名
private String xm;          // 拼音缩写
private boolean active;     // 布尔值无前缀
```

### 3.2 包结构组织

```
{服务名}/src/main/java/com/aim/mall/{模块}/
├── controller/          # 接口层
│   ├── admin/           # 管理端接口（门面服务）
│   ├── app/             # 客户端接口（门面服务）
│   └── internal/        # 内部接口（应用/支撑服务）
├── service/             # 应用层
│   └── impl/
├── mapper/              # 基础设施层
├── domain/              # 领域层
│   ├── entity/          # 实体类
│   ├── enums/           # 枚举类（非远程调用相关）
│   ├── vo/              # 视图对象
│   └── exception/       # 异常类
├── api/                 # API 传输层
│   ├── dto/             # 传输对象
│   │   ├── request/     # 请求对象 (XxxRequest)
│   │   └── response/    # 响应对象 (XxxResponse)
│   ├── enums/           # 枚举类（远程调用相关）
│   └── feign/           # Feign 客户端
├── mq/                  # 消息队列
│   └── consumer/        # 消息消费者
└── config/              # 配置类
```

### 3.3 参数注解规范

```java
// 路径参数
@GetMapping("/{id}")
public CommonResult<AgentVO> getById(@PathVariable("id") Long id)

// Query 参数
@GetMapping("/list")
public CommonResult<List<AgentVO>> listByStatus(
    @RequestParam(name = "status", required = false) Integer status)

// RequestBody
@PostMapping("/create")
public CommonResult<Long> create(
    @RequestBody @Valid AgentCreateRequest request)

// RequestHeader
@PostMapping("/create")
public CommonResult<Long> create(
    @RequestHeader(USER_TOKEN_HEADER) String user,
    @RequestBody @Valid AgentCreateRequest request)
```

### 3.4 Swagger 文档注解

使用 OpenAPI 3.0 规范注解：

```java
@RestController
@RequestMapping("/admin/api/v1/agent")
@Tag(name = "智能员工接口", description = "智能员工管理相关接口")
@Slf4j
public class AgentController {

    @PostMapping("/create")
    @Operation(summary = "创建智能员工")
    public CommonResult<Long> createAgent(
            @RequestBody @Valid AgentCreateRequest request) {
        // ...
    }
}
```

---

## 4. 异常处理规范

### 4.1 三种标准异常

仅允许使用以下三种标准异常类型：

| 异常类型 | 使用场景 | 示例 |
|----------|----------|------|
| `MethodArgumentValidationException` | 参数校验失败 | 必填字段为空、格式错误 |
| `RemoteApiCallException` | 远程服务调用失败 | Feign 调用超时、返回错误 |
| `BusinessException` | 业务规则违反 | 用户不存在、余额不足 |

### 4.2 异常处理规范

由于有全局异常捕获（[GlobalExceptionHandler](file://d:/workspace/new_code/ai-coding/ai_coding/repos/mall-agent/src/main/java/com/aim/mall/agent/config/GlobalExceptionHandler.java)），Controller 层**不捕获任何异常**，直接抛出由全局处理器统一处理：

```java
@PostMapping("/create")
public CommonResult<Long> createAgent(
        @RequestBody @Valid AgentCreateRequest request) {
    Long agentId = agentService.createAgent(request);
    return CommonResult.success(agentId);
}
```

**说明**:
- 参数校验异常（`MethodArgumentValidationException`）→ 自动返回 400 错误
- 业务异常（`BusinessException`）→ 自动返回业务错误码
- 远程调用异常（`RemoteApiCallException`）→ 自动返回服务错误
- 其他未捕获异常 → 自动返回 500 系统错误

### 4.3 自定义异常

如需自定义异常，放在 `domain/exception` 目录下：

```java
public class AgentDomainException extends RuntimeException {
    private final String errorCode;

    public AgentDomainException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
```

---

## 5. 响应格式规范

### 5.1 标准响应对象

使用项目中已有的 `CommonResult<T>` 统一响应格式：

```java
// 成功响应
CommonResult.success(data)                    // 返回成功数据
CommonResult.success()                        // 返回成功无数据
CommonResult.success(data, "自定义消息")       // 返回成功数据和自定义消息

// 分页响应
CommonResult.pageSuccess(items, totalCount)   // 分页成功响应

// 失败响应
CommonResult.failed("错误信息")               // 通用失败
CommonResult.failed(errorCode)               // 使用错误码枚举
CommonResult.failed(errorCode, "错误信息")     // 错误码 + 自定义消息
```

### 5.2 响应规范示例

**普通列表查询**：

```java
@GetMapping("/list")
@Operation(summary = "查询智能员工列表")
public CommonResult<List<AgentResponse>> listAgents() {
    List<Agent> agents = agentService.listAll();
    List<AgentResponse> responses = agents.stream()
        .map(this::convertToResponse)
        .collect(Collectors.toList());
    return CommonResult.success(responses);
}
```

**分页查询**：

```java
@GetMapping("/page")
@Operation(summary = "分页查询智能员工")
public CommonResult<CommonResult.PageData<AgentResponse>> pageAgents(
        @RequestParam(defaultValue = "1") Integer current,
        @RequestParam(defaultValue = "10") Integer size) {

    Page<Agent> page = agentService.page(current, size);
    List<AgentResponse> responses = page.getRecords().stream()
        .map(this::convertToResponse)
        .collect(Collectors.toList());

    return CommonResult.pageSuccess(responses, page.getTotal());
}
```

**单个对象查询**：

```java
@GetMapping("/{id}")
@Operation(summary = "根据ID查询智能员工")
public CommonResult<AgentResponse> getAgentById(@PathVariable("id") Long id) {
    Agent agent = agentService.getById(id);
    if (agent == null) {
        return CommonResult.failed("AGENT_NOT_FOUND", "智能员工不存在");
    }
    return CommonResult.success(convertToResponse(agent));
}
```

---

## 6. 日志规范

### 6.1 日志级别使用

| 级别 | 使用场景 |
|------|----------|
| `ERROR` | 系统错误、异常、需要立即处理的问题 |
| `WARN` | 警告、潜在问题、非预期的业务情况 |
| `INFO` | 重要业务操作、状态变更 |
| `DEBUG` | 调试信息、详细的执行过程 |

### 6.2 日志内容规范

```java
// 记录入口参数
log.info("创建智能员工: {}", request);

// 记录关键步骤
log.info("调用用户服务查询用户信息, userId: {}", userId);

// 记录异常
log.error("创建智能员工失败, request: {}", request, e);

// 记录性能信息
log.info("查询智能员工列表完成, 耗时: {}ms, 结果数: {}", duration, count);
```

---

## 7. 检查清单

### 代码生成前检查

- [ ] 类名符合命名规范
- [ ] 包路径符合服务类型（门面/应用/支撑）
- [ ] 使用了正确的 Lombok 注解
- [ ] Request/Response 实现了 Serializable
- [ ] LocalDateTime 字段使用了 @JsonFormat

### 代码生成后检查

- [ ] 没有使用 BeanUtils.copyProperties
- [ ] 没有使用 QueryWrapper 或 SELECT *
- [ ] 批量操作使用了 batchInsert/batchUpdate
- [ ] 异常处理使用了三种标准异常
- [ ] 响应格式使用了 CommonResult
- [ ] 包含了必要的日志记录

---

## 相关文档

- **代码生成模板**：`.qoder/skills/java-code-generation.md`
- **架构规范**：`.qoder/rules/05-architecture-standards.md`
