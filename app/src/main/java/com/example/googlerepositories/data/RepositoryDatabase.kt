package com.example.googlerepositories.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Repository::class], version = 1)
abstract class RepositoryDatabase : RoomDatabase() {

    abstract fun repositoryDao(): RepositoryDao
}