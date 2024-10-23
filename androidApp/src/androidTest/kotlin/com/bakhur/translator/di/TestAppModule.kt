package com.bakhur.translator.di

import com.bakhur.translator.translation.data.local.FakeHistoryDataSource
import com.bakhur.translator.translation.data.remote.FakeTranslateClient
import com.bakhur.translator.translation.domain.history.HistoryDataSource
import com.bakhur.translator.translation.domain.translate.TranslateClient
import com.bakhur.translator.translation.domain.translate.TranslateUseCase
import com.bakhur.translator.voice_to_text.data.FakeVoiceToTextParser
import com.bakhur.translator.voice_to_text.domain.VoiceToTextParser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    @Provides
    @Singleton
    fun provideFakeTranslateClient(): TranslateClient = FakeTranslateClient()

    @Provides
    @Singleton
    fun provideFakeHistoryDataSource(): HistoryDataSource = FakeHistoryDataSource()

    @Provides
    @Singleton
    fun provideTranslateUseCase(
        translateClient: TranslateClient,
        historyDataSource: HistoryDataSource
    ): TranslateUseCase =
        TranslateUseCase(
            translateClient = translateClient,
            historyDataSource = historyDataSource
        )

    @Provides
    @Singleton
    fun provideFakeVoiceToTextParser(): VoiceToTextParser = FakeVoiceToTextParser()
}