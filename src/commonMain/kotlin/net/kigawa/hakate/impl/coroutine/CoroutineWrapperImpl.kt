package net.kigawa.hakate.impl.coroutine

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import net.kigawa.hakate.api.coroutine.CoroutineContextWrapper

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class CoroutineWrapperImpl() : AbstractCoroutineWrapper {
    override fun launch(
        scope: CoroutineScope, context: CoroutineContextWrapper,
        start: CoroutineStart, block: suspend CoroutineScope.() -> Unit,
    ): Job

    override fun emptyCoroutineContext(): CoroutineContextWrapper
}