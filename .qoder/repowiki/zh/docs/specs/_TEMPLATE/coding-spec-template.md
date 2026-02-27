---
spec_type: 编码规范
version: v1.0
scope: 所有 Java 代码
status: draft
created_at: YYYY-MM-DD
updated_at: YYYY-MM-DD
author: 作者名
---

# 编码规范标题

## 概述

描述本编码规范的目的和适用范围。

## 命名规范

### 1. 类命名

| 类型 | 命名规范 | 示例 |
|------|----------|------|
| Controller | `XxxController` | `AgentController` |
| Service | `XxxService` / `XxxDomainService` | `AgentService` |
| Mapper | `XxxMapper` | `AgentMapper` |
| Entity | `AimXxxDO` | `AimAgentDO` |
| DTO | `XxxDTO` | `AgentCreateDTO` |
| Request | `XxxRequest` | `AgentCreateRequest` |
| Response | `XxxResponse` | `AgentResponse` |

### 2. 方法命名

| 操作类型 | 命名规范 | 示例 |
|----------|----------|------|
| 查询单个 | `getXxxByYyy` | `getAgentById` |
| 查询列表 | `listXxx` | `listAgents` |
| 分页查询 | `pageXxx` | `pageAgents` |
| 创建 | `createXxx` | `createAgent` |
| 更新 | `updateXxx` | `updateAgent` |
| 删除 | `deleteXxx` | `deleteAgent` |

### 3. 变量命名

- 局部变量：camelCase
- 常量：UPPER_SNAKE_CASE
- 类成员：camelCase

## 代码风格

### 1. 缩进与格式

- 使用 4 个空格缩进
- 每行不超过 120 字符
- 方法长度不超过 50 行

### 2. 注释规范

```java
/**
 * 类说明
 *
 * @author 作者名
 * @since YYYY/MM/DD
 */
public class Example {

    /**
     * 方法说明
     *
     * @param param1 参数1说明
     * @param param2 参数2说明
     * @return 返回值说明
     */
    public ReturnType methodName(Type1 param1, Type2 param2) {
        // 实现
    }
}
```

## 异常处理

### 1. 异常类型

| 场景 | 异常类型 | 说明 |
|------|----------|------|
| 参数错误 | `IllegalArgumentException` | 参数校验失败 |
| 业务错误 | `BusinessException` | 业务规则违反 |
| 系统错误 | `SystemException` | 系统内部错误 |

### 2. 异常处理原则

- 禁止捕获异常后不做处理
- 禁止用异常控制流程
- 必须记录异常日志

## 日志规范

### 1. 日志级别

| 级别 | 使用场景 |
|------|----------|
| ERROR | 系统错误、需要立即处理的问题 |
| WARN | 警告、潜在问题 |
| INFO | 重要业务操作记录 |
| DEBUG | 调试信息 |

### 2. 日志格式

```java
log.info("操作描述, param1={}, param2={}", value1, value2);
log.error("操作失败, orderId={}", orderId, exception);
```

## 适用范围

- 所有 Java 源代码
- 所有新功能开发

## 相关规范

- [架构规范](./架构规范.md)
- [错误码规范](./错误码规范.md)

## 版本历史

| 版本 | 日期 | 修改人 | 修改内容 |
|------|------|--------|----------|
| v1.0 | YYYY-MM-DD | 作者名 | 初始版本 |

## 附件

- 规范维护责任人：技术委员会
- 审核周期：每年
