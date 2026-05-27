# CLAUDE.md

This file defines the engineering rules for building and modifying the BazarBooks Android app. Follow these rules for every code change unless the user explicitly asks for a different approach.

## Project Shape

- Main Android project: `BazarBooks/`
- App entry point: `BazarBooks/app`
- Shared modules:
  - `core:common`: MVI base classes, validation helpers, common utilities.
  - `core:domain`: pure Kotlin domain models, repository interfaces, and use cases.
  - `core:data`: repository implementations, local/remote data sources, Room, DataStore.
  - `core:designsystem`: reusable Compose components, Material 3 theme, typography, colors, icons.
  - `core:navigation`: route definitions and navigation contracts.
- Feature modules live under `BazarBooks/feature/<feature-name>`.

## Development Commands

Run commands from `BazarBooks/`.

```bash
./gradlew :app:compileDebugKotlin
./gradlew :feature:<feature-name>:compileDebugKotlin
./gradlew test
./gradlew lint
```

Prefer compiling the smallest touched module first, then compile `:app:compileDebugKotlin` when app wiring, navigation, DI, domain contracts, or shared modules change.

## Architecture Rules

Use Feature-per-Module + Clean Architecture + MVI.

Dependency direction must remain one-way:

```text
app -> feature:* -> core:designsystem
               -> core:common
               -> core:domain

core:data -> core:domain
app -> core:data only for dependency graph composition
```

Do not let feature modules depend on `core:data`. Features depend on domain use cases or repository interfaces only. Data implementations are bound in Hilt modules outside feature UI code.

## Feature Module Structure

Each feature should be organized by responsibility:

```text
feature/<name>/src/main/kotlin/.../feature/<name>/
  <Feature>Route.kt          # Route-level composable, ViewModel hookup, effect collection
  <Feature>Screen.kt         # Stateless screen UI
  <Feature>Contract.kt       # State, Intent, Effect
  <Feature>ViewModel.kt      # MVI reducer and business orchestration
  components/                # Feature-private reusable composables
  model/                     # UI models only, if needed
  navigation/                # Feature navigation entry, if needed
```

For small features, `Contract.kt` and `ViewModel.kt` may stay together. Split files once readability suffers.

## MVI Pattern

All feature screens use the standardized MVI architecture:

- `BaseMviViewModel<State, Intent, Effect>`
- `StateFlow` for observable UI state
- `Channel`/effect flow for one-time effects such as navigation, snackbars, toasts, permission prompts, or dialogs
- immutable `State`
- sealed `Intent`
- sealed `Effect`

Use this shape:

```kotlin
data class ExampleState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val items: List<ItemUiModel> = emptyList()
) : MviState

sealed interface ExampleIntent : MviIntent {
    data object Started : ExampleIntent
    data class QueryChanged(val value: String) : ExampleIntent
    data object RetryClicked : ExampleIntent
}

sealed interface ExampleEffect : MviEffect {
    data object NavigateBack : ExampleEffect
    data class ShowMessage(val message: String) : ExampleEffect
}
```

ViewModel rules:

- ViewModels own screen state, validation, loading state, error state, and orchestration.
- ViewModels call domain use cases, not data repositories directly unless no use case exists yet.
- Use intents for every user action and lifecycle event that changes state.
- Use effects for one-time events only. Do not store navigation events in `State`.
- Keep reducers explicit and small.
- Validate inputs before launching work.
- Expose user-safe error messages in `State.errorMessage`.
- Clear stale errors when related input changes.
- Avoid Compose imports inside ViewModels and contracts.

## Clean Architecture Rules

Domain layer:

- `core:domain` is pure Kotlin.
- Put repository interfaces in `core.domain.repository`.
- Put use cases in `core.domain.usecase`.
- Put shared domain models in `core.domain.model`.
- Domain must not depend on Android, Compose, Room, Retrofit, Hilt, or DataStore.

Data layer:

- `core:data` implements domain repository interfaces.
- Room entities, DAO classes, DataStore keys, DTOs, and mappers stay in `core:data`.
- Convert data models to domain models before returning from repositories.
- Keep fake repositories in `core:data` or test sources, not in feature modules.

Feature layer:

- Feature modules contain UI, UI models, MVI contracts, and ViewModels.
- Feature modules should call domain use cases.
- Feature modules must not know Room entities, DataStore preferences, DTOs, or concrete repository implementations.

App layer:

- `app` owns app startup, top-level navigation, and dependency graph composition.
- With Hilt, bind repository implementations and provide use cases from DI modules.
- Do not put feature business logic in `MainActivity`.

## Jetpack Compose Rules

- Prefer stateless composables for actual UI.
- Use route/container composables only for ViewModel creation, state collection, and effect handling.
- Collect state with lifecycle-aware APIs when available, such as `collectAsStateWithLifecycle`.
- Pass immutable state and event lambdas down the tree.
- Do not launch repository or use case calls directly from composables.
- Use `LaunchedEffect(viewModel)` to collect one-time effects.
- Keep UI state hoisting clear: screen state comes from MVI state, local `remember` is only for transient visual-only state.
- Use Material 3 components and tokens from `core:designsystem`.
- Reuse design-system components before creating feature-local components.
- Do not hardcode colors when a design-system token exists.
- Provide content descriptions for meaningful icons and images. Decorative images should use `contentDescription = null`.
- Use `LazyColumn`/`LazyVerticalGrid` for scrollable lists, not manually composed large columns.
- Keep previews lightweight and pass fake UI state, not repositories.

Recommended screen shape:

```kotlin
@Composable
fun ExampleRoute(
    viewModel: ExampleViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel) {
        viewModel.effect.collect { effect ->
            when (effect) {
                ExampleEffect.NavigateBack -> onBack()
                is ExampleEffect.ShowMessage -> { /* show snackbar/toast */ }
            }
        }
    }

    ExampleScreen(
        state = state,
        onIntent = viewModel::onIntent
    )
}
```

## Dependency Injection With Hilt

- Use Hilt for ViewModels and dependency graph composition.
- Annotate ViewModels with `@HiltViewModel`.
- Inject use cases into ViewModels with constructor injection.
- Provide repository implementations in `core:data` DI modules.
- Provide use cases in domain/app DI modules when constructor injection is not enough.
- Use `hiltViewModel()` only in route-level composables.
- Do not manually create ViewModels in composables once Hilt is available.

## Navigation Compose Rules

- Use Jetpack Navigation Compose.
- Prefer type-safe navigation routes over raw string concatenation.
- Define route contracts in `core:navigation` or feature navigation files.
- Keep navigation calls at route/app composition boundaries.
- ViewModels emit navigation effects; composables translate effects into `navController` calls.
- Do not pass `NavController` into screen composables or ViewModels.
- For arguments, use typed route objects/classes and explicit serialization-safe fields.

## Persistence Rules

Room:

- Use Room for structured relational local data.
- Keep entities and DAOs in `core:data`.
- Keep migrations explicit.
- Never expose Room entities outside `core:data`.

DataStore:

- Use DataStore for preferences, onboarding state, auth/session flags, and small settings.
- Keep DataStore keys private to `core:data`.
- Expose domain-friendly flows through repositories.

## Images With Coil

- Use Coil Compose for remote images.
- Prefer a shared image component in `core:designsystem` for placeholders, loading state, errors, and clipping.
- Do not load network images manually in composables.
- Keep image URLs in domain/UI models; transformation and presentation belong in UI components.

## Validation And Errors

- Put reusable validation helpers in `core:common`.
- ViewModels perform validation before calling use cases.
- State contains field values, field-level errors when needed, `isLoading`, and `errorMessage`.
- Effects may emit `ShowMessage` for transient feedback.
- Do not show raw exception text directly if it may be technical or unsafe.

## Testing Rules

Prioritize tests around behavior:

- ViewModel tests for reducers, validation, loading, success, failure, and effects.
- Use case tests for domain behavior.
- Repository tests for mapping and persistence behavior.
- Compose UI tests for critical flows and regressions.

Prefer testing MVI intents and resulting state/effects rather than implementation details.

## Coding Standards

- Keep changes scoped to the requested feature.
- Prefer existing project patterns over introducing new abstractions.
- Use Kotlin idioms, immutable data classes, sealed interfaces, and clear names.
- Avoid large composable files. Split screens, contracts, components, and UI models when files become hard to scan.
- Do not duplicate UI components across features if they belong in the design system.
- Keep generated build outputs out of commits.
- Do not modify unrelated feature modules during a focused change.

## When Adding A New Feature

1. Add `:feature:<name>` to `settings.gradle.kts`.
2. Create feature module Gradle config.
3. Add state, intents, effects, ViewModel, route, and stateless screen.
4. Add domain use cases and repository methods if needed.
5. Implement repository behavior in `core:data`.
6. Add DI bindings/providers.
7. Add typed navigation route.
8. Compile the feature module, then compile `:app`.
9. Add focused tests for ViewModel behavior and domain logic.

