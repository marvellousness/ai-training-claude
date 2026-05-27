# Plan Creation and Organization

## Directory Structure

### Plan Location

**Important:**
- Do not create plans or reports in the user home directory.
- Always create plans or reports in the project's `.agents/current-work/` directory when using a `current-work` flow.

**Path convention:** `.agents/current-work/[YYMMDD]-[feature-slug]/`

**Example:** `.agents/current-work/260314-card-details-refresh/` or `.agents/current-work/260314-auth-session-fix/`

### File Organization

```text
.agents/current-work/[YYMMDD]-[feature-slug]/
|- research-report.md
|- plan.md
|- phase-01-domain-contracts.md
|- phase-02-data-integration.md
|- phase-03-persistence.md
|- phase-04-ui-flow.md
|- phase-05-navigation-and-testing.md
|- implementation-report.md
|- code-review-report.md
|- test-plan.md
`- test-report.md
```

### Active Plan State Tracking

Check whether a plan already exists for the current feature:
1. Look for matching directories in `.agents/current-work/`
2. Reuse the existing plan if it is clearly the same feature
3. Create a new directory only when the work is distinct

### Overview Plan (`plan.md`)

All `plan.md` files must include YAML frontmatter. See `output-standards.md` for the schema.

**Example `plan.md` structure:**

```markdown
---
title: "Session Refresh Hardening"
description: "Stabilize token refresh handling across app resume and expired-session paths"
status: pending
priority: P1
effort: 6h
branch: fix/session-refresh-hardening
tags: [bugfix, networking, domain, compose]
created: 2026-03-14
---

# Session Refresh Hardening

## Overview

Brief description of what this plan accomplishes.

## Phases

| # | Phase | Status | Effort | Link |
|---|-------|--------|--------|------|
| 1 | Domain Contracts | Pending | 1h | [phase-01](./phase-01-domain-contracts.md) |
| 2 | Data Integration | Pending | 2h | [phase-02](./phase-02-data-integration.md) |
| 3 | Persistence | Pending | 1h | [phase-03](./phase-03-persistence.md) |
| 4 | UI Flow | Pending | 1h | [phase-04](./phase-04-ui-flow.md) |
| 5 | Navigation and Testing | Pending | 1h | [phase-05](./phase-05-navigation-and-testing.md) |

## Dependencies

- List key dependencies here
```

### Phase Files (`phase-XX-name.md`)

Each phase file should contain:
- Context links
- Overview: priority, status, brief description
- Key insights from research or codebase review
- Functional and non-functional requirements
- Architecture notes and data flow
- Related code files to modify, create, or delete
- Detailed numbered implementation steps
- Success criteria and validation method
- Risk assessment and mitigation
- Security and privacy considerations when relevant
- Next steps and dependencies
