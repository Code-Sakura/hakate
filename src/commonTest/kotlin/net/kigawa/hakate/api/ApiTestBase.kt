package net.kigawa.hakate.api

abstract class ApiTestBase {
    val initializer by lazy { HakateInitializer() }
}