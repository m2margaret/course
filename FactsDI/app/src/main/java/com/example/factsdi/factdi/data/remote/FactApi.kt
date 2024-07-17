package com.example.factsdi.factdi.data.remote

import com.example.factsdi.factdi.data.model.Fact
import retrofit2.http.GET

interface FactApi {
    @GET("fact")
    suspend fun getFact(): Fact
}