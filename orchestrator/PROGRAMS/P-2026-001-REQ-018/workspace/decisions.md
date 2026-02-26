# 技术决策记录 (ADR) - REQ-018

## 相关需求

**REQ-018:** [mall-admin] 岗位管理接口组
- 来源: PRD 第 5.2 节, 线框图 A-002/A-003
- 服务: mall-admin (门面层) + mall-agent (核心层)

## 决策汇总

| 决策ID | 问题 | 决策 | 影响范围 |
|--------|------|------|----------|
| ADR-018-1 | 岗位编码唯一性 | 全局唯一 | 数据库唯一索引、服务校验 |
| ADR-018-2 | 禁用行为 | 无级联影响 | 仅更新状态，只影响新绑定 |
| ADR-018-3 | 删除规则 | 检查员工数量 | 带校验的删除API |
| ADR-018-4 | 实体字段 | 定义10个字段 | 数据库模式、DTO、MyBatis映射 |
| ADR-018-5 | 列表查询条件 | 关键字+分页 | 查询API、动态SQL |

## 详细决策

### ADR-018-1: 岗位编码唯一性

- **日期:** 2026-02-26
- **状态:** 已确认
- **背景:** 用户确认岗位编码必须全局唯一
- **决策:** 数据库在`code`字段添加唯一索引；服务在新增/更新前校验唯一性
- **理由:** 确保系统中编码不重复，便于清晰识别
- **影响:** 
  - 数据库: `UNIQUE INDEX uk_code ON aim_job_type(code)`
  - 服务: 校验相同编码的`count(*) = 0`（更新时排除自身）

### ADR-018-2: 禁用岗位行为

- **日期:** 2026-02-26
- **状态:** 已确认
- **背景:** 用户指定了禁用岗位类型的自定义行为
- **决策:** 禁用仅阻止新员工绑定；已存在的员工继续正常运行
- **理由:** 对现有业务操作无破坏性影响
- **影响:**
  - 禁用API仅更新status字段
  - 不对员工进行级联更新

### ADR-018-3: 删除规则

- **日期:** 2026-02-26
- **状态:** 已确认
- **背景:** 用户需要带校验的删除功能
- **决策:** 添加DELETE接口；仅当`employee_count = 0`时允许删除
- **理由:** 防止误删正在使用的岗位类型
- **影响:**
  - 新增API: `DELETE /admin/api/v1/job-types/{jobTypeId}`
  - 校验: 如有员工绑定则返回错误

### ADR-018-4: 实体字段定义

- **日期:** 2026-02-26
- **状态:** 已确认
- **背景:** 用户指定了完整的字段列表
- **决策:** 共10个字段，包含`is_default`标记
- **理由:** 支持默认岗位选择和员工数量展示
- **影响:**
  - 数据库表: `aim_job_type`包含所有字段
  - 特殊逻辑: 只能有一个岗位类型的`is_default = 1`
  - 计算字段: `employee_count`用于列表展示

### ADR-018-5: 列表查询条件

- **日期:** 2026-02-26
- **状态:** 已确认
- **背景:** 用户指定了过滤条件需求
- **决策:** 支持关键字（名称/描述）过滤和分页
- **理由:** 为管理后台提供灵活的查询能力
- **影响:**
  - 查询参数: keyword, pageNum, pageSize
  - 排序: sort_order升序, created_at降序
  - 动态SQL和可选WHERE条件
  - **注意:** 无状态过滤 - 返回所有状态的岗位类型

## API规范汇总

### 管理端接口

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | /admin/api/v1/job-types | 岗位列表（分页） |
| POST | /admin/api/v1/job-types | 新增岗位 |
| PUT | /admin/api/v1/job-types/{jobTypeId} | 编辑岗位 |
| PUT | /admin/api/v1/job-types/{jobTypeId}/status | 启用/禁用 |
| DELETE | /admin/api/v1/job-types/{jobTypeId} | 删除岗位 |

### 内部接口 (mall-agent)

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | /inner/api/v1/job-types/list | 带过滤的列表 |
| POST | /inner/api/v1/job-types/create | 新增 |
| PUT | /inner/api/v1/job-types/update | 更新 |
| PUT | /inner/api/v1/job-types/status | 状态变更 |
| DELETE | /inner/api/v1/job-types/delete | 删除 |
| GET | /inner/api/v1/job-types/detail | 根据ID查询 |
