package net.kigawa.hakate.api.coroutine

import kotlin.coroutines.CoroutineContext

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class CoroutineContextWrapper(
    private val coroutineContext: CoroutineContext,
) {
    fun coroutineContext(): CoroutineContext = coroutineContext
}