package com.bakhur.translator.translation.domain.history

import com.bakhur.translator.core.domain.util.CommonFlow
import kotlin.coroutines.CoroutineContext

interface HistoryDataSource {

    fun getHistory(context: CoroutineContext): CommonFlow<List<HistoryItem>>

    suspend fun insertHistoryItem(historyItem: HistoryItem)
}