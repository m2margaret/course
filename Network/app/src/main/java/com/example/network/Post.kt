package com.example.network

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface Post {
    @GET("posts")
    suspend fun getPosts(): List<message> //метод возвращает лист из сообщений

    @POST("posts")
    suspend fun savePosts(
        @Body saveMessage: message
    ): Call<Post>
}