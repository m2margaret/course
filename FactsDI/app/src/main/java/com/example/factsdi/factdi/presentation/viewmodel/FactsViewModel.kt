package com.example.factsdi.factdi.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.factsdi.factdi.domain.GetFactsUseCase
import com.example.factsdi.factdi.data.model.Fact
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class FactListViewModel @Inject constructor(private val getFactsUseCase: GetFactsUseCase) : ViewModel() {
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