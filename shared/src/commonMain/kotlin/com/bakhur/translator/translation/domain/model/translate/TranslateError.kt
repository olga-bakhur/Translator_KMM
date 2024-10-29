package com.bakhur.translator.translation.domain.model.translate

enum class TranslateError {
    SERVICE_UNAVAILABLE,
    CLIENT_ERROR,
    SERVER_ERROR,
    UNKNOWN_ERROR
}

class TranslateException(val error: TranslateError) : Exception(
    "An error occurred when translating: $error"
)