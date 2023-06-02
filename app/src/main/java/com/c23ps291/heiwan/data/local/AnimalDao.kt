package com.c23ps291.heiwan.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.c23ps291.heiwan.data.local.entity.AnimalEntity

@Dao
interface AnimalDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnimal(story: List<AnimalEntity>)

    @Query("SELECT * FROM animal")
    fun getAnimals(): PagingSource<Int, AnimalEntity>

    @Query("DELETE FROM animal")
    fun deleteAll()
}