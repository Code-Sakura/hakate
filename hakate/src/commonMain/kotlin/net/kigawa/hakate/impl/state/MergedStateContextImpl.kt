package net.kigawa.hakate.impl.state

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.kigawa.hakate.api.state.StateContext
import net.kigawa.hakate.api.state.StateDispatcher
import net.kigawa.hakate.impl.Utl.suspendApply

class MergedStateContextImpl(
    private val first: StateContext,
    private val second: StateContext,
) : StateContext {
    override fun launch(block: suspend CoroutineScope.() -> Unit): Job {
        return first.launch {
            val f = this
            second.launch {
                val s = this
                f.launch { withContext(s.coroutineContext) { block() } }
            }
        }
    }

    override fun dispatcher(): StateDispatcher = first.dispatcher()
    override fun dispatch(
        block: suspend StateContext.() -> Unit,
    ): Job {
        return launch {
            StateContextImpl(dispatcher(), this@launch).suspendApply {
                block()
            }
        }
    }

    override fun cancel() {
        first.cancel()
        second.cancel()
    }
}