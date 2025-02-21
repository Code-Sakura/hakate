package net.kigawa.hakate.api.state

import kotlinx.coroutines.Job

interface StateContext {
    fun dispatcher(): StateDispatcher
    fun dispatch(block: suspend StateContext.() -> Unit): Job
    fun <T, R> State<T>.collect(defaultValue: R, block: suspend StateContext.(value: T, prev: R) -> R) =
        this.collect(this@StateContext, defaultValue, block)

    fun <T> State<T>.collect(block: suspend StateContext.(value: T) -> Unit) =
        this.collect(this@StateContext, block)

}