package com.bakhur.translator.voice_to_text.domain.repository

import com.bakhur.translator.core.domain.util.CommonStateFlow
import com.bakhur.translator.voice_to_text.domain.model.VoiceToTextParserState

interface VoiceToTextParser {

    val state: CommonStateFlow<VoiceToTextParserState>

    fun startListening(languageCode: String)
    fun stopListening()
    fun cancel()
    fun reset()
}