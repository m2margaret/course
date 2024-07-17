package com.example.factsdi.factdi.data.repository

import com.example.factsdi.factdi.data.model.Fact
import com.example.factsdi.factdi.data.remote.FactsRemoteDataSource
import javax.inject.Inject


class FactRepository @Inject constructor(private val factsAPI: FactsRemoteDataSource) {
    suspend fun getFacts(count: Int): List<Fact> {
        return factsAPI.getFacts(count)
    }
}