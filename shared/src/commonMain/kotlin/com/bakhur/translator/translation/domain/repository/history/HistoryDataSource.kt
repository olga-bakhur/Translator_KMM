package com.bakhur.translator.translation.domain.repository.history

import com.bakhur.translator.core.domain.util.CommonFlow
import com.bakhur.translator.translation.domain.model.history.HistoryItem
import kotlin.coroutines.CoroutineContext

interface HistoryDataSource {

    fun getHistory(context: CoroutineContext): CommonFlow<List<HistoryItem>>

    suspend fun insertHistoryItem(historyItem: HistoryItem)
}