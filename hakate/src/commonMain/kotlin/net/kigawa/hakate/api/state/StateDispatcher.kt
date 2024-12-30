package net.kigawa.hakate.api.state

import kotlinx.coroutines.Job

interface StateDispatcher {
    fun <T> newState(defaultValue: T): State<T>
    fun <T> newState(): State<T?> = newState(null)
    fun <T> currentValue(state: State<T>): T
    fun <T> useState(block: suspend StateContext.() -> T): Job
}