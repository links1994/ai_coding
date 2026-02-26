# P-2026-001-REQ-018: [mall-admin] 岗位管理接口组

## 目标

实现管理后台智能员工岗位类型管理功能，包括岗位列表查询、新增、编辑、启用/禁用等接口。

## 背景

根据 PRD 第 5.2 节和线框图 A-002/A-003，管理后台需要支持对智能员工岗位类型的全生命周期管理。岗位类型是智能员工创建时的必填选项，影响智能员工的业务定位和能力配置。

## 方案

### 整体思路

在 mall-admin 服务中实现岗位管理 Controller，通过 Feign 调用 mall-agent 服务的 JobTypeService 完成核心业务逻辑。

### 涉及模块

- **门面层**: mall-admin - 提供管理后台 HTTP 接口
- **应用层**: mall-agent - 提供岗位类型 CRUD 及状态管理

### API 接口

- `GET /admin/api/v1/job-types` - 岗位列表（支持分页、筛选）
- `POST /admin/api/v1/job-types` - 新增岗位
- `PUT /admin/api/v1/job-types/{jobTypeId}` - 编辑岗位
- `PUT /admin/api/v1/job-types/{jobTypeId}/status` - 启用/禁用岗位

## 涉及文件

- `repos/mall-admin/src/main/java/com/aim/mall/admin/controller/JobTypeAdminController.java` - 管理后台 Controller
- `repos/mall-admin/src/main/java/com/aim/mall/admin/feign/JobTypeFeignClient.java` - Feign 客户端
- `repos/mall-agent/src/main/java/com/aim/mall/agent/service/JobTypeService.java` - 岗位服务接口
- `repos/mall-agent/src/main/java/com/aim/mall/agent/service/impl/JobTypeServiceImpl.java` - 服务实现
- `repos/mall-agent/src/main/java/com/aim/mall/agent/controller/inner/JobTypeInnerController.java` - Inner 接口
- `repos/mall-agent/src/main/java/com/aim/mall/agent/mapper/JobTypeMapper.java` - 数据访问层
- `repos/mall-agent/src/main/java/com/aim/mall/agent/entity/JobType.java` - 岗位实体

## 验收标准

- [ ] 岗位列表接口支持分页和关键字搜索
- [ ] 新增岗位时校验编码唯一性
- [ ] 编辑岗位时校验编码唯一性（排除自身）
- [ ] 启用/禁用岗位同步更新关联员工状态
- [ ] 返回关联员工数量统计
- [ ] 单元测试覆盖核心逻辑
