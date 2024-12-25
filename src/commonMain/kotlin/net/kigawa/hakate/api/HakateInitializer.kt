package net.kigawa.hakate.api

import net.kigawa.hakate.impl.dispatcher.StateDispatcherImpl

class HakateInitializer() {
    fun newStateDispatcher() = StateDispatcherImpl()
}