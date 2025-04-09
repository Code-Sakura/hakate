package net.kigawa.hakate.api.state

import kotlinx.coroutines.Job

interface StateDispatcher {
    fun <T> newState(defaultValue: T): MutableState<T>
    fun <T> newState(): MutableState<T?> = newState(null)
    fun <T> currentValue(state: State<T>): T
    fun <T> useState(block: suspend StateContext.() -> T): Job
    fun newStateContext(): StateContext
}