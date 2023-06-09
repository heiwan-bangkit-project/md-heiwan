package com.c23ps291.heiwan.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.c23ps291.heiwan.data.HeiwanRepository
import okhttp3.MultipartBody

class SearchViewModel(repository: HeiwanRepository) : ViewModel() {

    private val repo = repository

    fun getListAnimal(name: String) = repo.getAnimalsByName(name).asLiveData()

    fun getPred(image: MultipartBody.Part) = repo.getPred(image).asLiveData()
}