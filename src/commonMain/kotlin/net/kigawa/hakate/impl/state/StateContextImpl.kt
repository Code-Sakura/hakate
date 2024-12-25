package net.kigawa.hakate.impl.state

import kotlinx.coroutines.CoroutineScope
import net.kigawa.hakate.api.state.StateContext
import net.kigawa.hakate.api.state.StateDispatcher
import net.kigawa.hakate.impl.Utl.suspendApply

class StateContextImpl(
    private val dispatcher: StateDispatcher,
    private val scope: CoroutineScope,
) : StateContext {
    override fun dispatcher(): StateDispatcher = dispatcher

    override fun StateContext.dispatch(
        block: suspend StateContext.() -> Unit,
    ) {
        dispatcher.coroutine().launch(scope) {
            StateContextImpl(dispatcher, this).suspendApply {
                block()
            }
        }
    }
}