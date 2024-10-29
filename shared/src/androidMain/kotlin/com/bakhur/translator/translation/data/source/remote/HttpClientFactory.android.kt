package com.bakhur.translator.translation.data.source.remote

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json

actual class HttpClientFactory {

    actual fun create(): HttpClient {
        return HttpClient(Android) {
            install(ContentNegotiation) {
                json()
            }
            install(DefaultRequest) {
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL
            }
            install(HttpTimeout) {
                requestTimeoutMillis = NETWORK_TIME_OUT
                connectTimeoutMillis = NETWORK_TIME_OUT
                socketTimeoutMillis = NETWORK_TIME_OUT
            }
        }
    }
}