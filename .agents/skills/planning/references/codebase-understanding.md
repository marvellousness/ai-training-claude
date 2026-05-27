# Codebase Understanding Phase

**When to skip:** If scout reports already provide enough context, skip this phase.

## Core Activities

### Parallel Scout Agents
- Use `/scout` or the `explore` agent to locate files needed for the task
- Split searches by app code, shared modules, feature modules, tests, and config
- Wait for all scouting results before committing to a design

### Essential Documentation Review
- Read repo guidance such as `AGENTS.md`
- Read `.agents/docs/` project-specific rules if present and relevant
- Check style and tooling docs before planning format-sensitive work

### Android Project Analysis
- Review root `settings.gradle.kts` and `build.gradle.kts` for module structure, plugins, and build configuration
- Review feature module `build.gradle.kts` files for dependencies and Android config
- Check `gradle.properties`, `libs.versions.toml` (version catalog), and any convention plugins
- Review `AndroidManifest.xml`, ProGuard/R8 rules, resource directories, and signing configs when relevant
- Check Detekt/ktlint configuration and any CI/build scripts

### Project Structure Analysis
- Identify architecture pattern: Clean Architecture, MVVM, MVI, multi-module structure, navigation graph usage
- Map app module, feature modules, core modules, and shared library modules (e.g., `:domain`, `:data`, `:network`, `:database`)
- Review test target structure and existing test conventions (JUnit, Espresso, Robolectric, Turbine)
- Identify localization, theming (Material 3), navigation, persistence (Room/DataStore), and networking (Retrofit/Ktor) patterns

### Pattern Recognition
- Study surrounding code before proposing new structure
- Identify how state, coroutines/Flow, dependency injection (Hilt/Dagger/Koin), and error handling are done now
- Note inconsistencies and technical debt only when they materially affect the plan

### Integration Planning
- Identify how the new work fits existing modules and build variants
- Map dependencies between domain, data, and presentation layers
- Consider backward compatibility and rollout risk

## Best Practices

- Start with repo-level docs and project configuration before diving deep into source files
- Use scouts for targeted discovery
- Document patterns found so the plan stays consistent with the codebase
- Call out architecture constraints early
