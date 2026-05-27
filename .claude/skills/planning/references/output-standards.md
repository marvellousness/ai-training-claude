# Output Standards and Quality

## Plan File Format

**Important:**
- Do not create plans or reports in the user directory.
- Always create plans or reports in the current project directory.

### YAML Frontmatter

All `plan.md` files must include YAML frontmatter:

```yaml
---
title: "{Brief plan title}"
description: "{One-sentence summary}"
status: pending
priority: P2
effort: 4h
branch: feat/feature-name
tags: [feature, compose]
created: 2026-03-14
---
```

### Auto-Population Rules

When creating plans, auto-populate:
- `title` from the task description
- `description` from the overview summary
- `status` as `pending` for new plans
- `priority` from user request or default `P2`
- `effort` from summed phase estimates
- `branch` from the current git branch when useful
- `tags` inferred from task keywords and architecture area
- `created` as today's date

### Recommended Tag Vocabulary

- **Type**: `feature`, `bugfix`, `refactor`, `docs`, `infra`
- **UI**: `compose`, `views-xml`, `navigation`, `design-system`, `a11y`, `l10n`
- **Architecture**: `domain`, `networking`, `persistence`, `viewmodel`, `coroutines`, `flow`, `rxjava`
- **Storage/Security**: `room`, `datastore`, `keystore`, `privacy`, `security`

## Task Breakdown

- Transform complex requirements into clear, actionable tasks
- Respect dependencies, risk, and business value
- Include specific file paths for every expected modification
- Provide acceptance criteria and validation steps per phase

## Workflow Process

1. Initial analysis
2. Optional research phase
3. Synthesis and design
4. Plan documentation
5. Review and refinement

## Output Requirements

### What Planners Do
- Create plans only, not implementation
- Provide the plan path and a concise summary
- Keep plans self-contained
- Include pseudocode only when helpful
- Provide options with trade-offs when needed

### Writing Style
- Sacrifice grammar for concision
- Prefer bullets, short sentences, and direct instructions
- Prioritize actionable information over prose

## Quality Standards

### Thoroughness
- Consider edge cases and failure modes
- Think through user flows, lifecycle events, and dependency boundaries
- Document assumptions explicitly

### Maintainability
- Prefer solutions that fit the current architecture
- Avoid over-engineering
- Record rationale for important choices

### Research Depth
- Research more when uncertainty is material
- Validate against Android best practices and repo patterns

### Security and Performance
- Address secure storage, privacy, and data protection when relevant
- Identify rendering, threading, memory, or startup implications
- Note main-thread constraints and background work requirements

### Implementability
- Be detailed enough for junior developers
- Keep plans grounded in the actual repo structure and tooling
