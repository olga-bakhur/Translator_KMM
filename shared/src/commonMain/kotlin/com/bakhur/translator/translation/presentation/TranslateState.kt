package com.bakhur.translator.translation.presentation

import com.bakhur.translator.core.presentation.UiLanguage
import com.bakhur.translator.core.util.EMPTY
import com.bakhur.translator.translation.domain.model.translate.TranslateError
import com.bakhur.translator.translation.presentation.model.UiHistoryItem
import com.bakhur.translator.translation.presentation.util.NetworkConstants.DEFAULT_FROM_LANGUAGE
import com.bakhur.translator.translation.presentation.util.NetworkConstants.DEFAULT_TO_LANGUAGE

data class TranslateState(
    val fromText: String = EMPTY,
    val toText: String? = null,
    val isTranslating: Boolean = false,
    val fromLanguage: UiLanguage = UiLanguage.byCode(DEFAULT_FROM_LANGUAGE),
    val toLanguage: UiLanguage = UiLanguage.byCode(DEFAULT_TO_LANGUAGE),
    val isChoosingFromLanguage: Boolean = false,
    val isChoosingToLanguage: Boolean = false,
    val error: TranslateError? = null,
    val history: List<UiHistoryItem> = emptyList()
)