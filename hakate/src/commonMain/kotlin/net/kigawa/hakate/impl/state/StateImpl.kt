package net.kigawa.hakate.impl.state

import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import net.kigawa.hakate.api.state.MutableState
import net.kigawa.hakate.api.state.State
import net.kigawa.hakate.api.state.StateContext

class StateImpl<T>(
    defaultValue: T,
    override val stateContext: StateContext,
) : MutableState<T> {
    private val flow = MutableStateFlow<T>(defaultValue)
    override fun collect(
        context: StateContext,
        block: suspend StateContext.(T) -> Unit,
    ): Job {
        return context.dispatch {
            flow.collect { block(it) }
        }
    }


    override fun <R> child(
        defaultValue: R, block: suspend StateContext.(T, R) -> R,
    ): State<R> {
        val newState = StateImpl(defaultValue, stateContext)
        collect(stateContext) {
            newState.set(block(it, newState.flow.value))
        }
        return newState
    }


    override fun <R> child(block: (T) -> R): State<R> {
        val newState = StateImpl(block(flow.value), stateContext)
        collect(stateContext) {
            newState.set(block(it))
        }
        return newState
    }

    override fun set(value: T) {
        flow.value = value
    }

    override fun currentValue(): T = flow.value
    override fun <U, R> merge(
        state: State<U>, block: (T, U) -> R,
    ): State<R> {
        val mergedContext = stateContext.merge(state.stateContext)
        val newState = StateImpl(block(currentValue(), state.currentValue()), mergedContext)
        collect(mergedContext) { v1 ->
            state.collect(mergedContext) { v2 ->
                newState.set(block(v1, v2))
            }
        }
        return newState
    }

}