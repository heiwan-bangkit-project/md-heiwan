package com.c23ps291.heiwan.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Animal(

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("name")
    val name: String?,

    @field:SerializedName("description")
    val description: String?,

    @field:SerializedName("image")
    val image: String?,

    @field:SerializedName("price")
    val price: String?,

    @field:SerializedName("uuid")
    val uuid: String?,

    ) : Parcelable
