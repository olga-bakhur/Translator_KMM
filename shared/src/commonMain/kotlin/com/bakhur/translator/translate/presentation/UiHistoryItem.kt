package com.bakhur.translator.translate.presentation

import com.bakhur.translator.core.presentation.UiLanguage

class UiHistoryItem(
    val id: Long,
    val fromLanguage: UiLanguage,
    val toLanguage: UiLanguage,
    val fromText: String,
    val toText: String
)