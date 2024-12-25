package net.kigawa.hakate.api.state

interface StateDispatcher {
    fun <T> newState(defaultValue: T): State<T>
    fun <T> newState(): State<T?> = newState(null)
}