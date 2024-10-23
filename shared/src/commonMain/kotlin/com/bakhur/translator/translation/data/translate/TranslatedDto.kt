package com.bakhur.translator.translation.data.translate

import kotlinx.serialization.Serializable

@Serializable
data class TranslatedDto(
    val translatedText: String
)