package net.kigawa.hakate.api.state

import kotlinx.coroutines.Job

interface State<T> {
    fun collect(context: StateContext, block: suspend StateContext.(value: T) -> Unit): Job
    fun <R> collect(
        context: StateContext, defaultValue: R, block: suspend StateContext.(value: T, prev: R) -> R,
    ): Job {
        var prev = defaultValue
        return collect(context) {
            block(it, prev).also { prev = it }
        }
    }

    fun <R> child(defaultValue: R, block: suspend StateContext.(T, prev: R) -> R): State<R>
    fun <R> child(defaultValue: (T) -> R, block: suspend StateContext.(T, prev: R) -> R): State<R> {
        return child(defaultValue(currentValue()), block)
    }

    fun <R> child(block: (T) -> R): State<R>
    fun currentValue(): T
}
