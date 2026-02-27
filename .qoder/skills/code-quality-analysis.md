---
name: code-quality-analysis
description: 代码质量分析 Skill，对生成的代码进行质量分析和优化建议
tools: Read, Write, Grep, Glob
---

# 代码质量分析 Skill

对生成的代码进行全面的质量分析，识别问题并提供优化建议。

---

## 触发条件

- HTTP 测试生成完成
- 用户明确指令："分析代码质量" 或 "委托: 优化代码质量"
- 主 Agent 在 Phase 6 调用

---

## 输入

- 生成的 Java 源代码
- 项目架构规范（RESOURCE-MAP.yml）
- 编码规范（.qoder/rules/04-coding-standards.md）

---

## 输出

- 代码质量分析报告：`orchestrator/PROGRAMS/{program_id}/workspace/code-quality-report.md`
- 质量分析数据：`orchestrator/PROGRAMS/{program_id}/workspace/code-quality-analysis.yaml`

---

## 分析范围

### 1. 代码重复检测

- 识别 3 行以上的重复代码片段
- 检测相似逻辑的不同实现
- 发现可提取为公共方法的重复代码

### 2. 性能优化

- 识别 N+1 查询问题
- 检测不必要的循环和嵌套
- 发现可以缓存的数据查询
- 识别内存泄漏风险点

### 3. SQL 性能专项

- 识别在循环中执行单条 CRUD 操作
- 检测可优化的查询语句
- 发现可使用批量操作的场景

### 4. 设计模式应用

- 识别适合应用设计模式的场景
- 检查现有设计模式的正确性
- 发现违反设计原则的代码

### 5. DDD 原则检查

- 验证领域模型的纯净性
- 检查聚合根的正确性
- 识别跨领域边界的不当调用

---

## 质量评估标准

| 指标 | 目标值 | 检测方法 |
|------|--------|----------|
| 圈复杂度 | ≤ 10 | 分析每个方法的圈复杂度 |
| 方法长度 | ≤ 50 行 | 统计每个方法的代码行数 |
| 类职责单一性 | - | 分析类的方法数量和功能相关性 |
| 依赖关系合理性 | - | 分析类之间的依赖关系是否符合分层架构 |

---

## 报告格式

### YAML 格式报告

```yaml
code_quality_analysis:
  req_id: "REQ-{timestamp}-{uuid}"
  feature_id: feature_001
  feature_name: "功能点名称"

  issues:
    - issue_type: duplication
      severity: high
      location: "类名.方法名:行号"
      description: "重复代码描述"
      suggestion: "改进建议"

    - issue_type: performance
      severity: high
      location: "类名.方法名:行号"
      description: "性能问题描述"
      solution: "优化方案"

  quality_metrics:
    - metric_name: "圈复杂度"
      current_value: 15
      target_value: 10
      improvement_needed: true

  best_practices_checklist:
    - practice: "SOLID原则遵循"
      status: compliant
      violations: []
```

### Markdown 报告

```markdown
# 代码质量分析报告

## 概览

| 指标 | 当前值 | 目标值 | 状态 |
|------|--------|--------|------|
| 圈复杂度 | 15 | 10 | 需改进 |
| 方法平均长度 | 45 | 50 | 达标 |
| 重复代码率 | 5% | 5% | 达标 |

## 问题列表

### 高优先级

1. **重复代码** - AgentService.createAgent 和 updateAgent
   - 建议：提取公共方法

### 中优先级

2. **性能问题** - 循环中单条插入
   - 建议：使用批量插入

## 优化建议

...
```

---

## 检查清单

### 必需检查项

- [ ] 代码重复检测
- [ ] 性能问题识别
- [ ] SQL 性能专项检查
- [ ] 设计模式应用检查
- [ ] DDD 原则检查
- [ ] 圈复杂度评估
- [ ] 方法长度评估

### 报告要求

- [ ] 生成 YAML 格式报告
- [ ] 包含量化指标
- [ ] 提供优化建议
- [ ] 包含实施计划

---

## 返回格式

```
状态：已完成
报告：orchestrator/PROGRAMS/{program_id}/workspace/code-quality-analysis.yaml
产出：N 个优化建议（含代码修改）
决策点：需要人工确认高风险重构吗？
```
