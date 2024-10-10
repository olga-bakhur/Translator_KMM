package com.bakhur.translator.core.domain.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector

actual open class CommonFlow<T> actual constructor(
    private val flow: Flow<T>
) : Flow<T> {

    actual override suspend fun collect(collector: FlowCollector<T>) {
        flow.collect(collector)
    }
}