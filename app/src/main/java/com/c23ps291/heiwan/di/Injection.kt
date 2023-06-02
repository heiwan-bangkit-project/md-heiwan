package com.c23ps291.heiwan.di

import android.content.Context
import com.c23ps291.heiwan.data.HeiwanRepository
import com.c23ps291.heiwan.data.local.roomdb.AnimalDatabase
import com.c23ps291.heiwan.data.remote.api.ApiConfig

object Injection {
    fun provideRepository(context: Context): HeiwanRepository {
        val database = AnimalDatabase.getDatabase(context)
        val apiService = ApiConfig.getApiService()
        return HeiwanRepository(database, apiService)
    }
}