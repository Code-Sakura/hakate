package net.kigawa.hakate.sample

import net.kigawa.hakate.api.HakateInitializer
import kotlin.jvm.JvmStatic

object Sample {
    @JvmStatic
    fun main(args: Array<String>) {
        val initializer = HakateInitializer()
        initializer.newStateDispatcher().newState("")

    }
}