package net.kigawa.hakate.impl.state

import kotlinx.coroutines.Job
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

    override fun currentValue(): T = flow.value

    override fun <R> collect(context: StateContext, defaultValue: R, block: StateContext.(T, R) -> R): Job {
        return context.dispatch {
            var defaultValue = defaultValue
            flow.collect {
                defaultValue = block(it, defaultValue)
            }
        }
    }

    override fun <R> child(context: StateContext, defaultValue: R, block: StateContext.(T, R) -> R): State<R> {
        val state = context.dispatcher().newState(defaultValue)
        context.dispatch {
            collect(defaultValue) { value, prev ->
                val result = block(value, prev)
                state.set(result)
                result
            }
        }
        return state
    }

    override fun <R> child(
        context: StateContext, defaultValue: (T) -> R,
        block: StateContext.(T, R) -> R,
    ): State<R> {
        return child(context, defaultValue(currentValue()), block)
    }

}