# Research and Analysis Phase

**When to skip:** If researcher reports already exist or the task is small and clear, skip this phase.

## Core Activities

### Parallel Researcher Agents
- Spawn multiple `explore` researchers in parallel when the topic has multiple solution paths
- Wait for all researcher outputs before synthesizing
- Assign each researcher a distinct concern: Android/Jetpack API guidance, third-party library viability, migration risk, or testing approach

### Structured Analysis
- Break down complex Android architecture decisions systematically
- Evaluate UI, navigation, async, persistence, and dependency choices with pros and cons
- Compare solutions based on compatibility with the repo, platform constraints, and maintenance cost

### Android Documentation Research
- Check Android Developer Documentation, Android Dev Summit/Google I/O sessions, Kotlin Evolution/KEEP proposals, and Android Studio release notes
- Review library docs, changelogs, and migration guides for Gradle dependencies (Maven Central, Google Maven)
- Read `.agents/docs/` project-specific guidance if present

### GitHub Analysis
- Use `gh` to inspect issues, PRs, and release context when needed
- Review maintained reference implementations for similar Android patterns
- Use `webfetch` to read official documentation and relevant repository pages

### Debugging Support
- Delegate root-cause work when planning around bugs or regressions
- Use build logs, test failures, and existing crash information (Logcat, stacktraces) as inputs
- Consider Android Profiler, Layout Inspector, StrictMode, and LeakCanary when relevant

## Best Practices

- Research breadth before depth
- Document findings for synthesis
- Compare at least two approaches when the choice is architectural
- Note edge cases, privacy impact, and rollout risk early
- Prefer stable, maintained approaches that fit the repo
