package com.bakhur.translator.translate.domain.translate

import com.bakhur.translator.core.domain.language.Language
import com.bakhur.translator.core.domain.util.Resource
import com.bakhur.translator.translate.domain.history.HistoryDataSource
import com.bakhur.translator.translate.domain.history.HistoryItem

class TranslateUseCase(
    private val client: TranslateClient,
    private val historyDataSource: HistoryDataSource
) {

    suspend fun execute(
        fromLanguage: Language,
        toLanguage: Language,
        fromText: String
    ): Resource<String> =
        try {
            val translatedText = client.translate(
                fromLanguage = fromLanguage,
                toLanguage = toLanguage,
                fromText = fromText
            )

            historyDataSource.insertHistoryItem(
                HistoryItem(
                    id = null,
                    fromLanguageCode = fromLanguage.langCode,
                    toLanguageCode = toLanguage.langCode,
                    fromText = fromText,
                    toText = translatedText
                )
            )

            Resource.Success(translatedText)

        } catch (e: TranslateException) {
            e.printStackTrace()
            Resource.Error(e)
        }
}