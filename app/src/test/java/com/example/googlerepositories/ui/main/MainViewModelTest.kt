package com.example.googlerepositories.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.googlerepositories.data.Repository
import com.example.googlerepositories.mock.FakeLocalGitHubRepository
import com.example.googlerepositories.util.extensions.Resource
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val fakeGitHubRepository = FakeLocalGitHubRepository()

    @Mock
    private lateinit var viewModel: MainViewModel

    @Before
    fun setup() {
        viewModel = MainViewModel(fakeGitHubRepository)
    }

    @Test
    fun `Repositories changes when MainViewModel starts`() = runBlockingTest {
        // Arrange:
        val observer = mock<Observer<Flow<Resource<List<Repository>>>>>()
        viewModel.repositories.observeForever(observer)

        // Act:
        // MainViewModel executes only the fetchRepositories method that initiates the loading of
        // the LiveData.

        // Assert:
        verify(observer).onChanged(any())
    }
}