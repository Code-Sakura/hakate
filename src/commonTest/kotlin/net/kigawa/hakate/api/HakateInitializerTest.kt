package net.kigawa.hakate.api

import kotlin.test.Test
import kotlin.test.assertNotNull

class HakateInitializerTest : ApiTestBase() {

    @Test
    fun testInit() {
        val initializer = HakateInitializer()
        assertNotNull(initializer)
    }

    @Test
    fun testNewStateDispatcher() {
        val dispatcher = initializer.newStateDispatcher()
        assertNotNull(dispatcher)
    }
}