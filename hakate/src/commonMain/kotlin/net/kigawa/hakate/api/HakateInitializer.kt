package net.kigawa.hakate.api

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import net.kigawa.hakate.impl.dispatcher.StateDispatcherImpl

/**
 * Hakateの初期化を行う
 */
class HakateInitializer() {
    var coroutineScope = CoroutineScope(Dispatchers.Default)
    fun newStateDispatcher() = StateDispatcherImpl(coroutineScope)
}