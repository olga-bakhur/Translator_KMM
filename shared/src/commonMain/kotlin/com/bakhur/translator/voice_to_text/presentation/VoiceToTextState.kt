package com.bakhur.translator.voice_to_text.presentation

import com.bakhur.translator.EMPTY

data class VoiceToTextState(
    val powerRatios: List<Float> = emptyList(),
    val spokenText: String = EMPTY,
    val canRecord: Boolean = false,
    val recordError: String? = null,
    val displayState: DisplayState? = null
)

enum class DisplayState {
    WAITING_TO_TALK,
    SPEAKING,
    DISPLAYING_RESULTS,
    ERROR
}