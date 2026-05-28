package nova.publish.bazarbooks

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import androidx.lifecycle.viewModelScope
import nova.publish.bazarbooks.core.domain.usecase.auth.ObserveOnboardingCompletedUseCase
import nova.publish.bazarbooks.core.domain.usecase.auth.ObserveSessionUseCase
import nova.publish.bazarbooks.core.navigation.AppRoute

data class AppStartupState(
    val isLoading: Boolean = true,
    val startDestination: String = AppRoute.Onboarding.route,
)

@HiltViewModel
class AppStartupViewModel @Inject constructor(
    observeOnboardingCompletedUseCase: ObserveOnboardingCompletedUseCase,
    observeSessionUseCase: ObserveSessionUseCase,
) : ViewModel() {
    val state = combine(
        observeOnboardingCompletedUseCase(),
        observeSessionUseCase(),
    ) { onboardingCompleted, sessionActive ->
        AppStartupState(
            isLoading = false,
            startDestination = when {
                !onboardingCompleted -> AppRoute.Onboarding.route
                sessionActive -> AppRoute.Home.route
                else -> AppRoute.SignIn.route
            },
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = AppStartupState(),
    )
}
