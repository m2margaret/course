package com.example.facts.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.facts.datasourse.model.Fact
import com.example.facts.domain.usecase.GetFactsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FactListViewModel(private val getFactsUseCase: GetFactsUseCase) : ViewModel() {
    private val _facts = MutableLiveData<List<Fact>>()
    val facts: LiveData<List<Fact>> = _facts

    fun loadFacts(count: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val facts = getFactsUseCase.execute(count)
            //Log.d("FactListViewModel", "Полученные факты: $facts") // Выводим список в лог
            _facts.postValue(facts)
        }
    }
}