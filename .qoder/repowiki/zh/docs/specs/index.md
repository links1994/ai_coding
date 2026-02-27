# 技术规范索引

本目录归档项目技术规范，包括架构规范、编码规范、接口规范、错误码规范等，供开发参考和代码质量分析使用。

## 规范列表

### 架构规范

（暂无）

### 编码规范

（暂无）

### 错误码规范

- [错误码规范](./错误码规范.md) - v1.0 - 2026-02-04

### 接口规范

（暂无）

### 数据库规范

（暂无）

### 安全规范

（暂无）

## 按时间索引

| 日期 | 规范 | 类型 | 版本 |
|------|------|------|------|
| 2026-02-04 | 错误码规范 | 错误码规范 | v1.0 |

## 规范模板

创建新规范时，请使用以下模板：

- [通用规范模板](./_TEMPLATE/spec-template.md)
- [架构规范模板](./_TEMPLATE/architecture-spec-template.md)
- [编码规范模板](./_TEMPLATE/coding-spec-template.md)

## 如何添加新规范

1. 选择合适的模板
2. 根据规范类型保存到对应文件
3. 更新本索引
4. 或使用 `spec-archiving` Skill 自动归档

## 相关 Skill

- [规范归档 Skill](../../../skills/spec-archiving.md)
- [知识库查询 Skill](../../../skills/knowledge-base-query.md)
- [代码质量分析 Skill](../../../skills/code-quality-analysis.md)

---

**注意**: 所有规范文档必须包含 YAML Front Matter 头部信息，格式如下：

```yaml
---
spec_type: 规范类型
version: v1.0
scope: 适用范围
status: active
created_at: YYYY-MM-DD
updated_at: YYYY-MM-DD
author: 作者名
---
```
