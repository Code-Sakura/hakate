package net.kigawa.hakate.impl.state

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import net.kigawa.hakate.api.state.StateContext
import net.kigawa.hakate.api.state.StateDispatcher
import net.kigawa.hakate.impl.Utl.suspendApply

class StateContextImpl(
    private val dispatcher: StateDispatcher,
    override val coroutineScope: CoroutineScope,
) : StateContext {
    override fun dispatcher(): StateDispatcher = dispatcher
    override fun dispatch(
        block: suspend StateContext.() -> Unit,
    ): Job {
        return coroutineScope.launch {
            StateContextImpl(dispatcher, this@launch).suspendApply {
                block()
            }
        }
    }

    override fun cancel() {
        coroutineScope.cancel("cancel by StateContext")
    }
}