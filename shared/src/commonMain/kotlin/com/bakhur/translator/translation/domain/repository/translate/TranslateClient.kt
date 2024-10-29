package com.bakhur.translator.translation.domain.repository.translate

import com.bakhur.translator.core.domain.language.Language

interface TranslateClient {

    suspend fun translate(
        fromLanguage: Language,
        fromText: String,
        toLanguage: Language
    ): String
}