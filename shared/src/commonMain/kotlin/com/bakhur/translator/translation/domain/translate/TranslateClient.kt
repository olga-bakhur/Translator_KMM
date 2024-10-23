package com.bakhur.translator.translation.domain.translate

import com.bakhur.translator.core.domain.language.Language

interface TranslateClient {

    suspend fun translate(
        fromLanguage: Language,
        fromText: String,
        toLanguage: Language
    ): String
}