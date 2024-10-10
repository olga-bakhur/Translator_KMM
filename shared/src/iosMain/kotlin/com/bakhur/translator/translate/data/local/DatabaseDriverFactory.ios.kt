package com.bakhur.translator.translate.data.local

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.bakhur.translator.database.TranslateDatabase

actual class DatabaseDriverFactory {

    actual fun create(): SqlDriver =
        NativeSqliteDriver(
            schema = TranslateDatabase.Schema,
            name = TRANSLATE_DATABASE_NAME
        )
}