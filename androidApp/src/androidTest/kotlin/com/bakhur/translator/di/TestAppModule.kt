package com.bakhur.translator.di

import com.bakhur.translator.translation.data.local.FakeHistoryDataSource
import com.bakhur.translator.translation.data.remote.FakeTranslateClient
import com.bakhur.translator.translation.domain.repository.history.HistoryDataSource
import com.bakhur.translator.translation.domain.repository.translate.TranslateClient
import com.bakhur.translator.translation.domain.usecase.TranslateUseCase
import com.bakhur.translator.voice_to_text.data.FakeVoiceToTextParser
import com.bakhur.translator.voice_to_text.domain.repository.VoiceToTextParser
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