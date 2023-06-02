package com.c23ps291.heiwan.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.c23ps291.heiwan.data.HeiwanRepository

class HomeViewModel(repository: HeiwanRepository) : ViewModel() {

    private val repo = repository

    fun getListAnimal() = repo.getAnimals().asLiveData().cachedIn(viewModelScope)
}