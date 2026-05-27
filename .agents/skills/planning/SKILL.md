---
name: planning
description: Plan Android feature implementations, architecture updates, and technical roadmaps for Kotlin, Jetpack Compose, Android Views, and Gradle projects.
license: MIT
---

# Planning

Create detailed implementation plans through codebase analysis, solution design, and structured documentation.
**IMPORTANT**: Never start implementation. Do not edit product code. Propose the plan only.

## Input

The planner receives one of:
- A research report path at `.agents/current-work/[folder-name]/research-report.md`
- Direct task context if research was skipped

## Core Principles

- **YAGNI**, **KISS**, **DRY**
- Be honest, direct, concise
- Sacrifice grammar for concision
- Stay token-efficient without losing critical detail

## Workflow

### 1. Understand Context
Load `references/codebase-understanding.md`.
Skip if the caller already provides strong scout output or enough context.

- Read research report if provided, else trigger research only when needed
- Analyze relevant code with Glob, Grep, and Read
- Understand existing architecture, module boundaries, and repo conventions

### 2. Solution Design
Load `references/solution-design.md`.

- Evaluate approaches and trade-offs
- Align with existing Jetpack Compose, Android Views, Clean Architecture, Kotlin Coroutines/Flow, RxJava, and Hilt/Dagger patterns already in the repo
- Consider security, performance, testing, and rollout risk

### 3. Plan Creation
Load `references/plan-organization.md`.

- Create a structured implementation plan
- Break work into phases with clear dependencies
- Include specific file paths, validation steps, and acceptance criteria

### 4. Output
Load `references/output-standards.md`.

- If input is `.agents/current-work/[folder-name]/research-report.md` -> Save the plan to `.agents/current-work/[folder-name]/plan.md`
- If no file path is provided -> return the plan in the response unless the caller explicitly wants a `current-work` folder flow
- Respond with the plan path if saved, plus a concise summary

## Rules

- **DO NOT** implement code - planning only
- **MUST** save to `.agents/current-work/[folder-name]/plan.md` when working in a `current-work` context
- Include pseudocode only when it clarifies an approach
- Provide multiple options with trade-offs when appropriate
- Ask questions only if unresolved ambiguity materially changes the design

## Quality Standards

- Detailed enough for a junior Android developer to implement safely
- Validated against existing repo patterns
- Maintains long-term maintainability
- Addresses security, privacy, performance, and testability
- Researches uncertain areas before locking in a direction

**Remember:** Plan quality determines implementation success.
