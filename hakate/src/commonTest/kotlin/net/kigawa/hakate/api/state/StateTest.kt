package net.kigawa.hakate.api.state

import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class StateTest : StateTestBase() {
    @Test
    fun testCollect() = runTest {
        val prev = ""
        val next = "test"
        val state = stateDispatcher.newState<String>(prev)

        var value: String? = null
        var isSet = false
        stateDispatcher.useState {
            state.collect {
                value = it
                isSet = true
            }
        }

        waitStateSet { isSet }
        assertEquals(prev, value)

        isSet = false
        state.set(next)

        waitStateSet { isSet }
        assertEquals(next, value)
    }

    @Test
    fun testChild() = runTest {
        val prev = ""
        val next = "test"
        val state = stateDispatcher.newState<String>(prev)

        var value: String? = null
        var isSet = false
        stateDispatcher.useState {
            val child = state.child { parent, prev: String? ->
                return@child "$parent-child"
            }
            child.collect {
                if (it == null) return@collect
                value = it
                isSet = true
            }
        }

        waitStateSet { isSet }
        assertEquals("$prev-child", value)

        isSet = false
        state.set(next)

//        waitStateSet { isSet }
//        assertEquals("$next-child", value)
    }

}