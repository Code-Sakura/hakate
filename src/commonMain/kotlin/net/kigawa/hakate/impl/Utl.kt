package net.kigawa.hakate.impl

object Utl {
    suspend fun <T> T.suspendApply(block: suspend T.() -> Unit): T {
        block()
        return this
    }
}