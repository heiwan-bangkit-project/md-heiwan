package com.c23ps291.heiwan.data.model

import com.google.gson.annotations.SerializedName

data class UploadResponse(
    @field:SerializedName("success")
    val success: String?,
    @field:SerializedName("message")
    val message: String?
)
