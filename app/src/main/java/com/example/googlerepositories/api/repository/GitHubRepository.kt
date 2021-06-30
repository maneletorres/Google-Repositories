package com.example.googlerepositories.api.repository

import androidx.room.withTransaction
import com.example.googlerepositories.api.webservice.ApiRest
import com.example.googlerepositories.data.Repository
import com.example.googlerepositories.data.RepositoryDao
import com.example.googlerepositories.data.RepositoryDatabase
import com.example.googlerepositories.util.extensions.Resource
import com.example.googlerepositories.util.extensions.networkBoundResource
import com.example.googlerepositories.util.toRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GitHubRepository {

    fun getRepositories(): Flow<Resource<List<Repository>>>
}

class GitHubRepositoryImpl @Inject constructor(
    private val apiRest: ApiRest,
    private val db: RepositoryDatabase
) : GitHubRepository {
    private val repositoryDao: RepositoryDao = db.repositoryDao()

    override fun getRepositories() = networkBoundResource(
        query = { repositoryDao.getAllRepositories() },
        fetch = {
            // FIXME: delay set to 2000 milliseconds to demonstrate the execution of ProgressBar
            //  while loading the list of repositories.
            delay(2000)
            apiRest.getGoogleRepositories().map { it.toRepository() }
        },
        saveFetchResult = { repositories ->
            db.withTransaction {
                repositoryDao.deleteAllRepositories()
                repositoryDao.insertRepositories(repositories)
            }
        }
    )
}