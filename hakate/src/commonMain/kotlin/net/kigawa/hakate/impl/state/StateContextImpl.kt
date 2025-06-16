package net.kigawa.hakate.impl.state

import kotlinx.coroutines.*
import net.kigawa.hakate.api.state.StateContext
import net.kigawa.hakate.api.state.StateDispatcher
import net.kigawa.hakate.impl.Utl.suspendApply
import net.kigawa.hakate.impl.dispatcher.StateDispatcherImpl
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * Standard implementation of the [StateContext] interface.
 * Manages coroutine operations and state dispatching.
 *
 * @property dispatcher The state dispatcher used for handling state operations.
 * @property coroutineScope The coroutine scope associated with this context.
 */
class StateContextImpl(
    private val dispatcher: StateDispatcher,
    override val coroutineScope: CoroutineScope,
) : StateContext {
    /**
     * Launches a new coroutine within this context's scope.
     * Delegates to the underlying coroutineScope's launch function.
     *
     * @param block The suspend function to execute in the coroutine.
     * @return A [Job] that can be used to control the launched coroutine.
     */
    override fun launch(block: suspend CoroutineScope.() -> Unit): Job {
        return coroutineScope.launch(block = block)
    }

    /**
     * Creates a new state dispatcher associated with this context.
     * Returns a new instance of [StateDispatcherImpl] with the current coroutine scope.
     *
     * @return A new [StateDispatcher] instance.
     */
    override fun dispatcher(): StateDispatcher = StateDispatcherImpl(coroutineScope)

    /**
     * Dispatches a block of code to be executed in a new state context.
     * Creates a new [StateContextImpl] within the launched coroutine to execute the block.
     *
     * @param block The suspend function to execute in the new context.
     * @return A [Job] that can be used to control the launched coroutine.
     */
    override fun dispatch(
        block: suspend StateContext.() -> Unit,
    ): Job {
        return launch {
            StateContextImpl(dispatcher, this@launch).suspendApply {
                block()
            }
        }
    }

    /**
     * Cancels all coroutines associated with this context.
     * Cancels the underlying coroutine scope with a specific cancellation message.
     */
    override fun cancel() {
        coroutineScope.cancel("cancel by StateContext")
    }

    /**
     * Creates a new state context with the specified coroutine context.
     * Returns a new [StateContextImpl] with a coroutine scope that combines the current scope's context
     * with the provided coroutine context.
     *
     * @param coroutineContext The coroutine context to use for the new state context.
     * @return A new [StateContext] with the specified coroutine context.
     */
    override fun withCoroutineContext(coroutineContext: CoroutineContext): StateContext {
        return StateContextImpl(dispatcher, CoroutineScope(coroutineScope.newCoroutineContext(coroutineContext)))
    }
}
