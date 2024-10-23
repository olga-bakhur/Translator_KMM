package com.bakhur.translator.translation.data.local

import com.bakhur.translator.core.domain.util.CommonFlow
import com.bakhur.translator.core.domain.util.toCommonFlow
import com.bakhur.translator.translation.domain.history.HistoryDataSource
import com.bakhur.translator.translation.domain.history.HistoryItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.coroutines.CoroutineContext

class FakeHistoryDataSource : HistoryDataSource {

    private val _data = MutableStateFlow<List<HistoryItem>>(emptyList())

    override fun getHistory(context: CoroutineContext): CommonFlow<List<HistoryItem>> =
        _data.toCommonFlow()

    override suspend fun insertHistoryItem(historyItem: HistoryItem) {
        _data.value += historyItem
    }
}