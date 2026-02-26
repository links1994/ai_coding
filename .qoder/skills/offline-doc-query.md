---
name: offline-doc-query
description: 离线文档查询 Skill，查询本地指定目录的文档资源
tools: Read, Grep, Glob
---

# 离线文档查询 Skill

查询本地指定目录的文档资源，支持框架文档、API 文档、技术规范等的离线检索。

---

## 触发条件

- 网络无法连接，需要查询技术文档时
- 用户明确指令："查询本地文档" 或 "委托: 离线查询文档"
- 主 Agent 在需要参考外部文档但网络不可用时调用

---

## 输入

- 查询关键词：`{keyword}` 或 `{doc-name}`
- 文档类型（可选）：`framework` | `api` | `spec` | `guide`
- `.qoder/repowiki/docs/` — 本地文档目录
- `orchestrator/ALWAYS/RESOURCE-MAP.yml` — 文档资源映射

---

## 输出

- 查询结果：`artifacts/generated/{program_id}/reports/offline-doc-query-result.md`
- 相关文档片段：提取的匹配内容

---

## 支持的文档类型

### 1. 框架文档 (framework)

Spring Boot、MyBatis-Plus、RocketMQ 等框架的官方文档离线版。

```
目录: .qoder/repowiki/docs/frameworks/
├── spring-boot/
│   ├── reference.pdf
│   └── api/
├── mybatis-plus/
│   ├── guide.md
│   └── api/
└── rocketmq/
    └── docs/
```

### 2. API 文档 (api)

第三方 SDK 和内部服务的 API 文档。

```
目录: .qoder/repowiki/docs/apis/
├── aliyun/
│   ├── nlp-api.md
│   └── oss-api.md
├── internal/
│   ├── user-service-api.md
│   └── agent-service-api.md
└── third-party/
    └── payment-api.md
```

### 3. 技术规范 (spec)

编码规范、架构规范、设计模式等技术文档。

```
目录: .qoder/repowiki/docs/specs/
├── java-coding-standard.pdf
├── restful-api-design.md
├── microservice-patterns.md
└── ddd-practice-guide.md
```

### 4. 操作指南 (guide)

部署手册、运维指南、故障排查等操作文档。

```
目录: .qoder/repowiki/docs/guides/
├── deployment/
│   ├── docker-deploy.md
│   └── k8s-deploy.md
├── operations/
│   ├── monitoring.md
│   └── backup-restore.md
└── troubleshooting/
    └── common-issues.md
```

---

## 工作流程

### Step 1: 确定查询范围

根据输入确定查询范围：

```
输入: "mybatis-plus 分页查询"
分析:
  - 关键词: mybatis-plus, 分页查询
  - 文档类型: framework
  - 搜索范围: .qoder/repowiki/docs/frameworks/mybatis-plus/
```

### Step 2: 检索本地文档

```
1. 检查文档目录是否存在
2. 根据关键词匹配文件名和内容
3. 按相关性排序结果
```

### Step 3: 提取相关内容

从匹配的文档中提取最相关的片段：

```markdown
## 匹配结果 1

**来源**: docs/frameworks/mybatis-plus/guide.md
**相关度**: 95%
**片段**:

```java
// 分页查询示例
Page<Agent> page = new Page<>(current, size);
agentMapper.selectPage(page, queryWrapper);
```

## 匹配结果 2

**来源**: docs/frameworks/mybatis-plus/api/IPage.html
**相关度**: 80%
**片段**:

IPage 接口定义了分页查询的基本方法...
```

### Step 4: 生成查询报告

```markdown
# 离线文档查询结果

## 查询信息

- 查询关键词: {keywords}
- 文档类型: {doc-type}
- 查询时间: {timestamp}
- 网络状态: 离线

## 匹配结果

{提取的文档片段}

## 相关文档

| 文档 | 路径 | 相关度 |
|------|------|--------|
| {doc-1} | {path-1} | {score-1} |
| {doc-2} | {path-2} | {score-2} |

## 建议

- {suggestion-1}
- {suggestion-2}
```

---

## 文档索引机制

为提高查询效率，建立文档索引：

```yaml
# .qoder/repowiki/docs/index.yml
documents:
  - id: mybatis-plus-guide
    name: MyBatis-Plus 指南
    path: frameworks/mybatis-plus/guide.md
    type: framework
    tags: [orm, mybatis, database]
    sections:
      - id: pagination
        title: 分页查询
        keywords: [page, pagination, 分页]
      - id: query-wrapper
        title: 条件构造器
        keywords: [query, wrapper, 查询]

  - id: spring-boot-reference
    name: Spring Boot 参考文档
    path: frameworks/spring-boot/reference.pdf
    type: framework
    tags: [spring, boot, framework]
```

索引更新：
```
委托: 更新文档索引
→ 扫描 .qoder/repowiki/docs/ 目录
→ 解析文档结构和关键词
→ 更新 .qoder/repowiki/docs/index.yml
```

---

## 使用场景

### 场景 1: 开发时查询文档

```
用户: "mybatis-plus 怎么用分页？"
主 Agent:
  → 调用 offline-doc-query Skill
  → 查询 .qoder/repowiki/docs/frameworks/mybatis-plus/
  → 返回分页查询示例代码
```

### 场景 2: 框架版本差异

```
用户: "Spring Boot 2.x 和 3.x 的配置区别"
主 Agent:
  → 调用 offline-doc-query Skill
  → 查询 docs/frameworks/spring-boot/
  → 返回版本迁移指南
```

### 场景 3: API 参考

```
用户: "用户服务有哪些 Feign 接口？"
主 Agent:
  → 调用 offline-doc-query Skill
  → 查询 .qoder/repowiki/docs/apis/internal/user-service-api.md
  → 返回接口列表和定义
```

---

## 文档同步策略

当网络可用时，支持文档同步：

```
委托: 同步文档
→ 检查 docs/sync-config.yml
→ 下载最新文档到本地
→ 更新文档索引
→ 记录同步时间
```

同步配置示例：

```yaml
# .qoder/repowiki/docs/sync-config.yml
sources:
  - name: mybatis-plus-docs
    url: https://baomidou.com/guide/
    local_path: frameworks/mybatis-plus/
    sync_interval: 7d
    
  - name: spring-boot-docs
    url: https://docs.spring.io/spring-boot/docs/current/reference/html/
    local_path: frameworks/spring-boot/
    sync_interval: 30d
```

---

## 返回格式

```
状态：已完成
报告：artifacts/generated/{program_id}/reports/offline-doc-query-result.md
产出：匹配的文档片段和参考信息
决策点：需要同步最新文档吗？（网络可用时）
```

---

## 相关文档

- **依赖查询 Skill**: `.qoder/skills/dependency-query.md`
- **功能归档 Skill**: `.qoder/skills/feature-archiving.md`
