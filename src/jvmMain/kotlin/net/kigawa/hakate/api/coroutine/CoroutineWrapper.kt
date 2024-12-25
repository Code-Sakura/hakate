package net.kigawa.hakate.api.coroutine

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.EmptyCoroutineContext

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class CoroutineWrapper {
    actual fun launch(
        scope: CoroutineScope,
        context: CoroutineContextWrapper,
        start: CoroutineStart,
        block: suspend CoroutineScope.() -> Unit,
    ): Job {
        return scope.launch(context.coroutineContext(), start, block)
    }

    actual fun emptyCoroutineContext(): CoroutineContextWrapper {
        return CoroutineContextWrapper(EmptyCoroutineContext)
    }

}