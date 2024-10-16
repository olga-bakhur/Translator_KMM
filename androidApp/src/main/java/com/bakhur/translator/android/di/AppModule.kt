package com.bakhur.translator.android.di

import android.app.Application
import app.cash.sqldelight.db.SqlDriver
import com.bakhur.translator.database.TranslateDatabase
import com.bakhur.translator.translate.data.history.SqlDelightHistoryDataSource
import com.bakhur.translator.translate.data.local.DatabaseDriverFactory
import com.bakhur.translator.translate.data.remote.HttpClientFactory
import com.bakhur.translator.translate.data.translate.KtorTranslateClient
import com.bakhur.translator.translate.domain.history.HistoryDataSource
import com.bakhur.translator.translate.domain.translate.TranslateClient
import com.bakhur.translator.translate.domain.translate.TranslateUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient =
        HttpClientFactory().create()

    @Provides
    @Singleton
    fun provideTranslateClient(httpClient: HttpClient): TranslateClient =
        KtorTranslateClient(httpClient)

    @Provides
    @Singleton
    fun provideDatabaseDriver(app: Application): SqlDriver =
        DatabaseDriverFactory(app).create()

    @Provides
    @Singleton
    fun provideTranslateDatabase(driver: SqlDriver): TranslateDatabase =
        TranslateDatabase(driver)

    @Provides
    @Singleton
    fun provideHistoryDataSource(database: TranslateDatabase): HistoryDataSource =
        SqlDelightHistoryDataSource(database)

    @Provides
    @Singleton
    fun provideTranslateUseCase(
        translateClient: TranslateClient,
        historyDataSource: HistoryDataSource
    ): TranslateUseCase =
        TranslateUseCase(translateClient, historyDataSource)
}