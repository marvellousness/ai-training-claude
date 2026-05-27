package nova.publish.bazarbooks.core.common.mvi

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update

abstract class BaseMviViewModel<S : MviState, I : MviIntent, E : MviEffect>(
    initialState: S,
) {
    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<S> = _state.asStateFlow()

    private val _effect = Channel<E>(Channel.BUFFERED)
    val effect: Flow<E> = _effect.receiveAsFlow()

    fun onIntent(intent: I) {
        handleIntent(intent)
    }

    protected abstract fun handleIntent(intent: I)

    protected fun reduce(reducer: (S) -> S) {
        _state.update(reducer)
    }

    protected suspend fun emitEffect(effect: E) {
        _effect.send(effect)
    }
}
