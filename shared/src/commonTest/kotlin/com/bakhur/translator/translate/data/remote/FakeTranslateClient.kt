package com.bakhur.translator.translate.data.remote

import com.bakhur.translator.core.domain.language.Language
import com.bakhur.translator.translate.domain.translate.TranslateClient

class FakeTranslateClient : TranslateClient {

    var translatedText = "test translation"

    override suspend fun translate(
        fromLanguage: Language,
        fromText: String,
        toLanguage: Language
    ): String = translatedText
}