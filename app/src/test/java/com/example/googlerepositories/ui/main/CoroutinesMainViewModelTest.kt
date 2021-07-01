package com.example.googlerepositories.ui.main

import com.example.googlerepositories.mock.FakeLocalGitHubRepository
import com.example.googlerepositories.mock.fakeRepositories
import com.example.googlerepositories.rule.CoroutinesTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock

@ExperimentalCoroutinesApi
class CoroutinesMainViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private val fakeGitHubRepository = FakeLocalGitHubRepository()

    @Mock
    private lateinit var viewModel: MainViewModel

    @Before
    fun setup() {
        viewModel = MainViewModel(fakeGitHubRepository)
    }

    @Test
    fun `Listening to repositories Flow emits the list of repositories from the server`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            viewModel.repositories.value?.collect {
                assertEquals(fakeRepositories, it)
            }
        }
}