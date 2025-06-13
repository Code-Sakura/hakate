package net.kigawa.hakate.impl.state

import kotlinx.coroutines.*
import net.kigawa.hakate.api.state.StateContext
import net.kigawa.hakate.api.state.StateDispatcher
import net.kigawa.hakate.impl.Utl.suspendApply
import net.kigawa.hakate.impl.dispatcher.StateDispatcherImpl
import kotlin.coroutines.EmptyCoroutineContext

class StateContextImpl(
    private val dispatcher: StateDispatcher,
    override val coroutineScope: CoroutineScope,
) : StateContext {
    override fun launch(block: suspend CoroutineScope.() -> Unit): Job {
        return coroutineScope.launch(block = block)
    }

    override fun dispatcher(): StateDispatcher = StateDispatcherImpl(coroutineScope)
    override fun dispatch(
        block: suspend StateContext.() -> Unit,
    ): Job {
        return launch {
            StateContextImpl(dispatcher, this@launch).suspendApply {
                block()
            }
        }
    }

    override fun cancel() {
        coroutineScope.cancel("cancel by StateContext")
    }

    override fun newStateContext(): StateContext {
        return StateContextImpl(dispatcher, CoroutineScope(coroutineScope.newCoroutineContext(EmptyCoroutineContext)))
    }
}