package net.kigawa.hakate.impl.state

import kotlinx.coroutines.flow.MutableStateFlow
import net.kigawa.hakate.api.state.State
import net.kigawa.hakate.api.state.StateContext

class StateImpl<T>(
    defaultValue: T,
) : State<T> {
    private val flow = MutableStateFlow<T>(defaultValue)

    override fun set(value: T) {
        flow.value = value
    }

    override fun <R> StateContext.collect(defaultValue: R, block: StateContext.(T, R) -> R) {
        dispatch {
            var defaultValue = defaultValue
            flow.collect {
                defaultValue = block(it, defaultValue)
            }
        }
    }

    override fun <R> StateContext.child(defaultValue: R, block: StateContext.(T, R) -> R): State<R> {
        val state = dispatcher().newState(defaultValue)
        dispatch {
            collect(defaultValue) { value, prev ->
                val result = block(value, prev)
                state.set(result)
                result
            }
        }
        return state
    }

}