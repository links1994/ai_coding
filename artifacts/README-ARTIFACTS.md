# 产出物说明

Artifacts 目录存放开发过程中产生的各种产出物。

---

## 目录结构

```
artifacts/
├── README-ARTIFACTS.md        # 本文件
├── spec/                      # Spec 产出物（Phase 1-3）
│   ├── _TEMPLATE/             # 模板目录
│   │   ├── design.md          # 技术规格书模板
│   │   ├── phase1-requirements/   # Phase 1 模板
│   │   ├── phase2-clarification/  # Phase 2 模板
│   │   └── phase3-design/         # Phase 3 模板
│   └── {program_id}/          # 具体 Program 的产出物
│       ├── requirements/      # Phase 1 产出物
│       │   ├── decomposition.md
│       │   ├── dependencies.md
│       │   └── assignment.md
│       ├── clarification/     # Phase 2 产出物
│       │   ├── questions.md
│       │   ├── answers.md
│       │   └── decisions.md
│       ├── design.md          # Phase 3 主产出物
│       ├── api/
│       │   └── openapi.yaml
│       ├── diagrams/
│       │   ├── er-diagram.mmd
│       │   └── architecture.mmd
│       └── checklist.md
│
└── generated/                 # 生成物（Phase 4-6）
    ├── _TEMPLATE/             # 代码生成模板
    │   ├── java-code.md       # Java 代码模板
    │   └── feature-archive.md # 功能归档模板
    └── {program_id}/          # 具体 Program 的生成物
        ├── code/              # Phase 4: 生成的代码
        ├── tests/             # Phase 5: 生成的测试
        ├── reports/           # Phase 6: 质量报告
        └── archives/          # 功能归档
```

---

## 产出物说明

### requirements/

Phase 1（需求拆分）的产出物：

| 文件 | 说明 |
|------|------|
| `decomposition.md` | 需求拆分结果，包含所有 REQ-XXX 需求项 |
| `dependencies.md` | 依赖关系图，标注 hard/soft 依赖 |
| `assignment.md` | 服务归属判断，标记各需求所属服务 |

### clarification/

Phase 2（需求澄清）的产出物：

| 文件 | 说明 |
|------|------|
| `questions.md` | 澄清问题清单，按优先级分类 |
| `answers.md` | 用户确认后的答案 |
| `decisions.md` | 技术决策记录（ADR） |

### design.md

Phase 3（技术规格书）的主产出物，包含：

- 概述（目标、背景、技术选型）
- 数据模型设计（ER 图、表结构）
- API 接口定义（OpenAPI、Feign 接口）
- 架构设计（服务调用关系、时序图、代码结构）
- 验收标准

### api/openapi.yaml

OpenAPI 3.0 规范的接口定义文件。

### diagrams/

架构图表：

| 文件 | 说明 |
|------|------|
| `er-diagram.mmd` | 实体关系图（Mermaid 语法） |
| `architecture.mmd` | 系统架构图（Mermaid 语法） |

### checklist.md

验收标准清单，包含：

- 功能验收项
- 技术验收项
- 性能验收项
- 安全验收项

---

## 各 Phase 产出物路径

| Phase | 产出物 | 路径 |
|-------|--------|------|
| Phase 1 | 需求拆分文档 | `artifacts/spec/{program_id}/requirements/` |
| Phase 2 | 需求澄清文档 | `artifacts/spec/{program_id}/clarification/` |
| Phase 3 | 技术规格书 | `artifacts/spec/{program_id}/design.md` |
| Phase 4 | 生成代码 | `artifacts/generated/{program_id}/code/` |
| Phase 5 | 测试文件 | `artifacts/generated/{program_id}/tests/` |
| Phase 6 | 质量报告 | `artifacts/generated/{program_id}/reports/` |
| 归档 | 功能档案 | `artifacts/generated/{program_id}/archives/` |

---

## 与 workspace/ 的区别

| 目录 | 用途 | 内容 |
|------|------|------|
| `artifacts/spec/{program_id}/` | Phase 1-3 正式产出物 | 需求拆分、技术规格书、API 定义等 |
| `artifacts/generated/{program_id}/` | Phase 4-6 生成物 | 代码、测试、报告、归档等 |
| `orchestrator/PROGRAMS/{program_id}/workspace/` | 工作过程文档 | 状态跟踪、交接文档、临时文件等 |

**区别**：
- **Artifacts** 是**正式交付物**，需要保留和审阅
- **Workspace** 是**工作过程文档**，用于跟踪进度和上下文恢复

---

## 使用建议

1. **Phase 1 完成后**：检查 `spec/{program_id}/requirements/` 是否完整
2. **Phase 2 完成后**：确认 `spec/{program_id}/clarification/answers.md` 已得到用户确认
3. **Phase 3 完成后**：审阅 `spec/{program_id}/design.md` 和 `api/openapi.yaml`
4. **Phase 4 完成后**：检查 `generated/{program_id}/code/` 代码生成质量
5. **Phase 5 完成后**：验证 `generated/{program_id}/tests/` 测试覆盖
6. **Phase 6 完成后**：查看 `generated/{program_id}/reports/` 质量报告
