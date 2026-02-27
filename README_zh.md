# AI 编码工作区指南

一份帮助理解和使用此 AI 驱动编码工作区的综合指南。本文档聚焦于 **AI 编码执行流程**，而非具体的项目功能实现。

---

## 目录

1. [工作区概述](#1-工作区概述)
2. [目录结构](#2-目录结构)
3. [核心概念](#3-核心概念)
4. [快速入门](#4-快速入门)
5. [开发工作流](#5-开发工作流)
6. [各组件使用方法](#6-各组件使用方法)
7. [架构与编码规范](#7-架构与编码规范)
8. [常用命令](#8-常用命令)
9. [故障排查](#9-故障排查)

---

## 1. 工作区概述

这是一个 **AI 驱动的编码工作区**，旨在使用 AI 辅助来标准化和简化软件开发流程。它提供了一个结构化框架，用于：

- 需求拆分与分析
- 技术规格书生成
- 代码生成与质量保证
- 项目组织与跟踪

### 1.1 核心原则

| 原则 | 描述 |
|-----------|-------------|
| **基于 Program** | 所有工作都组织成离散的 "Program"（开发任务） |
| **规格驱动** | 开发遵循 6 阶段规格工作流 |
| **范围控制** | 每个 Program 明确定义写入权限 |
| **版本控制** | 基于 Git 的工作流，支持 worktree |

### 1.2 技术栈

| 层级 | 技术 |
|-------|------------|
| 语言 | Java 21 |
| 框架 | Spring Cloud（微服务） |
| 数据库 | MySQL |
| 缓存 | Redis |
| API 文档 | OpenAPI 3.0（Swagger） |

---

## 2. 目录结构

```
ai_coding/
├── AGENTS.md                          # Agent 配置指南
├── orchestrator/                      # 核心编排层
│   ├── ALWAYS/                        # 核心配置（每次必读）
│   │   ├── BOOT.md                    # 启动顺序
│   │   ├── CORE.md                    # 核心工作协议
│   │   ├── DEV-FLOW.md                # 开发流程规范
│   │   ├── SUB-AGENT.md               # 子代理规范
│   │   └── RESOURCE-MAP.yml           # 资源索引
│   └── PROGRAMS/                      # 开发任务
│       ├── _TEMPLATE/                 # Program 模板
│       └── P-YYYY-NNN-name/           # 具体 Program
│
├── .qoder/                            # Qoder AI 配置
│   ├── rules/                         # 编码和架构规范
│   │   ├── 01-prd-decomposition.md    # 阶段 1：需求拆分
│   │   ├── 02-requirement-clarification.md  # 阶段 2：需求澄清
│   │   ├── 03-tech-spec-generation.md # 阶段 3：技术规格书
│   │   ├── 04-coding-standards.md     # Java 编码规范
│   │   └── 05-architecture-standards.md # 架构规范
│   └── skills/                        # 可复用技能模块
│       ├── java-code-generation.md    # Java 代码生成
│       ├── http-test-generation.md    # HTTP 测试生成
│       ├── code-quality-analysis.md   # 代码质量分析
│       ├── feature-archiving.md       # 功能归档
│       ├── knowledge-base-query.md    # 知识库查询
│       └── dependency-query.md        # 依赖查询
│
├── inputs/                            # 输入文档目录
│   └── prd/                           # 产品需求文档（PRD）
│
├── repos/                             # 代码仓库目录
│
└── .qoder/repowiki/                   # 知识库存储
    └── feature/                       # 功能归档
        └── _TEMPLATE/                 # 功能归档模板
```

### 2.1 目录用途

| 目录 | 用途 | 说明 |
|-----------|---------|-------|
| `orchestrator/` | 所有开发活动的中央指挥中心 | 包含工作流定义、Program 管理和核心协议 |
| `.qoder/` | AI 配置和能力 | 包含规则（规范）和技能（可复用能力） |
| `inputs/` | 输入文档存储 | 存放 PRD 和其他输入文档 |
| `repos/` | 代码仓库存储 | 包含实际的项目代码仓库 |
| `.qoder/repowiki/` | 知识库存储 | 包含功能归档和可复用模板 |

---

## 3. 核心概念

### 3.1 什么是 "Program"？

**Program** 是此工作区中开发工作的基本单元。每个 Program 对应一个具体的功能开发或技术任务。

**Program ID 格式**：`P-YYYY-NNN-{name}`
- `P`：Program 缩写
- `YYYY`：年份（如 2026）
- `NNN`：序号（001-999）
- `name`：功能名称（小写，连字符分隔）

**示例**：`P-2026-001-user-authentication`

### 3.2 规格模式（Spec Mode）

当您输入 PRD（产品需求文档）时，系统自动进入 **规格模式**，包含 6 个阶段：

```
PRD → 阶段 1：需求拆分
    → 阶段 2：需求澄清
    → 阶段 3：技术规格书
    → 阶段 4：代码生成
    → 阶段 5：HTTP 测试生成
    → 阶段 6：代码质量优化
```

| 阶段 | 输入 | 输出 | 描述 |
|-------|-------|--------|-------------|
| 阶段 1 | PRD 文档 | `decomposition.md`, `dependencies.md`, `assignment.md` | 拆分需求并分析依赖 |
| 阶段 2 | 拆分结果 | `questions.md`, `answers.md`, `decisions.md` | 与用户澄清需求 |
| 阶段 3 | 确认的需求 | `design.md`, `openapi.yaml`, `checklist.md` | 生成技术规格书 |
| 阶段 4 | 技术规格书 | Java 代码文件 | 使用 Skill 生成代码 |
| 阶段 5 | Controller 代码 | `.http` 测试文件 | 生成 HTTP 测试 |
| 阶段 6 | 生成的代码 | 质量报告 | 分析和优化代码质量 |
| 阶段 7 | 已完成的代码 | 功能归档 | 归档功能供将来复用 |

### 3.3 三层服务架构

工作区支持标准化的微服务架构：

```
前端应用
      ↓
┌─────────────┐
│  API 网关    │  （基础设施）
└─────────────┘
      ↓
┌─────────────┬─────────────┬─────────────┐
│   门面服务    │   门面服务    │   门面服务    │  ← 门面层
│  (管理端)    │  (客户端)    │   (其他)     │
└─────────────┴─────────────┴─────────────┘
      ↓              ↓            ↓
      └──────────┬─────────┘      │
                 ↓                │
          ┌─────────────┐         │
          │   应用服务    │  ← 应用层（核心业务）
          └─────────────┘         │
                 ↓                │
          ┌─────────────┐         │
          │   支撑服务    │  ← 支撑层
          └─────────────┘         │
                 ↑                │
                 └────────────────┘
```

**服务调用规则**：

| 调用方 | 可调用 | 禁止调用 |
|--------|----------|-------------|
| 门面层 | 应用层、支撑层 | - |
| 应用层 | 支撑层 | 门面层 |
| 支撑层 | - | 门面层、应用层 |

**所有跨服务调用必须使用 OpenFeign。**

---

## 4. 快速入门

### 4.1 快速命令

| 命令 | 操作 |
|---------|--------|
| `继续 P-YYYY-NNN` | 加载并继续指定 Program |
| `新 Program: xxx` | 创建新的开发任务 |
| `委托: xxx` | 委托任务给子代理 |

### 4.2 创建新 Program

#### 方式一：从模板创建

```bash
# 复制模板目录
cp -r orchestrator/PROGRAMS/_TEMPLATE orchestrator/PROGRAMS/P-2026-001-feature-name

# 然后编辑：
# 1. PROGRAM.md - 填写任务定义
# 2. SCOPE.yml - 设置写入范围
# 3. STATUS.yml - 初始化状态
```

#### 方式二：通过 Agent 创建

```
用户："新 Program: 实现用户登录功能"
Agent：
  → 从模板创建 P-2026-001-user-login/
  → 生成初始 PROGRAM.md
  → 询问写入范围
  → 初始化 STATUS.yml
```

### 4.3 Program 结构

```
P-YYYY-NNN-{name}/
├── PROGRAM.md              # 任务定义
├── STATUS.yml              # 状态跟踪
├── SCOPE.yml               # 写入范围控制
└── workspace/              # 工作文档
    ├── decomposition.md    # 阶段 1：需求拆分
    ├── dependencies.md     # 依赖关系图
    ├── questions.md        # 阶段 2：澄清问题
    ├── answers.md          # 确认的答案
    ├── decisions.md        # 技术决策
    ├── CHECKPOINT.md       # 上下文快照
    ├── HANDOFF.md          # 交接文档
    └── RESULT.md           # 最终成果
```

### 4.4 关键文件说明

#### PROGRAM.md

任务定义文件，包含：
- **目标**：一句话描述要实现什么
- **背景**：为什么要做这个任务
- **方案**：技术方案概述
- **涉及文件**：需要修改的主要文件
- **验收标准**：完成条件

#### STATUS.yml

状态跟踪文件：

```yaml
program: P-YYYY-NNN
name: 任务名称
phase: in-progress      # planning / in-progress / review / done
status: active          # not-started / active / blocked / deployed

tasks:
  - id: t1
    name: 任务 1
    status: done        # pending / in-progress / done / blocked
  - id: t2
    name: 任务 2
    status: in-progress
```

#### SCOPE.yml

写入范围控制文件：

```yaml
write:
  - orchestrator/PROGRAMS/P-YYYY-NNN-{name}/workspace/
  - repos/<repo>/src/main/java/com/...

forbidden:
  - repos/<repo>/.env
  - repos/<repo>/src/main/resources/application-prod.yml
```

---

## 5. 开发工作流

### 5.1 Git 工作流

```
确认任务 → Worktree 开发 → 测试 → PR → 合并到 Main → 部署
```

### 5.2 完整开发周期

#### 步骤 1：确认任务

从 Program 的 `PROGRAM.md` 和 `STATUS.yml` 读取确认当前工作。

#### 步骤 2：创建 Worktree

```bash
cd repos/<repo>
git fetch origin
git worktree add ../repos/<repo>-<feature> -b feature/<name> main
cd ../repos/<repo>-<feature>

# 安装依赖
mvn clean install
```

#### 步骤 3：开发

在 worktree 中开发，提交代码：

```bash
# 检查代码质量
mvn checkstyle:check

# 运行测试
mvn test

# 提交
git add . && git commit -m "feat: xxx"
git push -u origin feature/<name>
```

开发过程中更新 `STATUS.yml` 跟踪进度。

#### 步骤 4：提交 PR

```bash
cd repos/<repo>-<feature>
gh pr create --base main --head feature/<name>
```

#### 步骤 5：合并

```bash
cd repos/<repo>
gh pr merge <PR 编号> --merge --delete-branch
```

#### 步骤 6：清理 Worktree

```bash
cd repos/<repo>
git worktree remove ../repos/<repo>-<feature>
git branch -D feature/<name>
```

#### 步骤 7：更新 Program 状态

更新 `STATUS.yml`，如果 Program 完成则写 `workspace/RESULT.md`。

### 5.3 分支命名

| 分支 | 用途 |
|--------|---------|
| `main` | 稳定版本 |
| `dev` | 测试集成（可选） |
| `feature/*` | 功能开发 |
| `fix/*` | Bug 修复 |

### 5.4 提交规范

格式：`<type>: <description>`

| Type | 描述 |
|------|-------------|
| `feat` | 新功能 |
| `fix` | Bug 修复 |
| `docs` | 文档 |
| `refactor` | 重构 |
| `test` | 测试 |
| `chore` | 杂项 |

---

## 6. 各组件使用方法

### 6.1 使用 `orchestrator/`

编排器是工作区的指挥中心。

#### ALWAYS/ 目录

此目录中的文件在 Agent **每次启动时**都会读取：

| 文件 | 何时阅读 | 用途 |
|------|--------------|---------|
| `BOOT.md` | 首次 | 了解启动顺序 |
| `CORE.md` | 每次 | 核心工作协议和规格模式阶段 |
| `DEV-FLOW.md` | 每次 | 开发工作流和 Git 命令 |
| `SUB-AGENT.md` | 委托时 | 如何使用子代理 |
| `RESOURCE-MAP.yml` | 每次 | 服务定义和依赖 |

#### PROGRAMS/ 目录

包含所有开发任务：

```
PROGRAMS/
├── _TEMPLATE/           # 新 Program 模板
│   ├── PROGRAM.md
│   ├── STATUS.yml
│   ├── SCOPE.yml
│   └── workspace/
└── P-YYYY-NNN-name/     # 实际的 Program
```

**使用方法**：
1. 复制 `_TEMPLATE/` 创建新 Program
2. 编辑 `PROGRAM.md` 定义任务
3. 编辑 `SCOPE.yml` 设置写入权限
4. 编辑 `STATUS.yml` 跟踪进度
5. 在 `workspace/` 目录中工作

### 6.2 使用 `.qoder/rules/`

规则定义开发的**标准和约束**。

| 规则文件 | 阶段 | 用途 |
|-----------|-------|---------|
| `01-prd-decomposition.md` | 阶段 1 | 如何将 PRD 拆分为需求 |
| `02-requirement-clarification.md` | 阶段 2 | 如何与用户澄清需求 |
| `03-tech-spec-generation.md` | 阶段 3 | 如何生成技术规格书 |
| `04-coding-standards.md` | 所有阶段 | Java 编码规范（命名、格式等） |
| `05-architecture-standards.md` | 所有阶段 | 架构规范（分层、服务、数据库） |

**使用方法**：
- Agent 在对应阶段自动读取这些文件
- 开发者应查看 `04-` 和 `05-` 了解编码指南
- 除非更新标准，否则不要修改这些文件

### 6.3 使用 `.qoder/skills/`

技能是执行特定任务的**可复用能力模块**。

| 技能 | 文件 | 用途 | 何时使用 |
|-------|------|---------|-------------|
| Java 代码生成 | `java-code-generation.md` | 生成 Java 微服务代码 | 阶段 4 |
| HTTP 测试生成 | `http-test-generation.md` | 生成 HTTP 测试文件 | 阶段 5 |
| 代码质量分析 | `code-quality-analysis.md` | 分析代码质量 | 阶段 6 |
| 功能归档 | `feature-archiving.md` | 归档已完成功能 | 功能完成后 |
| 知识库查询 | `knowledge-base-query.md` | 查询知识库（功能、规范、文档） | 需要时 |
| 依赖查询 | `dependency-query.md` | 查询模块依赖 | 需要时 |

**使用方法**：
```
用户："根据此规格书生成代码"
Agent：
  → 读取 `java-code-generation.md` 技能
  → 遵循技能中的工作流
  → 按规范生成代码
```

### 6.4 使用 `inputs/`

**输入文档**目录，主要用于存放 PRD。

**使用方法**：
1. 将 PRD 文件放入 `inputs/prd/`
2. 启动规格模式时引用：
   ```
   用户："基于 inputs/prd/my-feature-prd.md 开始开发"
   ```
3. Agent 读取 PRD 并自动进入阶段 1

**命名规范**：`{系统}-{功能}-prd.md`

### 6.5 使用 `repos/`

**代码仓库**目录。

**使用方法**：
1. 每个子目录是一个独立的 Git 仓库
2. 使用 Git worktree 进行功能开发（见 5.2 节）
3. 遵循 `RESOURCE-MAP.yml` 中定义的服务架构
4. 遵守 `SCOPE.yml` 中的写入权限

### 6.6 使用 `.qoder/repowiki/`

**知识库存储**目录。

**结构**：
```
.qoder/repowiki/
└── feature/                    # 功能归档
    ├── _TEMPLATE/              # 功能归档模板
    │   └── feature-archive.md
    └── {feature-name}/         # 已归档的功能
```

**使用方法**：
- 功能归档存储已完成的功能文档
- 参考这些用于类似的未来实现
- 模板提供标准化的文档格式

---

## 7. 架构与编码规范

### 7.1 四层架构（强制）

必须严格遵循：**接口层 → 应用层 → 领域层 → 基础设施层**

```
┌─────────────────────────────────────┐
│           接口层 (Controller)        │  ← 接收请求、参数校验
├─────────────────────────────────────┤
│           应用层 (Service)           │  ← 业务流程编排
├─────────────────────────────────────┤
│           领域层 (Domain)            │  ← 业务逻辑、领域模型
├─────────────────────────────────────┤
│         基础设施层 (Mapper/Repo)      │  ← 数据访问、外部调用
└─────────────────────────────────────┘
```

**约束**：
- 禁止跨层直接调用
- 高层模块不依赖低层模块的具体实现
- 遵循依赖倒置原则

### 7.2 服务类型

| 服务类型 | 职责 | 调用范围 |
|--------------|----------------|------------|
| **QueryService** | 只读、数据聚合 | 仅 Mapper 查询 |
| **ManageService** | 写操作、事务 | Services 或 Mapper 增删改 |
| **DomainService** | 复杂多实体逻辑 | 禁止直接调用 Mapper |

### 7.3 模块路径前缀

| 模块类型 | 路径前缀 | 描述 |
|-------------|-------------|-------------|
| 门面-管理端 | `/admin/api/v1/` | 供管理后台 |
| 门面-客户端 | `/app/api/v1/` | 供 APP/客户端 |
| 服务模块 | `/inner/api/v1/` | 供服务间 RPC |

### 7.4 接口风格规范

#### 门面服务

- **风格**：完整 RESTful（GET/POST/PUT/DELETE）
- **路径参数**：最多一个，在 URL 末尾
- **示例**：`GET /admin/api/v1/users/{id}`

#### 应用服务

- **风格**：简化（仅 GET/POST）
- **路径参数**：**禁止**
- **查询**：使用 Query 参数（GET）
- **操作**：使用 RequestBody（POST）
- **示例**：`POST /inner/api/v1/users/detail`

### 7.5 数据库规范

#### 通用字段（必需）

| 字段 | 类型 | 描述 |
|-------|------|-------------|
| `id` | BIGINT | 主键，自增 |
| `create_time` | DATETIME | 创建时间戳 |
| `update_time` | DATETIME | 自动更新时间戳 |
| `is_deleted` | TINYINT | 逻辑删除标记 |
| `creator_id` | BIGINT | 创建人 ID（可选） |
| `updater_id` | BIGINT | 更新人 ID（可选） |

#### 字符集

- **字符集**：`utf8mb4`
- **排序规则**：使用数据库默认（不指定）

### 7.6 Java 编码规范

#### 命名规范

| 类型 | 模式 | 示例 |
|------|---------|---------|
| 实体类 | 大驼峰 + DO | `UserDO` |
| DTO | 大驼峰 + DTO | `UserCreateDTO` |
| VO | 大驼峰 + VO | `UserVO` |
| Request | 大驼峰 + Request | `UserCreateRequest` |
| Response | 大驼峰 + Response | `UserResponse` |
| Service | 大驼峰 + Service | `UserService` |
| Mapper | 大驼峰 + Mapper | `UserMapper` |

#### 方法命名

| 操作 | 模式 | 示例 |
|-----------|---------|---------|
| 查询单个 | `getById`, `getByXXX` | `getById(Long id)` |
| 查询列表 | `listXXX` | `listByStatus(status)` |
| 分页 | `pageXXX` | `pageUsers(request)` |
| 创建 | `createXXX` | `createUser(request)` |
| 更新 | `updateXXX` | `updateUser(request)` |
| 删除 | `deleteXXX` | `deleteById(id)` |

#### Lombok 注解

| 注解 | 用于 |
|------------|---------|
| `@Data` | 实体类、DTO |
| `@Getter` | 只读对象 |
| `@Builder` | 复杂对象创建 |
| `@Slf4j` | 日志 |

#### 序列化

```java
@Data
public class UserRequest implements Serializable {
    private static final long serialVersionUID = -1L;
    // ...
}
```

#### 时间格式化

```java
@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
private LocalDateTime createdAt;
```

### 7.7 异常处理

仅使用这三种标准异常：

| 异常 | 使用场景 |
|-----------|----------|
| `MethodArgumentValidationException` | 参数校验失败 |
| `RemoteApiCallException` | 远程服务调用失败 |
| `BusinessException` | 业务规则违反 |

简化处理：
```java
try {
    // 业务逻辑
} catch (Exception e) {
    log.error("操作失败", e);
    return CommonResult.failed("ERROR_CODE", "消息");
}
```

### 7.8 响应格式

所有响应使用 `CommonResult<T>`：

```java
// 成功
CommonResult.success(data)
CommonResult.pageSuccess(items, total)

// 失败
CommonResult.failed("ERROR_CODE", "消息")
```

---

## 8. 常用命令

### 8.1 Git Worktree

```bash
# 创建 worktree
git worktree add ../repos/<repo>-<feature> -b feature/<name> main

# 移除 worktree
git worktree remove ../repos/<repo>-<feature>

# 列出 worktrees
git worktree list
```

### 8.2 GitHub CLI

```bash
# 创建 PR
gh pr create --base main --head feature/<name>

# 合并 PR
gh pr merge <PR 编号> --merge --delete-branch

# 查看状态
gh pr status
```

### 8.3 Maven

```bash
# 编译
mvn clean compile

# 测试
mvn test

# 打包
mvn clean package

# 跳过测试
mvn clean package -DskipTests

# 安装
mvn clean install
```

---

## 9. 故障排查

### 9.1 权限被拒绝

**原因**：文件不在 `SCOPE.yml` 写入列表中

**解决方案**：检查并更新 Program 目录中的 `SCOPE.yml`

### 9.2 更改未生效

**检查清单**：
1. 正确的 worktree？`git worktree list`
2. 正确的分支？`git branch`
3. 文件已跟踪？`git status`

### 9.3 Feign 调用失败

**检查清单**：
1. 目标服务正在运行？
2. Feign 配置正确？
3. URL 中没有路径参数（使用 Query/Body 代替）

### 9.4 获取帮助

1. 查看 `.qoder/rules/` 了解规范
2. 查看 `orchestrator/PROGRAMS/` 中的类似 Program
3. 查看各目录中的 `README-*.md` 文件
4. 向 Agent 提供具体错误信息寻求帮助

---

## 附录：文件快速参考

### 核心文档

| 文件 | 用途 |
|------|---------|
| `AGENTS.md` | Agent 配置 |
| `orchestrator/ALWAYS/BOOT.md` | 启动顺序 |
| `orchestrator/ALWAYS/CORE.md` | 核心协议 |
| `orchestrator/ALWAYS/DEV-FLOW.md` | 开发流程 |
| `orchestrator/ALWAYS/RESOURCE-MAP.yml` | 资源索引 |

### 规则

| 文件 | 用途 |
|------|---------|
| `01-prd-decomposition.md` | 阶段 1 规则 |
| `02-requirement-clarification.md` | 阶段 2 规则 |
| `03-tech-spec-generation.md` | 阶段 3 规则 |
| `04-coding-standards.md` | 编码规范 |
| `05-architecture-standards.md` | 架构规范 |

### 技能

| 文件 | 用途 |
|------|---------|
| `java-code-generation.md` | 代码生成 |
| `http-test-generation.md` | 测试生成 |
| `code-quality-analysis.md` | 质量分析 |
| `feature-archiving.md` | 功能归档 |
| `knowledge-base-query.md` | 知识库查询 |

---

*最后更新：2026年2月26日*
