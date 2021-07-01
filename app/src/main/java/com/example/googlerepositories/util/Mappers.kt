package com.example.googlerepositories.util

import com.example.googlerepositories.api.response.OwnerResponse
import com.example.googlerepositories.api.response.RepositoryResponse
import com.example.googlerepositories.data.Owner
import com.example.googlerepositories.data.Repository

fun RepositoryResponse.toRepository() : Repository =
    Repository(
        id = id,
        name = name,
        fullName = fullName,
        owner = owner.toOwner(),
        description = description,
        htmlUrl = htmlUrl,
        fork = fork,
        language = language,
    )

fun OwnerResponse.toOwner(): Owner = Owner(
    ownerId = id,
    avatarUrl = avatarUrl,
    ownerHtmlUrl = htmlUrl
)