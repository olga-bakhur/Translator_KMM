package com.bakhur.translator.translate.presentation

import com.bakhur.translator.core.EMPTY
import com.bakhur.translator.core.presentation.UiLanguage
import com.bakhur.translator.translate.domain.translate.TranslateError

data class TranslateState(
    val fromText: String = EMPTY,
    val toText: String? = null,
    val isTranslating: Boolean = false,
    val fromLanguage: UiLanguage = UiLanguage.byCode("en"),
    val toLanguage: UiLanguage = UiLanguage.byCode("en"),
    val isChoosingFromLanguage: Boolean = false,
    val isChoosingToLanguage: Boolean = false,
    val error: TranslateError? = null,
    val history: List<UiHistoryItem> = emptyList()
)