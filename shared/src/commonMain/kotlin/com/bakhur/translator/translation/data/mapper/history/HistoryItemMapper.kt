package com.bakhur.translator.translation.data.mapper.history

import com.bakhur.translator.translation.domain.model.history.HistoryItem
import database.HistoryEntity

fun HistoryEntity.toHistoryItem(): HistoryItem =
    HistoryItem(
        id = id,
        fromLanguageCode = fromLanguageCode,
        fromText = fromText,
        toLanguageCode = toLanguageCode,
        toText = toText
    )