package net.kigawa.hakate.api.state

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import net.kigawa.hakate.impl.state.MergedStateContextImpl
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * Interface for managing state and coroutine operations in a structured way.
 * Provides methods for launching coroutines, dispatching operations, and collecting state changes.
 */
interface StateContext {
    /**
     * The coroutine scope associated with this state context.
     * Used for launching coroutines and managing their lifecycle.
     */
    val coroutineScope: CoroutineScope

    /**
     * Launches a new coroutine within the context's scope.
     *
     * @param block The suspend function to execute in the coroutine.
     * @return A [Job] that can be used to control the launched coroutine.
     */
    fun launch(block: suspend CoroutineScope.() -> Unit): Job

    /**
     * Creates a new state dispatcher associated with this context.
     *
     * @return A new [StateDispatcher] instance.
     */
    fun dispatcher(): StateDispatcher

    /**
     * Dispatches a block of code to be executed in a new state context.
     *
     * @param block The suspend function to execute in the new context.
     * @return A [Job] that can be used to control the launched coroutine.
     */
    fun dispatch(block: suspend StateContext.() -> Unit): Job

    /**
     * Extension function to collect state changes with an accumulator.
     *
     * @param T The type of the state value.
     * @param R The type of the accumulated result.
     * @param defaultValue The initial value for the accumulator.
     * @param block The function to execute for each state change, receiving the new value and previous accumulated result.
     * @return The result of the collection operation.
     */
    fun <T, R> State<T>.collect(defaultValue: R, block: suspend StateContext.(value: T, prev: R) -> R) =
        this.collect(this@StateContext, defaultValue, block)

    /**
     * Extension function to collect state changes.
     *
     * @param T The type of the state value.
     * @param block The function to execute for each state change.
     * @return The result of the collection operation.
     */
    fun <T> State<T>.collect(block: suspend StateContext.(value: T) -> Unit) =
        this.collect(this@StateContext, block)

    /**
     * Cancels all coroutines associated with this context.
     */
    fun cancel()

    /**
     * Merges this context with another context.
     *
     * @param other The context to merge with.
     * @return A new [StateContext] that combines the behavior of both contexts.
     */
    fun merge(other: StateContext): StateContext {
        return MergedStateContextImpl(this, other)
    }

    /**
     * Creates a new state context with the specified coroutine context.
     *
     * @param coroutineContext The coroutine context to use for the new state context.
     * @return A new [StateContext] with the specified coroutine context.
     */
    fun withCoroutineContext(coroutineContext: CoroutineContext): StateContext

    /**
     * Creates a new state context with an empty coroutine context.
     *
     * @return A new [StateContext] with an empty coroutine context.
     */
    fun newStateContext(): StateContext = withCoroutineContext(EmptyCoroutineContext)
}
