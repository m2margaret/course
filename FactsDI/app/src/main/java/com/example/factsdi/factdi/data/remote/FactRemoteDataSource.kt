package com.example.factsdi.factdi.data.remote

import com.example.factsdi.factdi.data.model.Fact
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.Retrofit
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType

class FactsRemoteDataSource(private val retrofit: Retrofit) { //класс, который отвечает за получение данных о фактах с API
    private val factApi: FactApi = retrofit.create(FactApi::class.java)

    suspend fun getFacts(count: Int): List<Fact> { //функция, которая получает список фактов с API
        val facts = mutableListOf<Fact>()
        repeat(count) {
            facts.add(factApi.getFact())
        }
        return facts //список facts
    }

    companion object {
        fun create(baseUrl: String): FactsRemoteDataSource {
            val json = Json { ignoreUnknownKeys = true }
            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
                .build()
            return FactsRemoteDataSource(retrofit)
        }
    }
}