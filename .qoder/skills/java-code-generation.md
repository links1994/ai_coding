---
name: java-code-generation
description: Java 微服务代码生成 Skill，根据技术规格书生成符合规范的 Java 代码
tools: Read, Write, Grep, Glob
---

# Java 代码生成 Skill

根据技术规格书生成符合项目规范的 Java 微服务代码。

> **注意**：本 Skill 使用的代码模板遵循以下规范：
> - 编码规范：`.qoder/rules/04-coding-standards.md`
> - 架构规范：`.qoder/rules/05-architecture-standards.md`

---

## 触发条件

- 技术规格书（design.md）已生成并确认
- 用户明确指令："生成代码" 或 "委托: 生成代码"
- 主 Agent 在 Phase 4 调用

---

## 输入

- `artifacts/spec/{program_id}/design.md` — 技术规格书
- `orchestrator/ALWAYS/RESOURCE-MAP.yml` — 项目资源映射
- `orchestrator/PROGRAMS/{program_id}/SCOPE.yml` — 写入范围控制
- `.qoder/rules/04-coding-standards.md` — 编码规范
- `.qoder/rules/05-architecture-standards.md` — 架构规范

---

## 输出

- 各服务的 Java 源代码 → `repos/{service}/src/main/java/`
- 代码生成报告 `artifacts/generated/{program_id}/reports/code-generation-report.md`

---

## 工作流程

### Step 1: 读取配置

1. 读取技术规格书（design.md）
2. 读取项目资源映射（RESOURCE-MAP.yml）
3. 读取写入范围（SCOPE.yml）
4. 读取编码规范和架构规范

### Step 2: 服务分析

对每个服务进行分析：
- 确定服务类型（门面服务/应用服务/支撑服务）
- 确定需要生成的代码层（Controller/Service/Mapper/Entity/DTO/Feign）
- 确定依赖关系（Feign 调用哪些服务）

### Step 3: 代码生成

按服务并行生成代码，遵循以下规范：

#### 包结构

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
│   ├── enums/           # 枚举类
│   ├── vo/              # 视图对象
│   └── exception/       # 异常类
├── api/                 # API 传输层
│   ├── dto/
│   │   ├── request/     # 请求对象
│   │   └── response/    # 响应对象
│   ├── enums/           # 远程调用相关枚举
│   └── feign/           # Feign 客户端
└── config/              # 配置类
```

#### 接口风格

| 服务类型 | 风格 | 路径前缀 |
|----------|------|----------|
| 门面服务 | RESTful | `/admin/api/v1/` 或 `/app/api/v1/` |
| 应用服务 | 简化 | `/inner/api/v1/` |

### Step 4: 依赖处理

- 先生成被调用方的 Feign 接口
- 再生成调用方的 Feign 客户端
- 确保接口定义一致性

### Step 5: 输出报告

生成 `artifacts/generated/{program_id}/reports/code-generation-report.md`：

```markdown
# 代码生成报告

## 生成摘要

| 服务 | 状态 | 代码文件数 | 主要类 |
|------|------|------------|--------|
| mall-user | 完成 | 12 | UserController, UserService, UserMapper |
| mall-agent | 完成 | 18 | AgentController, AgentService, AgentMapper |
| mall-admin | 完成 | 8 | AgentAdminController, AgentFeignClient |
| mall-app | 完成 | 8 | AgentClientController, AgentFeignClient |

## 依赖关系

- mall-admin → mall-agent (Feign)
- mall-app → mall-agent (Feign)
- mall-agent → mall-user (Feign)

## 问题记录

- 无

## 下一步

1. 代码审查
2. 单元测试
3. 集成测试
```

---

## 代码模板

代码模板统一维护在：`artifacts/generated/_TEMPLATE/java-code.md`

模板包含：
- **Controller 模板**：门面服务（管理端/客户端）+ 应用服务（内部接口）
- **Service 模板**：接口定义 + 实现类（含完整 CRUD）
- **Mapper 模板**：Java 接口 + XML 映射
- **DTO/Request/Response 模板**：Create/Update/VO
- **Feign Client 模板**：跨服务调用
- **Entity/DO 模板**：MyBatis-Plus 实体

---

## 返回格式

```
状态：已完成
报告：artifacts/generated/{program_id}/reports/code-generation-report.md
产出：N 个文件（列出各服务代码路径）
决策点：无
```
