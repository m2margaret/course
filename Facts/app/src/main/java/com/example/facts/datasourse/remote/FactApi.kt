package com.example.facts.datasourse.remote

import com.example.facts.datasourse.model.Fact
import retrofit2.http.GET
import retrofit2.http.Query

interface FactApi {
    @GET("fact")
    suspend fun getFact(): Fact
}