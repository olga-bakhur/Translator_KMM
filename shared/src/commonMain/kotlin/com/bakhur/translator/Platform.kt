package com.bakhur.translator

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform