# README-docs

本地文档中心 - 存放项目相关的技术文档、API文档、框架文档和操作指南。

---

## 目录结构

```
.qoder/repowiki/docs/
├── README-docs.md              # 本文件（文档中心说明）
├── frameworks/                 # 框架文档
│   ├── spring-boot/
│   ├── mybatis-plus/
│   └── rocketmq/
├── apis/                       # API 文档
│   ├── internal/               # 内部服务 API
│   ├── aliyun/                 # 阿里云 API
│   └── third-party/            # 第三方 API
├── specs/                      # 技术规范
│   ├── java-coding-standard.md
│   ├── restful-api-design.md
│   └── microservice-patterns.md
└── guides/                     # 操作指南
    ├── deployment/             # 部署指南
    ├── operations/             # 运维指南
    └── troubleshooting/        # 故障排查
```

---

## 文档分类说明

### frameworks/ - 框架文档

存放第三方框架的官方文档离线版：

| 框架 | 内容 | 用途 |
|------|------|------|
| Spring Boot | 参考文档、API文档 | 开发参考 |
| MyBatis-Plus | 使用指南、API文档 | ORM开发 |
| RocketMQ | 使用文档、最佳实践 | 消息队列 |

**使用场景**：
- 网络不可用时查询技术细节
- 快速查找框架使用示例
- 了解框架配置参数

### apis/ - API 文档

存放各类 API 接口文档：

#### internal/ - 内部服务 API
- `user-service-api.md` - 用户服务接口
- `agent-service-api.md` - 智能员工服务接口
- `chat-service-api.md` - 对话服务接口

#### aliyun/ - 阿里云 API
- `nlp-api.md` - 自然语言处理
- `oss-api.md` - 对象存储

#### third-party/ - 第三方 API
- `payment-api.md` - 支付接口
- `sms-api.md` - 短信服务

**使用场景**：
- 开发时查询接口定义
- 了解请求/响应格式
- 查看错误码说明

### specs/ - 技术规范

存放项目技术规范和标准：

| 文档 | 内容 | 用途 |
|------|------|------|
| java-coding-standard.md | Java编码规范 | 代码审查 |
| restful-api-design.md | RESTful设计规范 | 接口设计 |
| microservice-patterns.md | 微服务设计模式 | 架构设计 |
| ddd-practice-guide.md | DDD实践指南 | 领域建模 |

**使用场景**：
- 代码审查时参考规范
- 设计时遵循标准
- 团队技术对齐

### guides/ - 操作指南

存放操作手册和运维文档：

#### deployment/ - 部署指南
- `docker-deploy.md` - Docker部署
- `k8s-deploy.md` - Kubernetes部署

#### operations/ - 运维指南
- `monitoring.md` - 监控配置
- `backup-restore.md` - 备份恢复

#### troubleshooting/ - 故障排查
- `common-issues.md` - 已知问题
- `performance-tuning.md` - 性能调优

**使用场景**：
- 部署时参考步骤
- 运维时查询配置
- 故障时排查问题

---

## 使用方式

### 方式 1：直接浏览

进入对应目录查看文档：

```bash
# 查看 Spring Boot 文档
cat .qoder/repowiki/docs/frameworks/spring-boot/reference.md

# 查看用户服务 API
cat .qoder/repowiki/docs/apis/internal/user-service-api.md
```

### 方式 2：使用 Skill 查询

使用 `offline-doc-query` Skill 智能检索：

```
用户: "mybatis-plus 分页查询如何实现？"
Agent:
  → 调用 offline-doc-query Skill
  → 检索 docs/frameworks/mybatis-plus/
  → 返回分页查询示例
```

---

## 文档索引

为提高查询效率，维护文档索引文件：

```yaml
# .qoder/repowiki/docs/index.yml
documents:
  - id: mybatis-plus-guide
    name: MyBatis-Plus 指南
    path: frameworks/mybatis-plus/guide.md
    type: framework
    tags: [orm, mybatis, database]
    
  - id: user-service-api
    name: 用户服务 API
    path: apis/internal/user-service-api.md
    type: api
    tags: [user, service, feign]
```

**更新索引**：
```
委托: 更新文档索引
→ 扫描 docs/ 目录
→ 提取文档元数据
→ 更新 index.yml
```

---

## 文档同步

当网络可用时，可从官方源同步最新文档：

```yaml
# .qoder/repowiki/docs/sync-config.yml
sources:
  - name: mybatis-plus-docs
    url: https://baomidou.com/guide/
    local_path: frameworks/mybatis-plus/
    sync_interval: 7d
```

**同步命令**：
```
委托: 同步文档
→ 读取 sync-config.yml
→ 下载最新文档
→ 更新索引
```

---

## 添加新文档

1. **确定文档类型**：framework / api / spec / guide
2. **放入对应目录**：选择正确的子目录
3. **更新索引**：在 `index.yml` 中添加条目
4. **添加标签**：便于检索时匹配

---

## 相关文档

- **离线文档查询 Skill**：`.qoder/skills/offline-doc-query.md`
- **功能归档目录**：`.qoder/repowiki/features/`
