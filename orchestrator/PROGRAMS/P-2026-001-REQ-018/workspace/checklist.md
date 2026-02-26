# REQ-018 验收清单

## 概述

| 项目 | 值 |
|------|-----|
| 需求ID | REQ-018 |
| 描述 | [mall-admin] 岗位管理接口组 |
| 状态 | 待开发 |

---

## 数据库

| ID | 检查项 | 状态 | 参考 |
|----|-----------|--------|-----------|
| DB-001 | 创建`aim_job_type`表 | 待完成 | tech-spec.md 2.2 |
| DB-002 | `code`字段唯一索引`uk_code` | 待完成 | tech-spec.md 2.2 |
| DB-003 | `status`字段索引`idx_status` | 待完成 | tech-spec.md 2.2 |
| DB-004 | `sort_order`字段索引`idx_sort_order` | 待完成 | tech-spec.md 2.2 |
| DB-005 | 所有字段有正确的注释 | 待完成 | tech-spec.md 2.2 |

---

## API实现

### 岗位列表

| ID | 检查项 | 状态 | 参考 |
|----|-----------|--------|-----------|
| API-001-1 | GET /admin/api/v1/job-types接口实现 | 待完成 | tech-spec.md 3.2.1 |
| API-001-2 | 名称和描述关键字搜索 | 待完成 | tech-spec.md 3.2.1 |
| API-001-3 | pageNum和pageSize分页 | 待完成 | tech-spec.md 3.2.1 |
| API-001-4 | 按sort_order升序、created_at降序排序 | 待完成 | tech-spec.md 3.2.1 |
| API-001-5 | 返回每个岗位的employee_count | 待完成 | tech-spec.md 3.2.1 |
| API-001-6 | 非法参数的正确错误处理 | 待完成 | tech-spec.md 3.2.1 |

### 新增岗位

| ID | 检查项 | 状态 | 参考 |
|----|-----------|--------|-----------|
| API-002-1 | POST /admin/api/v1/job-types接口实现 | 待完成 | tech-spec.md 3.2.2 |
| API-002-2 | 编码唯一性校验（全局） | 待完成 | tech-spec.md 3.2.2 |
| API-002-3 | 编码格式校验（字母数字下划线） | 待完成 | tech-spec.md 3.2.2 |
| API-002-4 | isDefault逻辑 - 设置时清除其他默认值 | 待完成 | tech-spec.md 3.2.2 |
| API-002-5 | 返回包含所有字段的新增岗位 | 待完成 | tech-spec.md 3.2.2 |
| API-002-6 | 重复编码的正确错误码 | 待完成 | tech-spec.md 3.4 |

### 编辑岗位

| ID | 检查项 | 状态 | 参考 |
|----|-----------|--------|-----------|
| API-003-1 | PUT /admin/api/v1/job-types/{jobTypeId}接口实现 | 待完成 | tech-spec.md 3.2.3 |
| API-003-2 | 禁止修改编码 | 待完成 | tech-spec.md 3.2.3 |
| API-003-3 | isDefault逻辑 - 设置时清除其他默认值 | 待完成 | tech-spec.md 3.2.3 |
| API-003-4 | 返回包含所有字段的更新岗位 | 待完成 | tech-spec.md 3.2.3 |
| API-003-5 | 岗位不存在的正确错误码 | 待完成 | tech-spec.md 3.4 |

### 更新岗位状态

| ID | 检查项 | 状态 | 参考 |
|----|-----------|--------|-----------|
| API-004-1 | PUT /admin/api/v1/job-types/{jobTypeId}/status接口实现 | 待完成 | tech-spec.md 3.2.4 |
| API-004-2 | 仅更新status字段 | 待完成 | tech-spec.md 3.2.4 |
| API-004-3 | 对员工无级联影响 | 待完成 | tech-spec.md 3.2.4 |
| API-004-4 | 岗位不存在的正确错误码 | 待完成 | tech-spec.md 3.4 |

### 删除岗位

| ID | 检查项 | 状态 | 参考 |
|----|-----------|--------|-----------|
| API-005-1 | DELETE /admin/api/v1/job-types/{jobTypeId}接口实现 | 待完成 | tech-spec.md 3.2.5 |
| API-005-2 | 删除前校验无员工绑定 | 待完成 | tech-spec.md 3.2.5 |
| API-005-3 | 有员工绑定时拒绝删除 | 待完成 | tech-spec.md 3.2.5 |
| API-005-4 | 岗位不存在的正确错误码 | 待完成 | tech-spec.md 3.4 |
| API-005-5 | 有绑定员工的正确错误码 | 待完成 | tech-spec.md 3.4 |

---

## 内部API (mall-agent)

| ID | 检查项 | 状态 | 参考 |
|----|-----------|--------|-----------|
| INNER-001 | GET /inner/api/v1/job-types/list实现 | 待完成 | tech-spec.md 3.1 |
| INNER-002 | POST /inner/api/v1/job-types/create实现 | 待完成 | tech-spec.md 3.1 |
| INNER-003 | PUT /inner/api/v1/job-types/update实现 | 待完成 | tech-spec.md 3.1 |
| INNER-004 | PUT /inner/api/v1/job-types/status实现 | 待完成 | tech-spec.md 3.1 |
| INNER-005 | DELETE /inner/api/v1/job-types/delete实现 | 待完成 | tech-spec.md 3.1 |
| INNER-006 | GET /inner/api/v1/job-types/detail实现 | 待完成 | tech-spec.md 3.1 |

---

## Feign客户端

| ID | 检查项 | 状态 | 参考 |
|----|-----------|--------|-----------|
| FEIGN-001 | JobTypeFeignClient接口定义 | 待完成 | tech-spec.md 3.3 |
| FEIGN-002 | 所有方法映射到内部API | 待完成 | tech-spec.md 3.3 |
| FEIGN-003 | 正确的请求/响应DTO | 待完成 | tech-spec.md 3.3 |

---

## 业务规则

| ID | 检查项 | 状态 | 参考 |
|----|-----------|--------|-----------|
| RULE-001 | 编码唯一性校验实现 | 待完成 | tech-spec.md 5.2 |
| RULE-002 | 默认标记逻辑 - 只能有一个默认 | 待完成 | tech-spec.md 5.2 |
| RULE-003 | 删除校验 - 检查员工数量 | 待完成 | tech-spec.md 5.2 |
| RULE-004 | 状态变更 - 无级联影响 | 待完成 | tech-spec.md 5.2 |

---

## 错误码

| ID | 检查项 | 状态 | 参考 |
|----|-----------|--------|-----------|
| ERR-001 | 重复编码错误码10092002 | 待完成 | tech-spec.md 3.4 |
| ERR-002 | 不存在错误码10092003 | 待完成 | tech-spec.md 3.4 |
| ERR-003 | 有绑定员工错误码10092004 | 待完成 | tech-spec.md 3.4 |
| ERR-004 | 参数错误码10091001 | 待完成 | tech-spec.md 3.4 |

---

## 测试

| ID | 检查项 | 状态 | 参考 |
|----|-----------|--------|-----------|
| TEST-001 | JobTypeService单元测试 | 待完成 | - |
| TEST-002 | JobTypeMapper单元测试 | 待完成 | - |
| TEST-003 | 管理端API集成测试 | 待完成 | - |
| TEST-004 | 内部API集成测试 | 待完成 | - |
| TEST-005 | Feign客户端集成测试 | 待完成 | - |

---

## 文档

| ID | 检查项 | 状态 | 参考 |
|----|-----------|--------|-----------|
| DOC-001 | API文档已更新 | 已完成 | openapi.yaml |
| DOC-002 | 技术规格书已完成 | 已完成 | tech-spec.md |
| DOC-003 | 数据库迁移脚本已准备 | 待完成 | - |

---

## 签字

| 角色 | 姓名 | 日期 | 签字 |
|------|------|------|------|
| 开发 | | | |
| 审核 | | | |
| 测试 | | | |
