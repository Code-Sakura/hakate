package net.kigawa.hakate.api.state

interface MutableState<T> : State<T> {
    fun set(value: T)
}