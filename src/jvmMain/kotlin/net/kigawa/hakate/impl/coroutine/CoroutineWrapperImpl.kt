package net.kigawa.hakate.impl.coroutine

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import net.kigawa.hakate.api.coroutine.CoroutineContextWrapper
import kotlin.coroutines.EmptyCoroutineContext

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class CoroutineWrapperImpl actual constructor() : AbstractCoroutineWrapper() {
    actual override fun launch(
        scope: CoroutineScope, context: CoroutineContextWrapper,
        start: CoroutineStart, block: suspend CoroutineScope.() -> Unit,
    ): Job {
        return scope.launch(context.coroutineContext(), start, block)
    }

    actual override fun emptyCoroutineContext(): CoroutineContextWrapper =
        CoroutineContextWrapper(EmptyCoroutineContext)
}