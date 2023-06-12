package com.c23ps291.heiwan.data.model

import com.google.gson.annotations.SerializedName

data class SellerResponse(

    @field:SerializedName("success")
    val success: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("data")
    val seller: List<User?>? = null,


    )

