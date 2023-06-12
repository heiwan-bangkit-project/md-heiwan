package com.c23ps291.heiwan.data.model

import com.google.gson.annotations.SerializedName

data class AddEditDeleteResponse(

    @field:SerializedName("data")
    val data: Data? = null,

    @field:SerializedName("success")
    val success: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null,
)

data class Data(

    @field:SerializedName("affectedRows")
    val affectedRows: Int? = null,

    )
