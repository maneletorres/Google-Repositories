package com.example.googlerepositories.ui.main

import android.os.Bundle
import com.example.googlerepositories.databinding.ActivityMainBinding
import com.example.googlerepositories.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViewModelBinding()
    }

    private fun setupViewModelBinding() {
        trackMethod({})

        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
    }
}