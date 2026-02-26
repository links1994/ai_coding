# 核心工作协议

本文件定义 Agent 的核心工作流程和协议，不包含具体的编码规范。

**编码规范**请参见：`.qoder/rules/04-coding-standards.md`
**架构规范**请参见：`.qoder/rules/05-architecture-standards.md`

---

## Spec 模式（Quest Mode）

当用户输入产品 PRD 时，自动进入 Spec 模式，按以下六阶段执行：

```
PRD → Phase 1: 需求拆分 → Phase 2: 需求澄清 → Phase 3: 技术规格书 → Phase 4: 代码生成 → Phase 5: HTTP 测试生成 → Phase 6: 代码质量优化
```

### Phase 1: 需求拆分与依赖分析

- **输入**: PRD 文档
- **输出**: `artifacts/spec/{program_id}/requirements/`
  - `decomposition.md` — 需求拆分结果
  - `dependencies.md` — 依赖关系图
  - `assignment.md` — 服务归属判断
- **规则**: 参考 `.qoder/rules/01-prd-decomposition.md`

### Phase 2: 需求澄清与确认

- **输入**: 需求拆分结果
- **输出**: `artifacts/spec/{program_id}/clarification/`
  - `questions.md` — 澄清问题列表
  - `answers.md` — 确认后的答案
  - `decisions.md` — 技术决策记录
- **规则**: 参考 `.qoder/rules/02-requirement-clarification.md`
- **注意**: 此阶段需要用户参与确认

### Phase 3: 技术规格书生成

- **输入**: 确认后的需求 + 技术决策
- **输出**: `artifacts/spec/{program_id}/`
  - `design.md` — 主技术规格书
  - `api/openapi.yaml` — OpenAPI 定义
  - `diagrams/` — 架构图、ER图
  - `checklist.md` — 验收标准
- **规则**: 参考 `.qoder/rules/03-tech-spec-generation.md`
- **规范**: 参考 `.qoder/rules/05-architecture-standards.md`

### Phase 4: 代码生成

- **输入**: 技术规格书
- **输出**: 各服务 Java 代码
- **执行**: 调用 `.qoder/skills/java-code-generation.md` Skill
- **规范**: 参考 `.qoder/rules/04-coding-standards.md`
- **产出**:
  - `repos/*/src/main/java/com/aim/mall/*/*/*.java`
  - `workspace/code-generation-report.md`

### Phase 5: HTTP 测试生成

- **输入**: 生成的 Controller 代码
- **输出**: HTTP 测试文件
- **执行**: 调用 `.qoder/skills/http-test-generation.md` Skill
- **产出**:
  - `repos/*/src/main/java/com/aim/mall/*/*/controller/http/*.http`
  - `workspace/http-test-report.md`

### Phase 6: 代码质量优化

- **输入**: 生成的代码
- **输出**: 质量分析报告和优化建议
- **执行**: 调用 `.qoder/skills/code-quality-analysis.md` Skill
- **产出**:
  - `workspace/code-quality-analysis.yaml`
  - `workspace/code-quality-report.md`

---

## Scope 控制

- **只修改** SCOPE.yml 中 `write` 允许的路径
- **超出范围**时询问用户，不要擅自修改
- `forbidden` 列表中的路径绝对不能写入

---

## 状态持久化

工作过程中维护以下文件（都在 Program 的 workspace/ 下）：

| 文件              | 时机          | 内容          |
|-----------------|-------------|-------------|
| `STATUS.yml`    | 持续更新        | 阶段进度、任务状态   |
| `CHECKPOINT.md` | 上下文紧张时      | 当前状态快照，便于恢复 |
| `HANDOFF.md`    | 会话结束未完成时    | 下次继续需要知道的信息 |
| `RESULT.md`     | Program 完成时 | 最终成果总结      |

### STATUS.yml 同步规范

使用 `add_tasks` / `update_tasks` 工具跟踪任务时，**必须同步更新 STATUS.yml 文件**，确保文件状态与实际操作一致：

```
每次调用 update_tasks 后 → 立即更新 STATUS.yml 中的对应任务状态
```

**状态映射关系**：

| 工具状态 | STATUS.yml 状态 | 说明 |
|---------|----------------|------|
| `PENDING` | `pending` | 待开始 |
| `IN_PROGRESS` | `in-progress` | 进行中 |
| `COMPLETE` | `done` | 已完成 |
| `ERROR` | `blocked` | 阻塞/出错 |
| `CANCELLED` | `cancelled` | 已取消 |

**示例**：
```yaml
# 当任务 t1 完成，t2 开始时
tasks:
  - id: t1
    status: done           # 从 in-progress 更新为 done
  - id: t2
    status: in-progress    # 从 pending 更新为 in-progress
```

## HANDOFF 格式

```
## HANDOFF

### 当前状态

- 已完成: xxx
- 进行中: yyy

### 分支

`feature/42-xxx` @ <commit-sha>

### 下一步

1. 完成 zzz
2. 测试 aaa

### 注意事项

- bbb 需要特别处理
```

## CHECKPOINT 格式

当上下文窗口紧张时，写入 CHECKPOINT.md 保存当前工作快照：

```
## CHECKPOINT

### 目标

当前 Program 的目标（一句话）

### 已完成

- [x] 任务 1
- [x] 任务 2

### 进行中

- [ ] 任务 3（进度：60%，卡在 xxx）

### 关键决策

- 选择方案 A 因为 yyy

### 文件变更

- `src/foo.ts` — 新增 xxx 功能
- `src/bar.ts` — 修改 yyy 逻辑
```

## 上下文管理

Agent 的上下文窗口有限。大任务必然跨越多次会话，HANDOFF / CHECKPOINT 机制保证跨会话零信息损失。

### 主动保存（推荐）

当对话已经很长、接近上下文上限时，用户会要求你保存进度：

1. **push** 当前分支的所有 commit
2. 更新 **STATUS.yml**（任务进度）
3. 写入 **workspace/HANDOFF.md**（交接文档）
4. 如果上下文特别紧张，额外写 **workspace/CHECKPOINT.md**（更完整的状态快照）
5. 用户开新会话，说 "继续 P-YYYY-NNN"，Agent 从 HANDOFF / CHECKPOINT 恢复

### Compress 后恢复

如果上下文已被自动压缩（compress），信息可能丢失：

1. 回退到 compress 之前的状态
2. 在回退后的完整上下文中执行 HANDOFF 流程
3. 开新会话，从 HANDOFF 恢复

HANDOFF 写入的是结构化的交接文档，信息密度远高于 compress 后的残留上下文。

---

## 文件通信原则

- 大段内容（代码、分析报告）写入文件，不要塞进对话
- 引用文件路径而非复制内容
- 保护上下文窗口

## 工作节奏

1. 明确当前任务
2. 执行并记录进度
3. 遇到决策点询问用户
4. 完成后更新状态

---

## 相关文档

- **编码规范**: `.qoder/rules/04-coding-standards.md`
- **架构规范**: `.qoder/rules/05-architecture-standards.md`
- **需求拆分**: `.qoder/rules/01-prd-decomposition.md`
- **需求澄清**: `.qoder/rules/02-requirement-clarification.md`
- **技术规格书**: `.qoder/rules/03-tech-spec-generation.md`
- **开发流程**: `orchestrator/ALWAYS/DEV-FLOW.md`
- **Sub-Agent**: `orchestrator/ALWAYS/SUB-AGENT.md`
