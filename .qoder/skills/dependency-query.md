---
name: dependency-query
description: 依赖查询 Skill，按需加载模块依赖信息和外部 jar 包引用，避免一次性加载大量上下文
tools: Read, Grep, Glob
---

# 依赖查询 Skill

按需查询项目中的模块依赖关系和外部 jar 包信息，支持在开发过程中动态获取依赖上下文，避免一次性加载全部内容。

---

## 触发条件

- 需要了解某个模块的依赖关系时
- 遇到未实现模块或缺失 jar 包引用时
- 用户明确指令："查询依赖" 或 "委托: 查询依赖"
- 主 Agent 在代码生成或调试过程中调用

---

## 输入

- 查询目标：`{module-name}` 或 `{jar-name}` 或 `{class-name}`
- `pom.xml` — 项目依赖配置
- `orchestrator/ALWAYS/RESOURCE-MAP.yml` — 项目资源映射
- `repos/{service}/pom.xml` — 服务级依赖配置

---

## 输出

- 依赖信息报告：`artifacts/generated/{program_id}/reports/dependency-query-report.md`
- 依赖关系图：`artifacts/generated/{program_id}/reports/dependency-graph.md`（可选）

---

## 查询类型

### 类型 1: 模块依赖查询

查询指定模块依赖哪些其他模块，以及被哪些模块依赖。

```
输入: mall-agent
输出:
  - 直接依赖: mall-common, mall-user
  - 被依赖: mall-admin, mall-app
  - 接口调用: UserFeignService, AgentFeignService
```

### 类型 2: Jar 包查询

查询指定 jar 包的版本、用途和引用位置。

```
输入: mybatis-plus
输出:
  - 版本: 3.5.5
  - 用途: ORM 框架
  - 引用服务: mall-agent, mall-user, mall-admin, mall-app
  - 关键配置: MyBatisPlusConfig.java
```

### 类型 3: 类依赖查询

查询指定类的依赖关系和使用位置。

```
输入: AgentService
输出:
  - 所在模块: mall-agent
  - 依赖类: AgentMapper, UserFeignService, AgentDomainService
  - 被调用: AgentController, AgentAdminController
  - 接口定义: AgentService (interface)
  - 实现类: AgentServiceImpl
```

---

## 工作流程

### Step 1: 解析查询目标

确定查询类型：
- 如果是服务名（mall-*）→ 模块依赖查询
- 如果是 artifactId（mybatis-plus, lombok）→ Jar 包查询
- 如果是类名（*Service, *Controller）→ 类依赖查询

### Step 2: 读取依赖配置

```
1. 读取根目录 pom.xml（获取 parent 依赖）
2. 读取目标模块的 pom.xml（获取模块依赖）
3. 解析 dependencyManagement 和 dependencies
```

### Step 3: 分析依赖关系

#### 模块依赖分析

```yaml
module: mall-agent
dependencies:
  internal:           # 内部模块依赖
    - mall-common
    - mall-user
  external:           # 外部 jar 依赖
    - spring-boot-starter-web
    - mybatis-plus-boot-starter
    - rocketmq-spring-boot-starter
  feign_clients:      # Feign 客户端
    - UserFeignService
  
dependents:           # 被哪些模块依赖
  - mall-admin
  - mall-app
```

#### Jar 包分析

```yaml
artifact: mybatis-plus-boot-starter
version: 3.5.5
groupId: com.baomidou
usage:
  - service: mall-agent
    config: MyBatisPlusConfig.java
    mappers: [AgentMapper, AgentSkillMapper]
  - service: mall-user
    config: MyBatisPlusConfig.java
    mappers: [UserMapper]
key_features:
  - 分页插件
  - 自动填充
  - 逻辑删除
```

#### 类依赖分析

```yaml
class: AgentServiceImpl
package: com.aim.mall.agent.service.impl
implements: AgentService
autowired:
  - AgentMapper
  - UserFeignService
  - AgentDomainService
called_by:
  - AgentController.createAgent()
  - AgentAdminController.createAgent()
  - AgentInnerController.createAgent()
```

### Step 4: 生成查询报告

```markdown
# 依赖查询报告

## 查询目标

- 类型: {query-type}
- 目标: {target}
- 查询时间: {timestamp}

## 查询结果

{根据查询类型展示结果}

## 建议

- {suggestion-1}
- {suggestion-2}
```

---

## 缓存机制

为提高查询效率，支持依赖信息缓存：

```
artifacts/generated/{program_id}/.cache/
├── dependencies/
│   ├── module-deps-cache.json      # 模块依赖缓存
│   ├── jar-info-cache.json         # Jar 包信息缓存
│   └── class-deps-cache.json       # 类依赖缓存
└── cache-metadata.yml              # 缓存元数据（更新时间）
```

缓存策略：
- pom.xml 修改时间变化时刷新缓存
- 手动触发刷新：`委托: 刷新依赖缓存`

---

## 使用场景

### 场景 1: 开发时查询依赖

```
用户: "AgentService 依赖哪些类？"
主 Agent:
  → 调用 dependency-query Skill
  → 类型: 类依赖查询
  → 目标: AgentService
  → 返回依赖关系图
```

### 场景 2: 解决缺失依赖

```
用户: "编译报错，找不到 UserFeignService"
主 Agent:
  → 调用 dependency-query Skill
  → 类型: 类依赖查询
  → 目标: UserFeignService
  → 发现: 位于 mall-user 模块
  → 建议: 检查 mall-agent 的 pom.xml 是否依赖 mall-user
```

### 场景 3: 版本冲突排查

```
用户: "mybatis-plus 版本冲突"
主 Agent:
  → 调用 dependency-query Skill
  → 类型: Jar 包查询
  → 目标: mybatis-plus
  → 返回各模块使用的版本
  → 建议: 统一在 parent pom 中管理版本
```

---

## 返回格式

```
状态：已完成
报告：artifacts/generated/{program_id}/reports/dependency-query-report.md
产出：依赖关系信息
决策点：需要更新依赖配置吗？
```

---

## 相关文档

- **功能归档 Skill**: `.qoder/skills/feature-archiving.md`
- **离线文档查询 Skill**: `.qoder/skills/offline-doc-query.md`
