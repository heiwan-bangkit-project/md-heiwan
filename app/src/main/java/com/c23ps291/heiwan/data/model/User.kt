package com.c23ps291.heiwan.data.model

import com.google.gson.annotations.SerializedName

data class User(
    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("name")
    val name: String?,

    @field:SerializedName("email")
    val email: String?,

    @field:SerializedName("phone")
    val phoneNumber: String?,

    @field:SerializedName("bookmark")
    val bookmark: List<Animal>
)
