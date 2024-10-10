package com.bakhur.translator.core.presentation

import com.bakhur.translator.core.domain.language.Language

actual class UiLanguage(
    actual val language: Language,
    val imageName: String
) {

    actual companion object {
        actual fun byCode(langCode: String): UiLanguage =
            allLanguages.find { it.language.langCode == langCode }
                ?: throw IllegalArgumentException("Invalid or unsupported language code")

        actual val allLanguages: List<UiLanguage>
            get() = Language.entries.map { language ->
                UiLanguage(
                    language = language,
                    imageName = language.langName.lowercase()
                )
            }
    }
}