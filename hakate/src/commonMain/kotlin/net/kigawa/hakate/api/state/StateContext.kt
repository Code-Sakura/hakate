package net.kigawa.hakate.api.state

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import net.kigawa.hakate.impl.state.MergedStateContextImpl

interface StateContext {
    val coroutineScope: CoroutineScope
    fun launch(block: suspend CoroutineScope.() -> Unit): Job
    fun dispatcher(): StateDispatcher
    fun dispatch(block: suspend StateContext.() -> Unit): Job
    fun <T, R> State<T>.collect(defaultValue: R, block: suspend StateContext.(value: T, prev: R) -> R) =
        this.collect(this@StateContext, defaultValue, block)

    fun <T> State<T>.collect(block: suspend StateContext.(value: T) -> Unit) =
        this.collect(this@StateContext, block)

    fun cancel()
    fun merge(other: StateContext): StateContext {
        return MergedStateContextImpl(this, other)
    }
    fun newStateContext(): StateContext
}