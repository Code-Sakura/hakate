package net.kigawa.hakate.api.state

interface State<T> {
    fun <R> StateContext.collect(defaultValue: R, block: StateContext.(value: T, prev: R) -> R)
    fun <R> StateContext.collect(block: StateContext.(value: T, prev: R?) -> R) = collect(null, block)
    fun StateContext.collect(block: StateContext.(value: T) -> Unit) = collect(null, { value, _ ->
        block(value)
        null
    })

    fun <R> StateContext.child(defaultValue: R, block: StateContext.(T, prev: R) -> R): State<R>
    fun <R> StateContext.child(block: StateContext.(T, prev: R?) -> R): State<R?> = child(null, block)
    fun set(value: T)
}
