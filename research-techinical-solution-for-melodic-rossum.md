# Technical Solution Plan for Bazar Books E-commerce Android App

## Context
Based on the UI designs provided in the `ui-design/` folder, we need to implement an e-commerce Android app for Bazar Books. The designs include: home screen, onboarding, signin & signup, forgot password, profile, notification, order-status, and cart & checkout. The app must follow the architecture rules defined in CLAUDE.md: Feature-per-Module + Clean Architecture + MVI, using Jetpack Compose, Hilt for DI, and adhering to dependency direction rules.

## Executive Summary
We will structure the app into feature modules corresponding to major user flows: onboarding, authentication, home, profile, notifications, orders, and cart/checkout. Each feature will follow the standardized MVI pattern with stateless composables, ViewModels handling business logic, and domain layer containing pure Kotlin use cases and repository interfaces. Data layer will implement repositories using Room for local data and Retrofit for network calls. Navigation will use Jetpack Navigation Compose with type-safe routes.

## Implementation Approach

### 1. Project Structure
```
BazarBooks/
├ app/
├ core/
│ ├ common/          # MVI base classes, validation helpers
│ ├ domain/          # Pure Kotlin: models, repository interfaces, use cases
│ ├ data/            # Repository implementations, Room, DataStore, DTOs, mappers
│ ├ designsystem/    # Reusable Compose components, Material 3 theme, typography, colors, icons
│ └ navigation/      # Route definitions and navigation contracts
└ feature/
   ├ onboarding/
   ├ auth/
   ├ home/
   ├ profile/
   ├ notifications/
   ├ orders/
   └ cart_checkout/
```

### 2. Core Modules
- **core:common**: 
  - `BaseMviViewModel<State, Intent, Effect>` abstract class
  - Validation helpers (email, password, etc.)
  - Common utilities (extensions, constants)
- **core:domain**:
  - `model`: User, Book, CartItem, Order, etc.
  - `repository`: Interfaces for UserRepository, BookRepository, OrderRepository, CartRepository
  - `usecase`: Interactors like LoginUseCase, GetBooksUseCase, PlaceOrderUseCase
- **core:data**:
  - Implementations of repository interfaces using Room (local) and Retrofit (remote)
  - DataStore for auth tokens, onboarding state
  - Mappers to convert DTOs to domain models
- **core:designsystem**:
  - Theme (colors, typography, shapes) following Material 3
  - Reusable components: AppButton, AppTextField, AppBar, BookItem, LoadingIndicator, etc.
  - Icon set
- **core:navigation**:
  - Sealed interface `AppRoute` with navigation arguments
  - Navigation graph setup

### 3. Feature Modules (Example: Auth Feature)
Each feature follows:
```
feature/auth/
├ data/                 # Feature-private data sources if needed (but prefer core:data)
├ model/                # UI models only if needed
├ navigation/           # Auth-specific navigation entries
├ AuthRoute.kt          # Route-level composable, ViewModel hookup, effect collection
├ AuthScreen.kt         # Stateless screen UI
├ AuthContract.kt       # State, Intent, Effect
├ AuthViewModel.kt      # MVI reducer and business orchestration
└ components/           # Feature-private reusable composables (if any)
```

#### Contract Example (AuthContract.kt)
```kotlin
data class AuthState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val email: String = "",
    val password: String = "",
    val isEmailValid: Boolean = true,
    val isPasswordValid: Boolean = true
) : MviState

sealed interface AuthIntent : MviIntent {
    data object Started : AuthIntent
    data class EmailChanged(val value: String) : AuthIntent
    data class PasswordChanged(val value: String) : AuthIntent
    data object LoginClicked : AuthIntent
    data object NavigateToSignUp : AuthIntent
    data object ForgotPasswordClicked : AuthIntent
}

sealed interface AuthEffect : MviEffect {
    data object NavigateToHome : AuthEffect
    data object NavigateToSignUp : AuthEffect
    data object NavigateToForgotPassword : AuthEffect
    data class ShowMessage(val message: String) : AuthEffect
}
```

#### ViewModel Example (AuthViewModel.kt)
```kotlin
@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase
) : BaseMviViewModel<AuthState, AuthIntent, AuthEffect>() {

    init {
        state.value = AuthState()
    }

    override fun handleIntent(intent: AuthIntent) {
        when (intent) {
            is AuthIntent.EmailChanged -> {
                val newEmail = intent.value
                state.update { it.copy(email = newEmail, isEmailValid = validateEmailUseCase(newEmail)) }
            }
            is AuthIntent.PasswordChanged -> {
                val newPassword = intent.value
                state.update { it.copy(password = newPassword, isPasswordValid = validatePasswordUseCase(newPassword)) }
            }
            AuthIntent.LoginClicked -> {
                if (state.value.isEmailValid && state.value.isPasswordValid) {
                    state.update { it.copy(isLoading = true) }
                    viewModelScope.launch {
                        try {
                            loginUseCase.invoke(state.value.email, state.value.password)
                            emitEffect(AuthEffect.NavigateToHome)
                        } catch (e: Exception) {
                            emitEffect(AuthEffect.ShowMessage(e.message ?: "Login failed"))
                        } finally {
                            state.update { it.copy(isLoading = false) }
                        }
                    }
                }
            }
            AuthIntent.NavigateToSignUp -> emitEffect(AuthEffect.NavigateToSignUp)
            AuthIntent.ForgotPasswordClicked -> emitEffect(AuthEffect.NavigateToForgotPassword)
            AuthIntent.Started -> {}
        }
    }
}
```

#### Route Composable (AuthRoute.kt)
```kotlin
@Composable
fun AuthRoute(
    viewModel: AuthViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is AuthEffect.NavigateToHome -> { /* handled by nav controller */ }
                is AuthEffect.NavigateToSignUp -> { /* handled by nav controller */ }
                is AuthEffect.NavigateToForgotPassword -> { /* handled by nav controller */ }
                is AuthEffect.ShowMessage -> { /* show snackbar/toast */ }
            }
        }
    }

    AuthScreen(
        state = state,
        onIntent = viewModel::onIntent,
        onBack = onBack
    )
}
```

#### Screen Composable (AuthScreen.kt)
```kotlin
@Composable
fun AuthScreen(
    state: AuthState,
    onIntent: (AuthIntent) -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Welcome to Bazar Books",
            style = MaterialTheme.typography.h5
        )
        Spacer(modifier = Modifier.height(24.dp))
        OutlinedTextField(
            value = state.email,
            onValueChange = { onIntent(AuthIntent.EmailChanged(it)) },
            label = { Text("Email") },
            isError = !state.isEmailValid,
            modifier = Modifier.fillMaxWidth()
        )
        if (!state.isEmailValid && state.email.isNotEmpty()) {
            Text(
                text = "Invalid email",
                color = Color.Red,
                fontSize = 12.sp
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = state.password,
            onValueChange = { onIntent(AuthIntent.PasswordChanged(it)) },
            label = { Text("Password") },
            isError = !state.isPasswordValid,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = null
                )
            },
            trailingIcon = {
                Icon(
                    imageVector = if (state.password.isNotEmpty()) Icons.Default.Visible else Icons.Default.VisibilityOff,
                    contentDescription = null
                )
            },
            isPassword = true,
            modifier = Modifier.fillMaxWidth()
        )
        if (!state.isPasswordValid && state.password.isNotEmpty()) {
            Text(
                text = "Password must be at least 6 characters",
                color = Color.Red,
                fontSize = 12.sp
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = { onIntent(AuthIntent.LoginClicked) },
            enabled = state.isEmailValid && state.isPasswordValid,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp)
                )
            } else {
                Text("Login")
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Button(
            onClick = { onIntent(AuthIntent.NavigateToSignUp) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Don't have an account? Sign Up")
        }
        Button(
            onClick = { onIntent(AuthIntent.ForgotPasswordClicked) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Forgot Password?")
        }
    }
}
```

### 4. Navigation Setup
- Use `rememberNavController()` in `MainActivity.kt`
- NavHost with start destination as `OnboardingRoute`
- Type-safe navigation using `navArgument` and custom `NavTypeSerializer` for complex objects if needed

### 5. Dependency Injection (Hilt)
- `@Module` in `core:data` for providing Room database, Retrofit instances, repository implementations
- `@Module` in `core:domain` or `app` for providing use cases
- `@HiltViewModel` on all ViewModels
- `@Inject` constructors in ViewModels and repository implementations

### 6. Data Persistence
- **Room**: For books, cart, orders, user profile (if offline needed)
- **DataStore**: For authentication tokens, onboarding completion flag, user preferences
- **Network**: Retrofit with OkHttp for API calls (to be defined based on backend)

### 7. Image Loading
- Use Coil composables (`AsyncImage`) from `core:designsystem` shared component
- Placeholder and error handling built into shared component

### 8. Validation
- Reusable validation extensions in `core:common` (email, password, etc.)
- ViewModels call validation before triggering use cases

### 9. Error Handling
- Use cases throw exceptions with meaningful messages
- ViewModels catch exceptions and emit `ShowMessage` effects
- Avoid showing raw exception text; map to user-friendly messages

### 10. Testing Strategy
- Unit tests for ViewModels (use coroutines-test, Truth/MockK)
- Unit tests for use cases and repository mappers
- Instrumented tests for critical navigation flows
- Compose UI tests for screen composables (using `createComposeRule`)

### 11. Quick Start Guide for New Feature
1. Add `:feature:<name>` to `settings.gradle.kts`
2. Create Gradle module with `com.android.library` plugin
3. Implement domain use cases and repository methods if needed (update `core:domain` and `core:data`)
4. Create feature module structure as outlined
5. Add DI bindings in `core:data` and `app` modules
6. Define typed navigation route in `core:navigation`
7. Implement route-level composable, screen, contract, ViewModel
8. Connect to navigation graph
9. Write unit tests for ViewModel behavior

### 12. Specific Screens Implementation Notes
- **Onboarding**: Use ViewPager or horizontal LazyPageIndicator; store completion in DataStore
- **Home**: LazyColumn with book categories, banners, recommended books; use paging library if large dataset
- **Profile**: Show user info, orders list, settings; allow logout (clear DataStore)
- **Notifications**: Load from local/remote; mark as read
- **Order Status**: Show order details with timeline steps
- **Cart & Checkout**: Cart summary, address selection, payment integration placeholder, order confirmation

### 13. Adherence to CLAUDE.md Rules
- Dependency direction: app -> feature -> core:designsystem -> core:common -> core:domain; core:data -> core:domain; app -> core:data only for DI
- No feature depends on core:data
- MVI pattern strictly followed with immutable State, sealed Intent/Effect
- Stateless composables; state hoisted from ViewModel
- Use `collectAsStateWithLifecycle` for state collection
- Effects for one-time events (navigation, messages)
- Hilt for DI; ViewModels use `hiltViewModel()`
- Jetpack Navigation Compose with type-safe routes
- Room and DataStore as per persistence rules
- Coil for images via design system
- Validation in ViewModels before use cases
- Testing prioritized around ViewModel and use case behavior

### 14. Unresolved Questions
- Backend API details needed for Retrofit interfaces and DTOs
- Specific color palette and typography from designs (to be extracted into designsystem)
- Whether to implement pagination for book lists
- Payment gateway integration requirements
- Offline-first requirements for cart and orders

## Next Steps
1. Extract UI specifications (dimensions, colors, text) from the provided PNG designs into design system resources.
2. Define data models (User, Book, Cart, Order) based on expected API.
3. Set up core modules with base MVI classes and dependencies.
4. Implement onboarding feature as initial entry point.
5. Proceed with authentication flow (signin/signup, forgot password).
6. Implement home screen with book listings.
7. Continue with profile, notifications, orders, and cart/checkout features.
