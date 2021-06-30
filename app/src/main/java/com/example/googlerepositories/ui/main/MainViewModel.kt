package com.example.googlerepositories.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.googlerepositories.api.repository.GitHubRepository
import com.example.googlerepositories.data.Repository
import com.example.googlerepositories.util.extensions.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val gitHubRepository: GitHubRepository) :
    ViewModel() {

    private val _repositories = MutableLiveData<Flow<Resource<List<Repository>>>>()
    val repositories: LiveData<Flow<Resource<List<Repository>>>> get() = _repositories

    init {
        fetchRepositories()
    }

    private fun fetchRepositories() {
        viewModelScope.launch {
            _repositories.postValue(gitHubRepository.getRepositories())
        }
    }
}