package net.kigawa.hakate.api.state

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class StateDispatcherTest : StateTestBase() {
    @Test
    fun testNewState0() {
        val state = stateDispatcher.newState<String>()
        assertNull(state.currentValue())
    }

    fun states() = listOf(
        "",
        null,
        5,
        1.1,
        "test",
        listOf("", "test1"),
    )

    @Test
    fun testNewState1() {
        states().forEach {
            val state = stateDispatcher.newState(it)
            assertEquals(it, state.currentValue())
        }
    }
}