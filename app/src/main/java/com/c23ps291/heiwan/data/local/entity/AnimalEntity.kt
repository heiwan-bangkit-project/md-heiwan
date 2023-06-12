package com.c23ps291.heiwan.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "animal")
data class AnimalEntity(

    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo("name")
    val name: String?,

    @ColumnInfo("description")
    val description: String?,

    @ColumnInfo("image")
    val image: String?,

    @ColumnInfo("price")
    val price: String?,

    ) : Parcelable
