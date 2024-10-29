package com.bakhur.translator.translation.data.source.remote

import io.ktor.client.HttpClient

const val NETWORK_TIME_OUT = 10_000L

expect class HttpClientFactory {

    fun create(): HttpClient
}