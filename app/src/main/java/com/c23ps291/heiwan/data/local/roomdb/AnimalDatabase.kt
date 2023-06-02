package com.c23ps291.heiwan.data.local.roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.c23ps291.heiwan.data.local.AnimalDao
import com.c23ps291.heiwan.data.local.RemoteKeysDao
import com.c23ps291.heiwan.data.local.entity.AnimalEntity
import com.c23ps291.heiwan.data.local.entity.RemoteKeys

@Database(
    entities = [AnimalEntity::class, RemoteKeys::class],
    version = 2,
    exportSchema = false
)
abstract class AnimalDatabase : RoomDatabase() {

    abstract fun animalDao(): AnimalDao
    abstract fun remoteKeysDao(): RemoteKeysDao

    companion object {
        @Volatile
        private var INSTANCE: AnimalDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): AnimalDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AnimalDatabase::class.java, "story_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}