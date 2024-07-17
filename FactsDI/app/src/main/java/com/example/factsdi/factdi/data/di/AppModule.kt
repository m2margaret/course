package com.example.factsdi.factdi.data.di

import com.example.factsdi.factdi.data.remote.FactsRemoteDataSource
import com.example.factsdi.factdi.data.repository.FactRepository
import com.example.factsdi.factdi.domain.GetFactsUseCase
import com.example.factsdi.factdi.presentation.viewmodel.FactListViewModelFactory
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule { //  Не абстрактный класс

    @Provides
    @Singleton
    fun provideFactRepository(factsAPI: FactsRemoteDataSource): FactRepository {
        return FactRepository(factsAPI)
    }

    @Provides
    @Singleton
    fun provideGetFactsUseCase(factRepository: FactRepository): GetFactsUseCase {
        return GetFactsUseCase(factRepository)
    }

    @Provides
    @Singleton
    fun provideFactListViewModelFactory(getFactsUseCase: GetFactsUseCase): FactListViewModelFactory {
        return FactListViewModelFactory(getFactsUseCase)
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val json = Json { ignoreUnknownKeys = true }
        return Retrofit.Builder()
            .baseUrl("https://catfact.ninja/")
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    @Provides
    @Singleton
    fun provideFactsRemoteDataSource(retrofit: Retrofit): FactsRemoteDataSource {
        return FactsRemoteDataSource.create("https://catfact.ninja/")
    }
}