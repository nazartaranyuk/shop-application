package com.nazartaraniuk.shopapplication.data.db

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2_TEST = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE favorites ADD COLUMN test INTEGER DEFAULT 0")
    }
}