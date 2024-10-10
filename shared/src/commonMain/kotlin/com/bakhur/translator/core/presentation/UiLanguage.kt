package com.bakhur.translator.core.presentation

import com.bakhur.translator.core.domain.language.Language

expect class UiLanguage {

    val language: Language

    companion object {
        fun byCode(langCode: String): UiLanguage
        val allLanguages: List<UiLanguage>
    }
}