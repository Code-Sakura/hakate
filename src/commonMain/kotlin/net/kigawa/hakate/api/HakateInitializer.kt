package net.kigawa.hakate.api

import net.kigawa.hakate.api.coroutine.CoroutineWrapper
import net.kigawa.hakate.impl.dispatcher.StateDispatcherImpl

class HakateInitializer(
    private val coroutineWrapper: CoroutineWrapper = CoroutineWrapper(),
) {
    fun newStateDispatcher() = StateDispatcherImpl(coroutineWrapper)
}