package net.kigawa.hakate.impl.dispatcher

import net.kigawa.hakate.api.coroutine.CoroutineWrapper
import net.kigawa.hakate.api.state.State
import net.kigawa.hakate.api.state.StateDispatcher
import net.kigawa.hakate.impl.state.StateImpl

class StateDispatcherImpl(
    private val coroutineWrapper: CoroutineWrapper,
) : StateDispatcher {
    override fun coroutine(): CoroutineWrapper = coroutineWrapper

    override fun <T> newState(defaultValue: T): State<T> {
        return StateImpl(defaultValue)
    }
}