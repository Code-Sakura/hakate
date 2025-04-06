package net.kigawa.hakate.api.multi

import net.kigawa.hakate.api.state.State

@Suppress("unused")
fun <T, U, V> mergeState(state1: State<T>, state2: State<U>, state3: State<V>): State<MultiValue3<T, U, V>> {
    return mergeState(state1, state2).merge(state3)
}

fun <T, U, V> State<MultiValue2<T, U>>.merge(state3: State<V>): State<MultiValue3<T, U, V>> {
    return merge(state3) { a, b -> MultiValue3(a.first, a.second, b) }
}

data class MultiValue3<T, U, V>(val first: T, val second: U, val third: V)