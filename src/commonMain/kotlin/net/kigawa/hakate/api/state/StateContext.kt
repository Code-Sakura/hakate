package net.kigawa.hakate.api.state

interface StateContext {
    fun dispatcher(): StateDispatcher
    fun StateContext.dispatch(block: suspend StateContext.() -> Unit)
}