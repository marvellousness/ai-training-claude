# Research

## Research Methodology

Always honor **YAGNI**, **KISS**, and **DRY**.
Be honest, direct, concise.

### Phase 1: Scope Definition

Define the research scope by:
- Identifying the Android frameworks, Jetpack APIs, Kotlin language features, or third-party libraries involved
- Determining recency requirements: SDK API level, Android Studio version, min/target SDK, package version
- Establishing evaluation criteria for sources
- Setting boundaries for research depth

### Phase 2: Systematic Information Gathering

Use a multi-source strategy:

1. **Parallel Subagent Research**
   - Spawn up to **2** `explore` subagents to investigate different aspects of the topic
   - Each subagent should cover a distinct angle, such as official API guidance vs community adoption
   - Synthesize findings into one cohesive report

2. **Search Strategy**
   - Use web search and web fetch tools deliberately
   - Search precise terms including framework name, Android API level, Android Studio version, migration, security, performance, and best practices
   - Prioritize Android Developer Documentation, Google I/O sessions, Kotlin Evolution, Android Studio release notes, GitHub repositories, and maintained package docs
   - **IMPORTANT:** Perform at most **5 research tool calls** unless the user explicitly narrows or expands scope

3. **Deep Content Analysis**
   - When a source looks promising, read the official docs or repository pages directly
   - Focus on API references, migration guides, release notes, permission requirements, and deployment constraints
   - Check compatibility with Gradle, Android Studio, and minimum API levels

4. **Cross-Reference Validation**
   - Verify claims across multiple independent sources
   - Check publication dates and SDK relevance
   - Separate stable recommendations from speculative or outdated advice
   - Note conflicting guidance explicitly

### Phase 3: Analysis and Synthesis

Analyze gathered information by:
- Identifying common patterns and Google-preferred practices
- Evaluating pros and cons of different approaches
- Assessing API maturity, availability, and maintenance status
- Recognizing security, privacy, and Play Store review implications
- Determining integration requirements for Kotlin, Jetpack Compose, View system, modules, and build tooling

### Phase 4: Report Generation

**Notes:**
- If input includes a `.agents/current-work/[folder-name]/` context -> Save research report to `.agents/current-work/[folder-name]/research-report.md`.
- If no `current-work` context is provided -> report the output but do not save a file.

Create a markdown report with this structure:

```markdown
# Research Report: [Topic]

## Executive Summary
[2-3 short paragraphs with the main findings and recommendation]

## Research Methodology
- Sources consulted: [number]
- Date range of materials: [earliest to most recent]
- Key search terms used: [list]

## Key Findings

### 1. Technology Overview
[What the framework, API, or library does]

### 2. Current State and Compatibility
[Latest SDK version, API level constraints, Gradle support, migration notes]

### 3. Best Practices
[Recommended usage patterns with explanation]

### 4. Security and Privacy Considerations
[Permissions, data handling, secrets management, Play Store policy impact]

### 5. Performance Insights
[Runtime cost, memory, rendering, networking, startup, or battery considerations]

## Comparative Analysis
[If applicable, compare approaches or libraries]

## Implementation Recommendations

### Quick Start Guide
[How to adopt safely in an Android project]

### Code Examples
[Relevant Kotlin snippets]

### Common Pitfalls
[Mistakes to avoid]

## Resources and References

### Official Documentation
- [Links]

### Release Notes and Sessions
- [Google I/O, Android Studio, SDK release notes]

### Community Resources
- [Android Developer Forums, issue tracker, maintained guides]

## Appendices

### A. Glossary
[Terms and definitions]

### B. Compatibility Matrix
[If applicable]

### C. Raw Research Notes
[Optional]
```

## Quality Standards

Ensure the research is:
- **Accurate** - verified across multiple sources
- **Current** - prefer information from the last 12 months unless historical context matters
- **Complete** - cover all requested aspects
- **Actionable** - recommend concrete next steps
- **Clear** - define terms and give examples
- **Attributed** - cite sources and link them

## Special Considerations

- For security topics, check recent advisories and Android platform guidance
- For performance topics, prioritize benchmarks, Android Profiler guidance, and real-world reports
- For new technologies, assess adoption, maintenance, and migration cost
- For Android APIs, verify platform availability, permissions, and API level requirements
- Always note deprecations and migration paths (e.g. View system → Jetpack Compose)

## Output Requirements

Your final report must:
1. Be saved to `.agents/current-work/[folder-name]/research-report.md` when in `current-work`
2. Include a timestamp for when the research was done
3. Use clear headings and a table of contents for longer reports
4. Use code blocks with syntax highlighting
5. Include architecture descriptions when helpful
6. Conclude with specific next steps

**IMPORTANT**: Sacrifice grammar for concision when writing reports.
**IMPORTANT**: List unresolved questions at the end if any.
