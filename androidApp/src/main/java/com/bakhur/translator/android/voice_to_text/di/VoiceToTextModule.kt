package com.bakhur.translator.android.voice_to_text.di

import android.app.Application
import com.bakhur.translator.android.voice_to_text.AndroidVoiceToTextParser
import com.bakhur.translator.voice_to_text.domain.VoiceToTextParser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object VoiceToTextModule {

    @Provides
    @ViewModelScoped
    fun provideVoiceToTextParser(context: Application): VoiceToTextParser =
        AndroidVoiceToTextParser(context)
}