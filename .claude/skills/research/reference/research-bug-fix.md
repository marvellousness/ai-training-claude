# Research Bug Fix

## Research Methodology

Always honor **YAGNI**, **KISS**, and **DRY**.
Be honest, direct, concise.

### Phase 1: Bug Analysis

Define the bug clearly by:
- Identifying the exact error, exception, or visible failure
- Determining affected Kotlin, Android Studio, Android API level, and dependency versions
- Noting whether it reproduces on emulator, physical device, or both
- Capturing stack traces, crash logs, Logcat output, or screenshots when relevant

### Phase 2: Targeted Information Gathering

**Priority Order:**
1. **Android Documentation and Issue Tracker (issuetracker.google.com)**
2. **GitHub Issues and PRs** for relevant libraries or SDK wrappers
3. **Stack Overflow and community write-ups**
4. **Library source code or changelogs**

**Parallel Subagent Research:**
- Spawn up to **2** `explore` subagents
- One agent focuses on official sources and issue trackers
- Another focuses on community solutions and workarounds
- Synthesize findings into one concise report

**Search Strategy:**
- Use web search with specific error text, class names, framework names, and API levels
- Include terms like `Kotlin`, `Android`, `Android Studio`, `crash`, `workaround`, `fixed`, `release notes`
- Search exact error messages and exception types whenever possible
- **IMPORTANT:** Perform at most **5 research tool calls**

**Query Examples:**
- `Kotlin [error message] Android Developer`
- `Android [framework] crash API [level]`
- `[library name] Android issue [feature] GitHub`
- `[class or API] deprecated migration Android`

### Phase 3: Source Evaluation

Prioritize sources like this:
1. **Android official docs or release notes**
2. **Google Issue Tracker with reproducible detail**
3. **Maintained library repo issues and merged PRs**
4. **Community posts** with clear version and API level context

### Phase 4: Solution Validation

Validate each candidate solution by:
- Checking that it applies to the project's Kotlin, Android Studio, and target/min API levels
- Verifying emulator vs physical device behavior differences
- Looking for permission, lifecycle, threading, or ProGuard/R8 caveats
- Preferring the smallest safe fix over speculative workarounds

### Phase 5: Report Generation

**Notes:**
- If input includes a `.agents/current-work/[folder-name]/` context -> Save report to `.agents/current-work/[folder-name]/research-report.md`.
- If no `current-work` context is provided -> report the output but do not save a file.

Create a concise markdown report:

```markdown
# Bug Research Report: [Error or Bug Title]

## Bug Summary
- **Error Message**: [exact error]
- **Kotlin/Android Studio**: [versions]
- **Android API Level**: [min and target]
- **Environment**: [emulator, device, both]
- **Dependency**: [if applicable]

## Root Cause Analysis
[What likely causes the bug based on research]

## Solutions Found

### 1. [Recommended Solution]
- **Source**: [Android docs, Issue Tracker, GitHub, etc.]
- **URL**: [link]
- **Status**: [accepted, merged, fixed, workaround]
- **Fix**: [code snippet or steps]
- **Applicability**: [API level or environment requirements]

### 2. [Alternative Solution]
- [Same structure]

## Workarounds
[Temporary options if a permanent fix is unavailable]

## Prevention
[How to avoid this class of bug later]

## References
- [Link 1]
- [Link 2]
```

## Quality Standards

- **Accuracy**: Verify solutions against official or primary sources
- **Currency**: Prefer recent fixes and version-specific guidance
- **Completeness**: Cover root cause and multiple options when available
- **Actionability**: Provide code or steps that can be applied directly
- **Clarity**: Show before/after or exact setup constraints when useful

## Special Considerations

- Always check whether the issue is fixed in a newer SDK, dependency, or Android Studio release
- Note trade-offs of workarounds
- Check for breaking changes in the recommended fix
- Document emulator-only, device-only, or API-level-specific behavior
- Note any impacts from ProGuard/R8 minification or build variant differences

## Output Requirements

1. Save report to `.agents/current-work/[folder-name]/research-report.md` when in `current-work`
2. Include the exact error message in the title when practical
3. Provide code snippets with language highlighting
4. List solutions by priority, recommended first
5. Note unresolved questions or known limitations
