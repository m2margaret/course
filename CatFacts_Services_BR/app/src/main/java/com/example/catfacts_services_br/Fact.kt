package com.example.catfacts_services_br

import kotlinx.serialization.Serializable

@Serializable
data class Fact(
    val fact: String,
    val length: Int
)