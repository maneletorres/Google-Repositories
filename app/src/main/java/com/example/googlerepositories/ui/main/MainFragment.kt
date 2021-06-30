package com.example.googlerepositories.ui.main

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.googlerepositories.R
import com.example.googlerepositories.databinding.FragmentMainBinding
import com.example.googlerepositories.ui.base.BaseFragment
import com.example.googlerepositories.util.extensions.Resource
import com.example.googlerepositories.util.extensions.observe
import com.example.googlerepositories.util.extensions.setVisible
import com.example.googlerepositories.util.extensions.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : BaseFragment() {

    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var _binding: FragmentMainBinding
    private val binding get() = _binding

    private val repositoryAdapter by lazy {
        RepositoryAdapter(
            listener = { repository ->
                repository.apply {
                    val htmlUrl = htmlUrl ?: owner.ownerHtmlUrl
                    if (htmlUrl != null) showDialog(htmlUrl)
                    else toast(getString(R.string.repository_without_url))
                }
            }
        )
    }

    private var searchJob: Job? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        trackMethod({})

        _binding = FragmentMainBinding.inflate(inflater, container, false)

        setupFallbackBehaviour()
        setupRecyclerView()

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        trackMethod({})

        super.onCreate(savedInstanceState)

        setupObservers()
    }

    override fun setupFallbackBehaviour() {
        trackMethod({})

        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().finish()
            }
        })
    }

    private fun setupObservers() {
        trackMethod({})

        with(mainViewModel) {
            observe(repositories) {
                searchJob?.cancel()
                searchJob = lifecycleScope.launch {
                    it.collect {
                        when (it) {
                            is Resource.Loading -> binding.repositoriesProgressBar.isVisible = true
                            is Resource.Success -> {
                                repositoryAdapter.repositories = it.data ?: emptyList()
                                setupViews()
                            }
                            is Resource.Error -> {
                                binding.repositoriesProgressBar.isVisible = false
                                toast(it.error?.localizedMessage ?: "")
                            }
                        }
                    }
                }
            }
        }
    }

    private fun setupRecyclerView() {
        trackMethod({})

        binding.repositoriesRecyclerView.adapter = repositoryAdapter
    }

    private fun setupViews() {
        with(binding) {
            repositoriesProgressBar.setVisible(false)

            if (repositoryAdapter.repositories.isEmpty()) {
                repositoriesRecyclerView.setVisible(false)
                noRepositoriesTextView.setVisible(true)
            } else {
                noRepositoriesTextView.setVisible(false)
                repositoriesRecyclerView.setVisible(true)
            }
        }
    }

    private fun showDialog(url: String) {
        AlertDialog.Builder(requireActivity())
            .setTitle(R.string.navigation_to_url_dialog_title)
            .setPositiveButton(android.R.string.ok) { _, _ ->
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(intent)
            }
            .setNegativeButton(android.R.string.cancel) { _, _ ->
            }
            .setCancelable(true)
            .create()
            .show()
    }
}