package com.example.factsdi.factdi.domain

import com.example.factsdi.factdi.data.model.Fact
import com.example.factsdi.factdi.data.repository.FactRepository
import javax.inject.Inject

class GetFactsUseCase @Inject constructor(private val factRepository: FactRepository) {
    suspend fun execute(count: Int): List<Fact> {
        return factRepository.getFacts(count)
    }
}