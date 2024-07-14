package com.example.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.create
import java.util.concurrent.TimeUnit

//модуль класс, где хранится вся конфигурация ретрофит

object Networking {
    private val baseUrl = "https://jsonplaceholder.typicode.com/"
    private val contentType = "application/json".toMediaType() //строку преобразуем в объект

    private val json = Json //составим сам объект серилизатора

    private fun provideOkhttpClient(): OkHttpClient {
        val client = OkHttpClient.Builder()
        client.connectTimeout(20, TimeUnit.SECONDS)
        client.readTimeout(30, TimeUnit.SECONDS)
        client.writeTimeout(30, TimeUnit.SECONDS)
        return client.build()
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl) //в биледр передаем юрл
        .addConverterFactory(json.asConverterFactory(contentType))  //подключаем конвернтор с помощью метода add… передав туда наш серилизатор преобразованный в фабрику которую будет понимать ретрофит
        .client(provideOkhttpClient())
        .build()

    val messageApi: Post = retrofit.create()
// создать реализацию интерфейса где содержатся методы для работы с апи. Ретрофит реализацию создаёт за нас на основе описания. Надо вызвать метод онкрейт и передать саму реализацию.
}