package net.kigawa.hakate.api.coroutine

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class CoroutineWrapper() {
    fun launch(
        scope: CoroutineScope,
        context: CoroutineContextWrapper = emptyCoroutineContext(),
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> Unit,
    ): Job

    fun emptyCoroutineContext(): CoroutineContextWrapper
}