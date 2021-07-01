package com.example.googlerepositories.data

import androidx.annotation.VisibleForTesting
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Repository::class], version = 4)
abstract class RepositoryDatabase : RoomDatabase() {

    abstract fun repositoryDao(): RepositoryDao

    @VisibleForTesting
    val MIGRATION_1_2 = object : Migration(2, 3) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE repositories ADD COLUMN language TEXT NOT NULL DEFAULT 'Kotlin'")
        }
    }

    @VisibleForTesting
    val MIGRATION_2_3 = object : Migration(2, 3) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE repositories ADD COLUMN language TEXT NOT NULL DEFAULT 'Kotlin'")
        }
    }
}