package com.example.googlerepositories.mock

import com.example.googlerepositories.api.repository.GitHubRepository
import com.example.googlerepositories.data.Owner
import com.example.googlerepositories.data.Repository
import com.example.googlerepositories.util.extensions.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlin.random.Random

val fakeRepositories = createFakeRepository()

fun createFakeRepository(): List<Repository> {
    val repositoryList: ArrayList<Repository> = arrayListOf()
    for (i in 1..10) {
        val fork = Random.nextBoolean()
        val repo = Repository(
            1,
            "Name $i",
            "Full name $i",
            Owner(i.toLong(), "Login $i", "Avatar URL $i"),
            "Description $i",
            "HTML URL $i",
            fork,
            "Language $i"
        )
        repositoryList.add(repo)
    }
    return repositoryList
}

class FakeLocalGitHubRepository : GitHubRepository {
    private val repositories = mutableListOf<Repository>()

    override fun getRepositories(): Flow<Resource<List<Repository>>> =
        flowOf(Resource.Success(repositories))
}
