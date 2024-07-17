package com.example.factsdi.factdi.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.factsdi.factdi.domain.GetFactsUseCase
import javax.inject.Inject

class FactListViewModelFactory @Inject constructor(private val getFactsUseCase: GetFactsUseCase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FactListViewModel::class.java)) {
            return FactListViewModel(getFactsUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}