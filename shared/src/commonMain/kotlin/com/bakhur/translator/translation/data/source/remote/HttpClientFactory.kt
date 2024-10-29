package com.bakhur.translator.translation.data.source.remote

import io.ktor.client.HttpClient

expect class HttpClientFactory {

    fun create(): HttpClient
}