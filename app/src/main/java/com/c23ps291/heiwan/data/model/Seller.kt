package com.c23ps291.heiwan.data.model

import com.google.gson.annotations.SerializedName

data class Seller(

    @field:SerializedName("uuid")
    val uuid: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("phone")
    val phone: String,


    )
