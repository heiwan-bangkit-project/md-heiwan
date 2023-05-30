package com.c23ps291.heiwan.di

import android.content.Context
import com.c23ps291.heiwan.data.HeiwanRepository
import com.c23ps291.heiwan.data.remote.ApiConfig

object Injection {
    fun provideRepository(context: Context): HeiwanRepository {
//        val database = HeiwanDatabase.getDatabase(context)
        val apiService = ApiConfig.getApiService()
        return HeiwanRepository(apiService)
    }
}