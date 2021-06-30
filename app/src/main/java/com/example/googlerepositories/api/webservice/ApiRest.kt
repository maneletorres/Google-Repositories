package com.example.googlerepositories.api.webservice

import com.example.googlerepositories.api.response.RepositoryResponse
import retrofit2.http.GET

interface ApiRest {

    @GET("orgs/google/repos")
    suspend fun getGoogleRepositories(): List<RepositoryResponse>
}