package com.example.facts.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.facts.domain.usecase.GetFactsUseCase

class FactListViewModelFactory(private val getFactsUseCase: GetFactsUseCase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FactListViewModel::class.java)) {
            return FactListViewModel(getFactsUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}