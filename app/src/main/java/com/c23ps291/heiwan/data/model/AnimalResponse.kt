package com.c23ps291.heiwan.data.model

import com.google.gson.annotations.SerializedName

data class AnimalResponse(
    @field:SerializedName("success")
    val success: String,
    @field:SerializedName("message")
    val message: String?,
    @field:SerializedName("data")
    val data: List<Animal>,
)
