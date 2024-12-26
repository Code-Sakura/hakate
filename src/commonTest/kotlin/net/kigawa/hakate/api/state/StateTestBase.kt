package net.kigawa.hakate.api.state

import net.kigawa.hakate.api.ApiTestBase

abstract class StateTestBase : ApiTestBase() {
    val stateDispatcher by lazy { initializer.newStateDispatcher() }
}