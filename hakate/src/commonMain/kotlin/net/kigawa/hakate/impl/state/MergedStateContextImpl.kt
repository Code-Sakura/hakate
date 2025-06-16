package net.kigawa.hakate.impl.state

import kotlinx.coroutines.*
import net.kigawa.hakate.api.state.StateContext
import net.kigawa.hakate.api.state.StateDispatcher
import net.kigawa.hakate.impl.Utl.suspendApply
import kotlin.coroutines.EmptyCoroutineContext

/**
 * Implementation of [StateContext] that merges the behavior of two state contexts.
 * Operations are typically performed on both contexts, with results combined appropriately.
 *
 * @property first The primary state context.
 * @property second The secondary state context to merge with the primary one.
 */
class MergedStateContextImpl(
    private val first: StateContext,
    private val second: StateContext,
) : StateContext {
    /**
     * The merged coroutine scope that combines contexts from both state contexts.
     * Uses the first context as a base and adds the second context's coroutine context.
     */
    override val coroutineScope: CoroutineScope = CoroutineScope(
        first.coroutineScope.newCoroutineContext(second.coroutineScope.coroutineContext)
    )

    /**
     * Launches a new coroutine that coordinates execution across both contexts.
     * The block is executed in a context that combines both state contexts' coroutine contexts.
     *
     * @param block The suspend function to execute in the coroutine.
     * @return A [Job] that can be used to control the launched coroutine.
     */
    override fun launch(block: suspend CoroutineScope.() -> Unit): Job {
        return first.launch {
            val f = this
            second.launch {
                val s = this
                f.launch { withContext(s.coroutineContext) { block() } }
            }
        }
    }

    /**
     * Creates a new state dispatcher associated with this context.
     * Uses the first context's dispatcher implementation.
     *
     * @return A [StateDispatcher] from the first context.
     */
    override fun dispatcher(): StateDispatcher = first.dispatcher()

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
            StateContextImpl(dispatcher(), this@launch).suspendApply {
                block()
            }
        }
    }

    /**
     * Cancels all coroutines associated with both contexts.
     * Calls cancel on both the first and second contexts.
     */
    override fun cancel() {
        first.cancel()
        second.cancel()
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
        return StateContextImpl(
            dispatcher(),
            CoroutineScope(coroutineScope.newCoroutineContext(coroutineContext))
        )
    }
}
