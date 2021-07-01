package com.example.googlerepositories.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "repositories")
data class Repository(
    @PrimaryKey val id: Long,
    val name: String,
    val fullName: String,
    @Embedded
    val owner: Owner,
    val description: String?,
    val htmlUrl: String?,
    val fork: Boolean,
    val language: String?
)

data class Owner(
    val ownerId: Long,
    val avatarUrl: String,
    val ownerHtmlUrl: String?
)