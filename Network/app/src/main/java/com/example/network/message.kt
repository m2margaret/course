package com.example.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class message(
    @SerialName("userId")
    val userId: Int,
    @SerialName("id")
    val id: Int,
    @SerialName("title")
    val titleMessage: String,
    @SerialName("body")
    val bodyMessage: String
)