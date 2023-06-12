package com.c23ps291.heiwan.data.model

import com.google.gson.annotations.SerializedName

data class ProductsResponse(

    @field:SerializedName("data")
    val data: List<Animal>,

    @field:SerializedName("success")
    val success: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null,
)


