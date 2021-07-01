package com.example.googlerepositories.repository

import com.example.googlerepositories.api.repository.GitHubRepositoryImpl
import com.example.googlerepositories.api.webservice.ApiRest
import com.example.googlerepositories.data.Repository
import com.example.googlerepositories.data.RepositoryDatabase
import com.example.googlerepositories.mock.mockRepositoriesResponse
import com.example.googlerepositories.util.extensions.Resource
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

// TODO: to implement!!!
@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class GitHubRepositoryTest {

    @Test
    fun `should get repositories response`() = runBlockingTest {
        val repositoriesResponse = mockRepositoriesResponse()

        // Mock API Service:
        val apiService = mock<ApiRest> {
            onBlocking { getGoogleRepositories() } doReturn repositoriesResponse
        }

        // Mock Room database:
        val repositoryDatabase = mock<RepositoryDatabase> {}

        val gitHubRepository = GitHubRepositoryImpl(apiService, repositoryDatabase)

        // Test & verify:
        val flow: Flow<Resource<List<Repository>>> = gitHubRepository.getRepositories()
        /*flow.collect {
            val firstRepository = repositoriesResponse[0]
            val secondRepository = repositoriesResponse[1]

            data.data?.get(0) shouldBeEqualTo repositoriesResponse[0]

            data.data?.get(0)?.apply {
                id shouldBeEqualTo firstRepository.id
                name shouldBeEqualTo firstRepository.name
                fullName shouldBeEqualTo firstRepository.fullName
            }

            data.data?.get(1)?.apply {
                id shouldBeEqualTo secondRepository.id
                name shouldBeEqualTo secondRepository.name
                fullName shouldBeEqualTo secondRepository.fullName
            }
        }*/
    }
}