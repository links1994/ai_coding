---
name: feature-retrieval
description: 功能检索 Skill，查询 repowiki 中已归档的功能，支持下次迭代时快速复用
tools: Read, Grep, Glob
---

# 功能检索 Skill

查询 repowiki 中已归档的功能档案，帮助开发者在下次迭代时快速找到可复用的功能。

---

## 触发条件

- 开始新功能开发前，需要查找类似功能时
- 需要了解某个功能的实现细节时
- 用户明确指令："查询功能" 或 "委托: 检索功能"
- 主 Agent 在 Phase 1（需求拆分）前调用

---

## 输入

- 查询关键词：`{keyword}` 或 `{feature-name}`
- `.qoder/repowiki/features/index.md` — 功能索引
- `.qoder/repowiki/features/{feature-id}/` — 功能归档目录

---

## 输出

- 检索结果报告：`.qoder/repowiki/features/retrieval-result.md`
- 匹配的功能列表及相关信息

---

## 检索类型

### 类型 1: 关键词检索

根据关键词模糊匹配功能名称和描述。

```
输入: "创建"
输出:
  - F-001-create-agent: 智能员工创建
  - F-003-create-order: 订单创建
  - F-005-create-user: 用户创建
```

### 类型 2: 服务检索

根据服务名检索该服务下的所有功能。

```
输入: "mall-agent"
输出:
  - F-001-create-agent: 智能员工创建
  - F-002-update-agent: 智能员工更新
  - F-004-agent-dialogue: 智能员工对话
```

### 类型 3: 精确检索

根据功能 ID 精确检索单个功能。

```
输入: "F-001"
输出:
  - 完整的功能档案内容
```

---

## 工作流程

### Step 1: 解析查询意图

确定检索类型：
- 如果是模糊关键词 → 关键词检索
- 如果是服务名（mall-*）→ 服务检索
- 如果是 F-XXX 格式 → 精确检索

### Step 2: 读取功能索引

读取 `.qoder/repowiki/features/index.md` 获取所有归档功能列表。

### Step 3: 执行检索

#### 关键词检索

```
1. 遍历 index.md 中的所有功能
2. 匹配功能名称、描述、标签
3. 按相关度排序
4. 返回前 N 个结果
```

#### 服务检索

```
1. 筛选 index.md 中指定服务的功能
2. 按归档日期排序
3. 返回功能列表
```

#### 精确检索

```
1. 根据功能 ID 找到对应目录
2. 读取 {feature-id}-archive.md
3. 返回完整档案内容
```

### Step 4: 生成检索报告

```markdown
# 功能检索结果

## 查询信息

- 查询类型: {retrieval-type}
- 查询关键词: {keywords}
- 检索时间: {timestamp}

## 匹配结果

### 结果 1: F-001-create-agent

| 属性 | 值 |
|------|-----|
| 功能ID | F-001 |
| 功能名称 | 智能员工创建 |
| 所属服务 | mall-agent |
| 归档日期 | 2026-02-25 |
| 相关度 | 95% |

**功能描述**: 
{功能描述摘要}

**核心接口**:
- POST /admin/api/v1/agent/create
- POST /inner/api/v1/agent/create

**复用建议**:
{基于 reuse-guide.md 的摘要}

**档案路径**: `.qoder/repowiki/features/F-001-create-agent/`

---

### 结果 2: F-003-create-order

...

## 检索总结

- 共找到 {count} 个匹配功能
- 建议优先查看: F-001-create-agent（相关度最高）
- 下一步: 阅读完整档案或调用代码生成 Skill
```

---

## 使用场景

### 场景 1: 新功能开发前检索

```
用户: "我要实现一个创建商品的功能，有类似的吗？"
主 Agent:
  → 调用 feature-retrieval Skill
  → 关键词: "创建"
  → 返回 F-001-create-agent, F-003-create-order 等
  → 建议参考 F-001（相关度最高）
```

### 场景 2: 了解特定功能

```
用户: "F-002 功能是怎么实现的？"
主 Agent:
  → 调用 feature-retrieval Skill
  → 精确检索: F-002
  → 返回完整功能档案
```

### 场景 3: 查看服务下所有功能

```
用户: "mall-chat 服务有哪些功能？"
主 Agent:
  → 调用 feature-retrieval Skill
  → 服务检索: mall-chat
  → 返回该服务下所有归档功能
```

---

## 与代码生成的协作

```
用户: "基于 F-001 创建一个类似的订单功能"
主 Agent:
  → 调用 feature-retrieval Skill
  → 读取 F-001 档案
  → 理解功能结构和设计决策
  → 调用 java-code-generation Skill
  → 生成订单功能代码（参考 F-001 结构）
```

---

## 返回格式

```
状态：已完成
报告：.qoder/repowiki/content/features/retrieval-result.md
产出：{count} 个匹配功能
决策点：需要查看完整档案吗？
```

---

## 相关文档

- **功能归档 Skill**: `.qoder/skills/feature-archiving.md`
- **功能归档目录**: `.qoder/repowiki/features/`
- **快速指引**: `.qoder/repowiki/features/README-features.md`
- **代码生成 Skill**: `.qoder/skills/java-code-generation.md`
