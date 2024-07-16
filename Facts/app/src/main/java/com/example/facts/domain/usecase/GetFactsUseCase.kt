package com.example.facts.domain.usecase

import com.example.facts.datasourse.model.Fact
import com.example.facts.datasourse.repository.FactRepository

class GetFactsUseCase(private val factRepository: FactRepository) {
    suspend fun execute(count: Int): List<Fact> {
        return factRepository.getFacts(count)
    }
}