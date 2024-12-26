package net.kigawa.hakate.api.state

import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.plus
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.fail

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

    private suspend fun waitStateSet(check: () -> Boolean) {
        val limit = Clock.System.now().plus(1, DateTimeUnit.SECOND)
        while (true) {
            if (check()) break
            if (limit < Clock.System.now()) fail("timeout")
            delay(1)
        }
    }
}