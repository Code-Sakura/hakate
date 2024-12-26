package net.kigawa.hakate.api.state

import kotlin.test.Test

class StateDispatcherTest : StateTestBase() {
    @Test
    fun testNewState0() {
        val state = stateDispatcher.newState<String>()
        state
    }

    @Test
    fun testNewState1() {
        stateDispatcher.newState<String>("")
    }
}