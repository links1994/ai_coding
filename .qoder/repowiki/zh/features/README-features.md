# README-features

功能归档快速指引 - 帮助开发者快速理解和使用功能归档系统。

---

## 功能归档定义

功能归档是将已完成的业务功能进行结构化保存，形成可复用的知识库。每个归档包含：

- **功能档案**：完整的功能描述、接口、类、数据库设计
- **复用指南**：如何基于该功能进行复用或扩展
- **代码片段**：可复用的代码片段

---

## 目录结构

```
.qoder/repowiki/features/
├── README-features.md     # 本文件（快速指引）
├── index.md               # 功能索引（所有归档列表）
└── {feature-id}/          # 单个功能归档
    ├── {feature-id}-archive.md   # 功能档案
    ├── reuse-guide.md            # 复用指南
    └── snippets/                 # 代码片段
        ├── controller-snippet.java
        └── service-snippet.java
```

---

## 快速开始

### 1. 查看所有归档功能

打开 [`index.md`](index.md) 查看所有已归档的功能列表。

### 2. 查看单个功能详情

进入对应功能目录，阅读 `-archive.md` 文件：

```
.qoder/repowiki/features/F-001-create-agent/F-001-create-agent-archive.md
```

### 3. 基于归档功能复用

阅读功能目录下的 `reuse-guide.md`：

```
.qoder/repowiki/features/F-001-create-agent/reuse-guide.md
```

---

## 使用场景

### 场景 1：新功能开发前参考

**问题**：要实现一个类似"创建智能员工"的功能

**操作**：
1. 查看 `index.md` 找到类似功能（如 F-001-create-agent）
2. 阅读 `F-001-create-agent-archive.md` 了解实现细节
3. 参考 `reuse-guide.md` 进行复用

### 场景 2：代码审查时参考

**问题**：审查某个功能的代码实现

**操作**：
1. 根据功能 ID 找到对应归档
2. 对比归档中的设计决策和实际代码
3. 检查是否遵循了原始设计

### 场景 3：故障排查时参考

**问题**：某个功能出现异常

**操作**：
1. 找到该功能的归档
2. 查看"使用约束"和"问题解答"章节
3. 参考设计决策理解业务逻辑

---

## 归档内容说明

### 功能档案（{feature-id}-archive.md）

包含以下章节：

| 章节 | 内容 | 用途 |
|------|------|------|
| 基本信息 | ID、名称、服务、版本 | 快速识别功能 |
| 功能描述 | 业务背景、目标、用户故事 | 理解业务价值 |
| 接口清单 | REST API、Feign、MQ | 了解对外接口 |
| 核心类 | Controller、Service、Mapper | 代码结构参考 |
| 数据库变更 | 表、索引 | 数据模型参考 |
| 依赖关系 | 服务调用关系 | 架构理解 |
| 关键设计决策 | 技术选型理由 | 设计思路参考 |
| 测试用例 | 测试场景 | 测试覆盖参考 |
| 使用约束 | 限制、已知问题 | 规避方案 |

### 复用指南（reuse-guide.md）

包含：

- **快速复用场景**：完整复用、部分组件复用
- **修改建议**：常见修改点
- **问题解答**：Q&A

---

## 新增归档流程

当新功能开发完成后，使用 `feature-archiving` Skill 进行归档：

```
用户: "归档功能 F-003"
Agent:
  → 调用 feature-archiving Skill
  → 读取技术规格书和代码
  → 生成 F-003-archive.md
  → 更新 index.md
  → 生成 reuse-guide.md
```

---

## 索引说明

### index.md 格式

```markdown
| 功能ID | 功能名称 | 服务 | 归档日期 | 状态 |
|--------|----------|------|----------|------|
| F-001 | 智能员工创建 | mall-agent | 2026-02-25 | 可用 |
| F-002 | 智能员工对话 | mall-chat | 2026-02-26 | 可用 |
```

### 状态说明

| 状态 | 含义 |
|------|------|
| 可用 | 功能已上线，可以复用 |
| 废弃 | 功能已下线，不建议复用 |
| 更新中 | 功能正在迭代，谨慎复用 |

---

## 最佳实践

1. **开发前**：先查看是否有类似功能可复用
2. **开发中**：参考已有功能的设计决策
3. **开发后**：及时归档，完善复用指南
4. **迭代时**：更新归档，记录变更历史

---

## 相关文档

- **功能归档 Skill**：`.qoder/skills/feature-archiving.md`
- **功能检索 Skill**：`.qoder/skills/feature-retrieval.md`
- **代码生成 Skill**：`.qoder/skills/java-code-generation.md`
