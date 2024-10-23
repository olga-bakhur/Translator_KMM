package com.bakhur.translator.translation.domain.history

data class HistoryItem(
    val id: Long?,
    val fromLanguageCode: String,
    val toLanguageCode: String,
    val fromText: String,
    val toText: String
)