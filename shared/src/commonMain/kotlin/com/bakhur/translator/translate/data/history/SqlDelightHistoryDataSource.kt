package com.bakhur.translator.translate.data.history

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.bakhur.translator.core.domain.util.CommonFlow
import com.bakhur.translator.core.domain.util.toCommonFlow
import com.bakhur.translator.database.TranslateDatabase
import com.bakhur.translator.translate.domain.history.HistoryDataSource
import com.bakhur.translator.translate.domain.history.HistoryItem
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock
import kotlin.coroutines.CoroutineContext

class SqlDelightHistoryDataSource(
    db: TranslateDatabase
) : HistoryDataSource {

    private val queries = db.translateQueries

    override fun getHistory(context: CoroutineContext): CommonFlow<List<HistoryItem>> =
        queries.getHistory()
            .asFlow()
            .mapToList(context)
            .map { history ->
                history.map { historyEntity ->
                    historyEntity.toHistoryItem()
                }
            }
            .toCommonFlow()

    override suspend fun insertHistoryItem(historyItem: HistoryItem) =
        queries.insertHistoryEntity(
            id = historyItem.id,
            fromLanguageCode = historyItem.fromLanguageCode,
            fromText = historyItem.fromText,
            toLanguageCode = historyItem.toLanguageCode,
            toText = historyItem.toText,
            timestamp = Clock.System.now().toEpochMilliseconds()
        )
}