package com.bakhur.translator.translate.data.local

import app.cash.sqldelight.db.SqlDriver

const val TRANSLATE_DATABASE_NAME = "translate.db"

expect class DatabaseDriverFactory {

    fun create(): SqlDriver
}