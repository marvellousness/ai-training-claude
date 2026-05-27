# Solution Design

## Core Principles

Follow these fundamentals:
- **YAGNI** - do not add functionality until needed
- **KISS** - prefer the simplest correct solution
- **DRY** - avoid duplication and parallel abstractions

## Design Activities

### Technical Trade-off Analysis
- UI approach: Jetpack Compose vs Android Views (XML) vs mixed integration
- Navigation strategy: Compose Navigation, Navigation Component with fragments, single-activity architecture, deep links
- Async model: Kotlin Coroutines/Flow vs RxJava/RxKotlin, matching the surrounding code
- Dependency injection pattern already used in the repo (Hilt, Dagger, Koin, manual)
- API client and module boundary usage across `:domain`, `:data`, `:network`, `:database`, and app/feature modules
- Persistence strategy: Room, DataStore, SharedPreferences, EncryptedSharedPreferences, file storage, or shared module code
- Code generation (KSP/KAPT) or annotation processing only if the repo already leans that way
- Evaluate short-term safety vs long-term maintainability

### Security Assessment
- Secure token and secret storage using EncryptedSharedPreferences, Android Keystore, or existing wrappers
- Network security config, certificate pinning, or ClearText traffic considerations when relevant
- Sensitive logging avoidance
- Input validation for text entry and deep link/intent data
- Biometric authentication (BiometricPrompt) integration when relevant
- Permissions, exported components, and content provider exposure implications

### Performance and Scalability
- Compose recomposition scope and stability cost
- View layout cost, RecyclerView/ViewHolder reuse, ConstraintLayout measurement overhead
- Main-thread work, background offloading, and Dispatchers usage
- Large list rendering (LazyColumn/LazyList), pagination (Paging 3), and caching
- Image loading (Coil/Glide), decoding, and memory behavior
- Coroutine/Flow lifecycle leaks or improper scope usage
- Startup time (App Startup library) and excessive dependency initialization

### Edge Cases and Failure Modes
- Activity/Fragment lifecycle, process death, and configuration changes
- Permission denial and restricted states
- Emulator vs device differences, API level fragmentation
- Network failures, timeouts, offline scenarios
- Data migration, stale caches, or persistence corruption
- Localization, RTL, font scaling, and accessibility edge cases
- Rapid repeated user actions or coroutine race conditions

### Architecture Design
- Keep Clean Architecture boundaries clear: domain contracts, data implementations, presentation layers
- Preserve repository and use-case separation
- Keep business logic out of Composables/Activities/Fragments when possible
- Design ViewModels, UseCases, or navigation controllers to fit nearby patterns
- Plan error handling and user-facing state transitions explicitly
- Plan test seams for network, persistence, and time-based behavior

## Best Practices

- Document design decisions and rationale
- Consider both technical and UX impact
- Design for observability, debugging, and future maintenance
- Plan narrow verification: `./gradlew build`, `./gradlew test`, `./gradlew connectedAndroidTest`, `detekt`/`ktlint` when relevant
- Respect existing modules, build variants, and deployment constraints
