package com.bakhur.translator.voice_to_text.domain.model

import com.bakhur.translator.core.util.EMPTY

data class VoiceToTextParserState(
    val result: String = EMPTY,
    val error: String? = null,
    val powerRatio: Float = 0f,
    val isSpeaking: Boolean = false
)