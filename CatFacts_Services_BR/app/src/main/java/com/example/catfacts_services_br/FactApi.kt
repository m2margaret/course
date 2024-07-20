package com.example.catfacts_services_br

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface FactApi {
    @GET("facts")
    suspend fun getFacts(): List<Fact>

    @POST("facts")
    suspend fun postFact(
        @Body fact: String,
        @Query("length") factLength: Int
    ): Response<List<Fact>>
}