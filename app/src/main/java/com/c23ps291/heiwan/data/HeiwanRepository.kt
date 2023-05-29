package com.c23ps291.heiwan.data

import android.app.Application
import com.c23ps291.heiwan.data.local.ThemeDataStore

class HeiwanRepository(application: Application) {

    private val dataStore: ThemeDataStore

    init {
        dataStore = ThemeDataStore.getInstance(application)
    }

    suspend fun saveThemeSetting(theme: Int) = dataStore.saveThemeSetting(theme)

    fun getThemeSetting() = dataStore.getThemeSetting()
}