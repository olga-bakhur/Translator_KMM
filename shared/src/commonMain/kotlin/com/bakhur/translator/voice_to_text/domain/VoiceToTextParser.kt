package com.bakhur.translator.voice_to_text.domain

import com.bakhur.translator.core.domain.util.CommonStateFlow

interface VoiceToTextParser {

    val state: CommonStateFlow<VoiceToTextParserState>

    fun startListening(languageCode: String)
    fun stopListening()
    fun cancel()
    fun reset()
}