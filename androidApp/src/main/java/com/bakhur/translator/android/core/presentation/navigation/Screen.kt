package com.bakhur.translator.android.core.presentation.navigation

import com.bakhur.translator.android.core.presentation.navigation.NavArguments.LANGUAGE_CODE

object NavArguments {
    const val LANGUAGE_CODE = "languageCode"
    const val VOICE_RESULT = "voice_result"
}

sealed class Screen(val route: String) {
    data object Translate : Screen("translate")
    data object VoiceToText : Screen("voice_to_text/{$LANGUAGE_CODE}") {
        fun createRoute(languageCode: String): String = "voice_to_text/$languageCode"
    }
}