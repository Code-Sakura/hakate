package net.kigawa.hakate.impl

import kotlinx.coroutines.flow.MutableStateFlow
import net.kigawa.hakate.api.State

class StateImpl<T>: State<T> {
    private val flow = MutableStateFlow<T>()
    override fun collect(block: (T) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun <R> child(block: (T) -> R): State<R> {
        TODO("Not yet implemented")
    }

    override fun set(value: T) {
        TODO("Not yet implemented")
    }
}