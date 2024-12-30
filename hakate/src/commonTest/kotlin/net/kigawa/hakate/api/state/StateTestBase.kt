package net.kigawa.hakate.api.state

import kotlinx.coroutines.delay
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.plus
import net.kigawa.hakate.api.ApiTestBase
import kotlin.test.fail

abstract class StateTestBase : ApiTestBase() {
    val stateDispatcher by lazy { initializer.newStateDispatcher() }

    protected suspend fun waitStateSet(check: () -> Boolean) {
        val limit = Clock.System.now().plus(1, DateTimeUnit.SECOND)
        while (true) {
            if (check()) break
            if (limit < Clock.System.now()) fail("timeout")
            delay(1)
        }
    }
}