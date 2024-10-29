package com.bakhur.translator.translation.data.model.translate

import kotlinx.serialization.Serializable

@Serializable
data class TranslateResponse(
    val translatedText: String
)