package com.example.googlerepositories.di

import com.example.googlerepositories.api.repository.GitHubRepository
import com.example.googlerepositories.api.repository.GitHubRepositoryImpl
import com.example.googlerepositories.api.webservice.ApiRest
import com.example.googlerepositories.data.RepositoryDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
object DataModule {

    @Provides
    fun provideGitHubRepository(apiRest: ApiRest, db: RepositoryDatabase): GitHubRepository =
        GitHubRepositoryImpl(apiRest, db)
}