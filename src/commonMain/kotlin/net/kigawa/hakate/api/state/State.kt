package net.kigawa.hakate.api.state

interface State<T> {
    fun <R> collect(context: StateContext, defaultValue: R, block: StateContext.(value: T, prev: R) -> R)
    fun <R> child(context: StateContext, defaultValue: R, block: StateContext.(T, prev: R) -> R): State<R>
    fun set(value: T)
    fun currentValue(): T
}
