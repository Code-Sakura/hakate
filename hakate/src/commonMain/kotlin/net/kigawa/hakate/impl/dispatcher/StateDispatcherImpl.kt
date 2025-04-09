package net.kigawa.hakate.impl.dispatcher

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import net.kigawa.hakate.api.state.MutableState
import net.kigawa.hakate.api.state.State
import net.kigawa.hakate.api.state.StateContext
import net.kigawa.hakate.api.state.StateDispatcher
import net.kigawa.hakate.impl.state.StateContextImpl
import net.kigawa.hakate.impl.state.StateImpl

class StateDispatcherImpl(
    private val coroutineScope: CoroutineScope,
) : StateDispatcher {
    override fun <T> newState(defaultValue: T): MutableState<T> {
        return StateImpl(defaultValue, newStateContext())
    }

    override fun <T> currentValue(state: State<T>): T {
        return state.currentValue()
    }

    override fun <T> useState(block: suspend StateContext.() -> T): Job {
        return newStateContext().dispatch { block() }
    }

    override fun newStateContext(): StateContext {
        return StateContextImpl(this@StateDispatcherImpl, coroutineScope)
    }
}