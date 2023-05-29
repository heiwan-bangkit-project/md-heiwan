package com.c23ps291.heiwan.data.model

data class AnimalResponse(
    val success: String,
    val message: String?,
    val data: List<Animal>,
)
