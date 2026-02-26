# AI Coding Workspace Guide

A comprehensive guide for understanding and using this AI-driven coding workspace. This document focuses on the **AI coding execution workflow** rather than specific project implementations.

---

## Table of Contents

1. [Workspace Overview](#1-workspace-overview)
2. [Directory Structure](#2-directory-structure)
3. [Core Concepts](#3-core-concepts)
4. [Getting Started](#4-getting-started)
5. [Development Workflow](#5-development-workflow)
6. [How to Use Each Component](#6-how-to-use-each-component)
7. [Architecture & Coding Standards](#7-architecture--coding-standards)
8. [Common Commands](#8-common-commands)
9. [Troubleshooting](#9-troubleshooting)

---

## 1. Workspace Overview

This is an **AI-driven coding workspace** designed to standardize and streamline the software development process using AI assistance. It provides a structured framework for:

- Requirements decomposition and analysis
- Technical specification generation
- Code generation and quality assurance
- Project organization and tracking

### 1.1 Key Principles

| Principle | Description |
|-----------|-------------|
| **Program-Based** | All work is organized into discrete "Programs" (development tasks) |
| **Spec-Driven** | Development follows a 6-phase specification workflow |
| **Scope-Controlled** | Write permissions are explicitly defined per Program |
| **Version-Controlled** | Git-based workflow with worktree support |

### 1.2 Technology Stack

| Layer | Technology |
|-------|------------|
| Language | Java 21 |
| Framework | Spring Cloud (Microservices) |
| Database | MySQL |
| Cache | Redis |
| API Docs | OpenAPI 3.0 (Swagger) |

---

## 2. Directory Structure

```
ai_coding/
├── AGENTS.md                          # Agent configuration guide
├── orchestrator/                      # Core orchestration layer
│   ├── ALWAYS/                        # Core configuration (read every time)
│   │   ├── BOOT.md                    # Boot sequence
│   │   ├── CORE.md                    # Core working protocol
│   │   ├── DEV-FLOW.md                # Development flow specification
│   │   ├── SUB-AGENT.md               # Sub-agent specification
│   │   └── RESOURCE-MAP.yml           # Resource index
│   └── PROGRAMS/                      # Development tasks
│       ├── _TEMPLATE/                 # Program template
│       └── P-YYYY-NNN-name/           # Individual programs
│
├── .qoder/                            # Qoder AI configuration
│   ├── rules/                         # Coding and architecture rules
│   │   ├── 01-prd-decomposition.md    # Phase 1: Requirements decomposition
│   │   ├── 02-requirement-clarification.md  # Phase 2: Requirements clarification
│   │   ├── 03-tech-spec-generation.md # Phase 3: Technical specification
│   │   ├── 04-coding-standards.md     # Java coding standards
│   │   └── 05-architecture-standards.md # Architecture standards
│   └── skills/                        # Reusable skill modules
│       ├── java-code-generation.md    # Java code generation
│       ├── http-test-generation.md    # HTTP test generation
│       ├── code-quality-analysis.md   # Code quality analysis
│       ├── feature-archiving.md       # Feature archiving
│       ├── feature-retrieval.md       # Feature retrieval
│       ├── dependency-query.md        # Dependency query
│       └── offline-doc-query.md       # Offline documentation query
│
├── inputs/                            # Input documents directory
│   └── prd/                           # Product requirement documents (PRD)
│
├── repos/                             # Code repositories directory
│
└── artifacts/                         # Generated artifacts directory
    ├── spec_TEMPLATE/                 # Specification templates
    └── generated_TEMPLATE/            # Generated code templates
```

### 2.1 Directory Purposes

| Directory | Purpose | Usage |
|-----------|---------|-------|
| `orchestrator/` | Central command center for all development activities | Contains workflow definitions, Program management, and core protocols |
| `.qoder/` | AI configuration and capabilities | Contains rules (standards) and skills (reusable capabilities) |
| `inputs/` | Input documents storage | Place PRD and other input documents here |
| `repos/` | Code repositories storage | Contains actual project code repositories |
| `artifacts/` | Generated outputs storage | Contains specifications, generated code, and other artifacts |

---

## 3. Core Concepts

### 3.1 What is a "Program"?

A **Program** is the basic unit of development work in this workspace. Each Program corresponds to a specific feature development or technical task.

**Program ID Format**: `P-YYYY-NNN-{name}`
- `P`: Program abbreviation
- `YYYY`: Year (e.g., 2026)
- `NNN`: Sequence number (001-999)
- `name`: Feature name (lowercase, hyphen-separated)

**Example**: `P-2026-001-user-authentication`

### 3.2 Spec Mode (Quest Mode)

When you input a PRD (Product Requirements Document), the system automatically enters **Spec Mode** with 6 phases:

```
PRD → Phase 1: Requirements Decomposition 
    → Phase 2: Requirements Clarification 
    → Phase 3: Technical Specification 
    → Phase 4: Code Generation 
    → Phase 5: HTTP Test Generation 
    → Phase 6: Code Quality Optimization
```

| Phase | Input | Output | Description |
|-------|-------|--------|-------------|
| Phase 1 | PRD Document | `decomposition.md`, `dependencies.md`, `assignment.md` | Break down requirements and analyze dependencies |
| Phase 2 | Decomposition results | `questions.md`, `answers.md`, `decisions.md` | Clarify requirements with user |
| Phase 3 | Confirmed requirements | `design.md`, `openapi.yaml`, `checklist.md` | Generate technical specification |
| Phase 4 | Technical spec | Java code files | Generate code using Skill |
| Phase 5 | Controller code | `.http` test files | Generate HTTP tests |
| Phase 6 | Generated code | Quality report | Analyze and optimize code quality |

### 3.3 Three-Layer Service Architecture

The workspace supports a standardized microservices architecture:

```
Frontend Apps
      ↓
┌─────────────┐
│  API Gateway │  (Infrastructure)
└─────────────┘
      ↓
┌─────────────┬─────────────┬─────────────┐
│   Facade    │   Facade    │   Facade    │  ← Facade Layer
│  (Admin)    │  (Client)   │   (Other)   │
└─────────────┴─────────────┴─────────────┘
      ↓              ↓            ↓
      └──────────┬─────────┘      │
                 ↓                │
          ┌─────────────┐         │
          │ Application │  ← Application Layer (Core Business)
          └─────────────┘         │
                 ↓                │
          ┌─────────────┐         │
          │   Support   │  ← Support Layer
          └─────────────┘         │
                 ↑                │
                 └────────────────┘
```

**Service Call Rules**:

| Caller | Can Call | Cannot Call |
|--------|----------|-------------|
| Facade Layer | Application, Support | - |
| Application Layer | Support | Facade |
| Support Layer | - | Facade, Application |

**All cross-service calls must use OpenFeign.**

---

## 4. Getting Started

### 4.1 Quick Commands

| Command | Action |
|---------|--------|
| `继续 P-YYYY-NNN` | Load and continue a specific Program |
| `新 Program: xxx` | Create a new development task |
| `委托: xxx` | Delegate task to Sub-Agent |

### 4.2 Creating a New Program

#### Method 1: From Template

```bash
# Copy template directory
cp -r orchestrator/PROGRAMS/_TEMPLATE orchestrator/PROGRAMS/P-2026-001-feature-name

# Then edit:
# 1. PROGRAM.md - Fill in task definition
# 2. SCOPE.yml - Set write scope
# 3. STATUS.yml - Initialize status
```

#### Method 2: Via Agent

```
User: "新 Program: implement user login feature"
Agent:
  → Creates P-2026-001-user-login/ from template
  → Generates initial PROGRAM.md
  → Asks for write scope
  → Initializes STATUS.yml
```

### 4.3 Program Structure

```
P-YYYY-NNN-{name}/
├── PROGRAM.md              # Task definition
├── STATUS.yml              # Status tracking
├── SCOPE.yml               # Write scope control
└── workspace/              # Working documents
    ├── decomposition.md    # Phase 1: Requirements decomposition
    ├── dependencies.md     # Dependency graph
    ├── questions.md        # Phase 2: Clarification questions
    ├── answers.md          # Confirmed answers
    ├── decisions.md        # Technical decisions
    ├── CHECKPOINT.md       # Context snapshot
    ├── HANDOFF.md          # Handoff document
    └── RESULT.md           # Final results
```

### 4.4 Key Files Explained

#### PROGRAM.md

Task definition file containing:
- **Goal**: One-sentence description of what to achieve
- **Background**: Why this task is needed
- **Solution**: Technical solution overview
- **Files Involved**: Main files to modify
- **Acceptance Criteria**: Completion conditions

#### STATUS.yml

Status tracking file:

```yaml
program: P-YYYY-NNN
name: Task Name
phase: in-progress      # planning / in-progress / review / done
status: active          # not-started / active / blocked / deployed

tasks:
  - id: t1
    name: Task 1
    status: done        # pending / in-progress / done / blocked
  - id: t2
    name: Task 2
    status: in-progress
```

#### SCOPE.yml

Write scope control file:

```yaml
write:
  - orchestrator/PROGRAMS/P-YYYY-NNN-{name}/workspace/
  - repos/<repo>/src/main/java/com/...

forbidden:
  - repos/<repo>/.env
  - repos/<repo>/src/main/resources/application-prod.yml
```

---

## 5. Development Workflow

### 5.1 Git Flow

```
Confirm Task → Worktree Development → Test → PR → Merge to Main → Deploy
```

### 5.2 Complete Development Cycle

#### Step 1: Confirm Task

Read from Program's `PROGRAM.md` and `STATUS.yml` to confirm current work.

#### Step 2: Create Worktree

```bash
cd repos/<repo>
git fetch origin
git worktree add ../repos/<repo>-<feature> -b feature/<name> main
cd ../repos/<repo>-<feature>

# Install dependencies
mvn clean install
```

#### Step 3: Develop

Develop in worktree, commit code:

```bash
# Check code quality
mvn checkstyle:check

# Run tests
mvn test

# Commit
git add . && git commit -m "feat: xxx"
git push -u origin feature/<name>
```

Update `STATUS.yml` during development to track progress.

#### Step 4: Submit PR

```bash
cd repos/<repo>-<feature>
gh pr create --base main --head feature/<name>
```

#### Step 5: Merge

```bash
cd repos/<repo>
gh pr merge <PR-number> --merge --delete-branch
```

#### Step 6: Clean Worktree

```bash
cd repos/<repo>
git worktree remove ../repos/<repo>-<feature>
git branch -D feature/<name>
```

#### Step 7: Update Program Status

Update `STATUS.yml`, write `workspace/RESULT.md` if Program is complete.

### 5.3 Branch Naming

| Branch | Purpose |
|--------|---------|
| `main` | Stable version |
| `dev` | Test integration (optional) |
| `feature/*` | Feature development |
| `fix/*` | Bug fixes |

### 5.4 Commit Convention

Format: `<type>: <description>`

| Type | Description |
|------|-------------|
| `feat` | New feature |
| `fix` | Bug fix |
| `docs` | Documentation |
| `refactor` | Refactoring |
| `test` | Tests |
| `chore` | Miscellaneous |

---

## 6. How to Use Each Component

### 6.1 Using `orchestrator/`

The orchestrator is the command center of the workspace.

#### ALWAYS/ Directory

Files in this directory are read **every time** the Agent starts:

| File | When to Read | Purpose |
|------|--------------|---------|
| `BOOT.md` | First | Understand boot sequence |
| `CORE.md` | Always | Core working protocol and Spec Mode phases |
| `DEV-FLOW.md` | Always | Development workflow and Git commands |
| `SUB-AGENT.md` | When delegating | How to use Sub-Agents |
| `RESOURCE-MAP.yml` | Always | Service definitions and dependencies |

#### PROGRAMS/ Directory

Contains all development tasks:

```
PROGRAMS/
├── _TEMPLATE/           # Template for new Programs
│   ├── PROGRAM.md
│   ├── STATUS.yml
│   ├── SCOPE.yml
│   └── workspace/
└── P-YYYY-NNN-name/     # Actual Programs
```

**How to use**:
1. Copy `_TEMPLATE/` to create new Program
2. Edit `PROGRAM.md` to define the task
3. Edit `SCOPE.yml` to set write permissions
4. Edit `STATUS.yml` to track progress
5. Work in `workspace/` directory

### 6.2 Using `.qoder/rules/`

Rules define **standards and constraints** for development.

| Rule File | Phase | Purpose |
|-----------|-------|---------|
| `01-prd-decomposition.md` | Phase 1 | How to decompose PRD into requirements |
| `02-requirement-clarification.md` | Phase 2 | How to clarify requirements with user |
| `03-tech-spec-generation.md` | Phase 3 | How to generate technical specifications |
| `04-coding-standards.md` | All | Java coding standards (naming, formatting, etc.) |
| `05-architecture-standards.md` | All | Architecture standards (layers, services, DB) |

**How to use**:
- Agent reads these automatically during corresponding phases
- Developers should review `04-` and `05-` for coding guidelines
- Do not modify these files unless updating standards

### 6.3 Using `.qoder/skills/`

Skills are **reusable capability modules** that perform specific tasks.

| Skill | File | Purpose | When to Use |
|-------|------|---------|-------------|
| Java Code Generation | `java-code-generation.md` | Generate Java microservice code | Phase 4 |
| HTTP Test Generation | `http-test-generation.md` | Generate HTTP test files | Phase 5 |
| Code Quality Analysis | `code-quality-analysis.md` | Analyze code quality | Phase 6 |
| Feature Archiving | `feature-archiving.md` | Archive completed features | After feature completion |
| Feature Retrieval | `feature-retrieval.md` | Query archived features | Before new development |
| Dependency Query | `dependency-query.md` | Query module dependencies | When needed |
| Offline Doc Query | `offline-doc-query.md` | Query local documentation | When needed |

**How to use**:
```
User: "Generate code for this specification"
Agent:
  → Reads `java-code-generation.md` Skill
  → Follows the workflow in the Skill
  → Generates code according to standards
```

### 6.4 Using `inputs/`

Directory for **input documents**, primarily PRDs.

**How to use**:
1. Place PRD files in `inputs/prd/`
2. Reference them when starting Spec Mode:
   ```
   User: "Based on inputs/prd/my-feature-prd.md, start development"
   ```
3. Agent reads PRD and enters Phase 1 automatically

**Naming convention**: `{system}-{feature}-prd.md`

### 6.5 Using `repos/`

Directory for **code repositories**.

**How to use**:
1. Each subdirectory is a separate Git repository
2. Use Git worktree for feature development (see Section 5.2)
3. Follow the service architecture defined in `RESOURCE-MAP.yml`
4. Respect the `SCOPE.yml` write permissions

### 6.6 Using `artifacts/`

Directory for **generated outputs**.

**Structure**:
```
artifacts/
├── spec_{program_id}/          # Generated specifications
│   ├── design.md
│   ├── api/
│   └── diagrams/
└── generated_{program_id}/     # Generated code artifacts
```

**How to use**:
- Specifications are auto-generated during Spec Mode
- Reference these for implementation
- Archive completed features for future retrieval

---

## 7. Architecture & Coding Standards

### 7.1 Four-Layer Architecture (Mandatory)

Must strictly follow: **Interface Layer → Application Layer → Domain Layer → Infrastructure Layer**

```
┌─────────────────────────────────────┐
│      Interface Layer (Controller)    │  ← Receive requests, parameter validation
├─────────────────────────────────────┤
│      Application Layer (Service)     │  ← Business flow orchestration
├─────────────────────────────────────┤
│      Domain Layer (Domain)           │  ← Business logic, domain models
├─────────────────────────────────────┤
│   Infrastructure Layer (Mapper/Repo) │  ← Data access, external calls
└─────────────────────────────────────┘
```

**Constraints**:
- No cross-layer direct calls
- Higher-level modules don't depend on lower-level implementations
- Follow dependency inversion principle

### 7.2 Service Types

| Service Type | Responsibility | Call Scope |
|--------------|----------------|------------|
| **QueryService** | Read-only, data aggregation | Only Mapper queries |
| **ManageService** | Write operations, transactions | Services or Mapper for CUD |
| **DomainService** | Complex multi-entity logic | No direct Mapper calls |

### 7.3 Module Path Prefixes

| Module Type | Path Prefix | Description |
|-------------|-------------|-------------|
| Facade - Admin | `/admin/api/v1/` | For admin backend |
| Facade - Client | `/app/api/v1/` | For APP/client |
| Service | `/inner/api/v1/` | For inter-service RPC |

### 7.4 Interface Style Standards

#### Facade Services

- **Style**: Full RESTful (GET/POST/PUT/DELETE)
- **Path Parameters**: Maximum one, at end of URL
- **Example**: `GET /admin/api/v1/users/{id}`

#### Application Services

- **Style**: Simplified (GET/POST only)
- **Path Parameters**: **Prohibited**
- **Query**: Use Query params (GET)
- **Operations**: Use RequestBody (POST)
- **Example**: `POST /inner/api/v1/users/detail`

### 7.5 Database Standards

#### Universal Fields (Required)

| Field | Type | Description |
|-------|------|-------------|
| `id` | BIGINT | Primary key, auto-increment |
| `create_time` | DATETIME | Creation timestamp |
| `update_time` | DATETIME | Auto-updated timestamp |
| `is_deleted` | TINYINT | Logical deletion flag |
| `creator_id` | BIGINT | Creator ID (optional) |
| `updater_id` | BIGINT | Updater ID (optional) |

#### Character Set

- **Charset**: `utf8mb4`
- **Collation**: Use database default (do not specify)

### 7.6 Java Coding Standards

#### Naming Conventions

| Type | Pattern | Example |
|------|---------|---------|
| Entity | PascalCase + DO | `UserDO` |
| DTO | PascalCase + DTO | `UserCreateDTO` |
| VO | PascalCase + VO | `UserVO` |
| Request | PascalCase + Request | `UserCreateRequest` |
| Response | PascalCase + Response | `UserResponse` |
| Service | PascalCase + Service | `UserService` |
| Mapper | PascalCase + Mapper | `UserMapper` |

#### Method Naming

| Operation | Pattern | Example |
|-----------|---------|---------|
| Query single | `getById`, `getByXXX` | `getById(Long id)` |
| Query list | `listXXX` | `listByStatus(status)` |
| Paginated | `pageXXX` | `pageUsers(request)` |
| Create | `createXXX` | `createUser(request)` |
| Update | `updateXXX` | `updateUser(request)` |
| Delete | `deleteXXX` | `deleteById(id)` |

#### Lombok Annotations

| Annotation | Use For |
|------------|---------|
| `@Data` | Entities, DTOs |
| `@Getter` | Read-only objects |
| `@Builder` | Complex object creation |
| `@Slf4j` | Logging |

#### Serialization

```java
@Data
public class UserRequest implements Serializable {
    private static final long serialVersionUID = -1L;
    // ...
}
```

#### Time Formatting

```java
@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
private LocalDateTime createdAt;
```

### 7.7 Exception Handling

Use only these three standard exceptions:

| Exception | Use Case |
|-----------|----------|
| `MethodArgumentValidationException` | Parameter validation failure |
| `RemoteApiCallException` | Remote service call failure |
| `BusinessException` | Business rule violation |

Simplified handling:
```java
try {
    // business logic
} catch (Exception e) {
    log.error("Operation failed", e);
    return CommonResult.failed("ERROR_CODE", "Message");
}
```

### 7.8 Response Format

Use `CommonResult<T>` for all responses:

```java
// Success
CommonResult.success(data)
CommonResult.pageSuccess(items, total)

// Failure
CommonResult.failed("ERROR_CODE", "Message")
```

---

## 8. Common Commands

### 8.1 Git Worktree

```bash
# Create worktree
git worktree add ../repos/<repo>-<feature> -b feature/<name> main

# Remove worktree
git worktree remove ../repos/<repo>-<feature>

# List worktrees
git worktree list
```

### 8.2 GitHub CLI

```bash
# Create PR
gh pr create --base main --head feature/<name>

# Merge PR
gh pr merge <PR-number> --merge --delete-branch

# View status
gh pr status
```

### 8.3 Maven

```bash
# Compile
mvn clean compile

# Test
mvn test

# Package
mvn clean package

# Skip tests
mvn clean package -DskipTests

# Install
mvn clean install
```

---

## 9. Troubleshooting

### 9.1 Permission Denied

**Cause**: File not in `SCOPE.yml` write list

**Solution**: Check and update `SCOPE.yml` in your Program directory

### 9.2 Changes Not Reflected

**Checklist**:
1. Correct worktree? `git worktree list`
2. Correct branch? `git branch`
3. File tracked? `git status`

### 9.3 Feign Call Failed

**Checklist**:
1. Target service running?
2. Feign config correct?
3. No path parameters in URL (use Query/Body instead)

### 9.4 Getting Help

1. Check `.qoder/rules/` for standards
2. Review similar Programs in `orchestrator/PROGRAMS/`
3. Check `README-*.md` files in each directory
4. Ask Agent with specific error messages

---

## Appendix: File Quick Reference

### Core Documentation

| File | Purpose |
|------|---------|
| `AGENTS.md` | Agent configuration |
| `orchestrator/ALWAYS/BOOT.md` | Boot sequence |
| `orchestrator/ALWAYS/CORE.md` | Core protocol |
| `orchestrator/ALWAYS/DEV-FLOW.md` | Development flow |
| `orchestrator/ALWAYS/RESOURCE-MAP.yml` | Resource index |

### Rules

| File | Purpose |
|------|---------|
| `01-prd-decomposition.md` | Phase 1 rules |
| `02-requirement-clarification.md` | Phase 2 rules |
| `03-tech-spec-generation.md` | Phase 3 rules |
| `04-coding-standards.md` | Coding standards |
| `05-architecture-standards.md` | Architecture standards |

### Skills

| File | Purpose |
|------|---------|
| `java-code-generation.md` | Code generation |
| `http-test-generation.md` | Test generation |
| `code-quality-analysis.md` | Quality analysis |
| `feature-archiving.md` | Feature archiving |
| `feature-retrieval.md` | Feature retrieval |

---

*Last Updated: February 26, 2026*
