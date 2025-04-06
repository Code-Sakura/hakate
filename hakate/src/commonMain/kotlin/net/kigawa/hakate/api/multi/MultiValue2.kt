package net.kigawa.hakate.api.multi

import net.kigawa.hakate.api.state.State

fun <T, U> mergeState(state1: State<T>, state2: State<U>): State<MultiValue2<T, U>> {
    return state1.merge(state2) { a, b -> MultiValue2(a, b) }
}

data class MultiValue2<T, U>(val first: T, val second: U)