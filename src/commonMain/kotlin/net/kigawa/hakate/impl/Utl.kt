package net.kigawa.hakate.impl

object Utl {
    fun <T> T.apply(block: T.() -> Unit): T {
        block()
        return this
    }
    suspend fun <T> T.suspendApply(block: suspend T.() -> Unit): T {
        block()
        return this
    }
}