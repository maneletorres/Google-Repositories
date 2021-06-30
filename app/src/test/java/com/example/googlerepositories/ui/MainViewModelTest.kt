package com.example.googlerepositories.ui

import com.example.googlerepositories.api.repository.GitHubRepository
import com.example.googlerepositories.ui.main.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Test
    fun `Listening to repositories Flow emits the list of repositories from the server`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val repository: GitHubRepository = FakeLocalGitHubRepository()

            val mainViewModel = MainViewModel(repository)

            mainViewModel.repositories.value?.collect {
                Assert.assertEquals(fakeRepositories, it)
            }
        }
}