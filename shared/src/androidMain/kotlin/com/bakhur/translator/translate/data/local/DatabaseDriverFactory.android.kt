package com.bakhur.translator.translate.data.local

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.bakhur.translator.database.TranslateDatabase

actual class DatabaseDriverFactory(
    private val context: Context
) {

    actual fun create(): SqlDriver =
        AndroidSqliteDriver(
            schema = TranslateDatabase.Schema,
            context = context,
            name = TRANSLATE_DATABASE_NAME
        )
}