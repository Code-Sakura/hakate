package net.kigawa.hakate.api.state

import net.kigawa.hakate.api.coroutine.CoroutineWrapper

interface StateDispatcher {
    fun coroutine(): CoroutineWrapper
    fun <T> newState(defaultValue: T): State<T>
    fun <T> newState(): State<T?> = newState(null)
}