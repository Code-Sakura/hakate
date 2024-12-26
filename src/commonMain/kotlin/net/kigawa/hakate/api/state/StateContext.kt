package net.kigawa.hakate.api.state

import kotlinx.coroutines.Job

interface StateContext {
    fun dispatcher(): StateDispatcher
    fun dispatch(block: suspend StateContext.() -> Unit): Job
    fun <T, R> State<T>.collect(defaultValue: R, block: StateContext.(value: T, prev: R) -> R) =
        this.collect(this@StateContext, defaultValue, block)

    fun <T, R> State<T>.collect(block: StateContext.(value: T, prev: R?) -> R) =
        this.collect(this@StateContext, null, block)

    fun <T> State<T>.collect(block: StateContext.(value: T) -> Unit) =
        this.collect(this@StateContext, null, { value, _ ->
            block(value)
            null
        })

    fun <T, R> State<T>.child(
        context: StateContext, defaultValue: R, block: StateContext.(T, prev: R) -> R,
    ): State<R> = this.child(this@StateContext, defaultValue, block)

    fun <T, R> State<T>.child(block: StateContext.(T, prev: R?) -> R): State<R?> =
        this.child(this@StateContext, null, block)
}