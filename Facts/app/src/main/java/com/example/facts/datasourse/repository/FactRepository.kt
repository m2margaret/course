package com.example.facts.datasourse.repository

import com.example.facts.datasourse.model.Fact
import com.example.facts.datasourse.remote.FactsRemoteDataSource

class FactRepository(private val factsAPI: FactsRemoteDataSource) {
    suspend fun getFacts(count: Int): List<Fact> {
        return factsAPI.getFacts(count)
    }
}