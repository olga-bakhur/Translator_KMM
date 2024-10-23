package com.bakhur.translator.translation.presentation

import com.bakhur.translator.core.presentation.UiLanguage

data class UiHistoryItem(
    val id: Long,
    val fromLanguage: UiLanguage,
    val toLanguage: UiLanguage,
    val fromText: String,
    val toText: String
)