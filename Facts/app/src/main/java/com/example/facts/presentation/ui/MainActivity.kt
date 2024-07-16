package com.example.facts.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.facts.datasourse.remote.FactsRemoteDataSource
import com.example.facts.datasourse.repository.FactRepository
import com.example.facts.domain.usecase.GetFactsUseCase
import com.example.facts.presentation.ui.theme.FactsTheme
import com.example.facts.presentation.viewmodel.FactListViewModel
import com.example.facts.presentation.viewmodel.FactListViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val baseUrl = "https://catfact.ninja/"
            val factsRemoteDataSource = FactsRemoteDataSource.create(baseUrl)
            val factRepository = FactRepository(factsRemoteDataSource)
            val getFactsUseCase = GetFactsUseCase(factRepository)
            val viewModel = viewModel<FactListViewModel>(factory = FactListViewModelFactory(getFactsUseCase))
            FactsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    FactScreen(viewModel)
                }
            }
        }
    }
}