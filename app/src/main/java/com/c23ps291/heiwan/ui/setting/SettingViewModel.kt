package com.c23ps291.heiwan.ui.setting

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.c23ps291.heiwan.data.SettingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SettingViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = SettingRepository(application)

    fun getThemeSettings(): LiveData<Int> {
        return repository.getThemeSetting().asLiveData(Dispatchers.IO)
    }

    fun saveThemeSetting(theme: Int) = viewModelScope.launch {
        repository.saveThemeSetting(theme)
    }

}