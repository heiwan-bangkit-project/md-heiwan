package com.c23ps291.heiwan.data.model

import com.google.gson.annotations.SerializedName

data class PredResponse(

    @field:SerializedName("success")
    val success: String,

    @field:SerializedName("message")
    val message: String?,

    @field:SerializedName("data")
    val data: String,

    )
