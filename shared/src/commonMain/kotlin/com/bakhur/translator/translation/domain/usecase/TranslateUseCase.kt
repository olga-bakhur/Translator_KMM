package com.bakhur.translator.translation.domain.usecase

import com.bakhur.translator.core.domain.language.Language
import com.bakhur.translator.core.domain.util.Resource
import com.bakhur.translator.translation.domain.model.history.HistoryItem
import com.bakhur.translator.translation.domain.model.translate.TranslateException
import com.bakhur.translator.translation.domain.repository.history.HistoryDataSource
import com.bakhur.translator.translation.domain.repository.translate.TranslateClient

class TranslateUseCase(
    private val translateClient: TranslateClient,
    private val historyDataSource: HistoryDataSource
) {

    suspend fun execute(
        fromLanguage: Language,
        toLanguage: Language,
        fromText: String
    ): Resource<String> =
        try {
            val translatedText = translateClient.translate(
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