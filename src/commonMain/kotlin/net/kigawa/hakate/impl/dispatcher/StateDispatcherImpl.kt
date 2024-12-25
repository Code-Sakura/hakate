package net.kigawa.hakate.impl.dispatcher

import net.kigawa.hakate.api.state.State
import net.kigawa.hakate.api.state.StateDispatcher
import net.kigawa.hakate.impl.state.StateImpl

class StateDispatcherImpl() : StateDispatcher {
    override fun <T> newState(defaultValue: T): State<T> {
        return StateImpl(defaultValue)
    }
}