package com.example.googlerepositories.mock

import com.example.googlerepositories.api.response.OwnerResponse
import com.example.googlerepositories.api.response.RepositoryResponse
import kotlin.random.Random

fun mockRepositoriesResponse() = listOf(mockRepositoryResponse(), mockRepositoryResponse(1))

fun mockRepositoryResponse(id: Long = 0) =
    RepositoryResponse(
        id = id,
        nodeId = "",
        name = "",
        fullName = "",
        private = Random.nextBoolean(),
        owner = mockOwner(),
        htmlUrl = "",
        description = "",
        fork = Random.nextBoolean(),
        language = listOf("Java", "Kotlin", "Swift", "Objective-C").random(),
    )

fun mockOwner(id: Long = 0) = OwnerResponse(
    login = "",
    id = id,
    nodeId = "",
    avatarUrl = "",
    gravatarId = "",
    url = "",
    htmlUrl = "",
    followersUrl = "",
    followingUrl = "",
    gistsUrl = "",
    starredUrl = "",
    subscriptionsUrl = "",
    organizationsUrl = "",
    reposUrl = "",
    eventsUrl = "",
    receivedEventsUrl = "",
    type = "",
    siteAdmin = Random.nextBoolean()
)