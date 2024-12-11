package net.kigawa.hakate.api

interface State<T> {
    fun collect(block: (T) -> Unit)
    fun <R> child(block: (T) -> R): State<R>
    fun set(value: T)
}