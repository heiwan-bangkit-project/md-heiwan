package com.c23ps291.heiwan.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.c23ps291.heiwan.data.HeiwanRepository

class DetailViewModel(repository: HeiwanRepository) : ViewModel() {

    private val repo = repository

    fun getAnimal(id: String) = repo.getDetailAnimal(id).asLiveData()

    fun getSellerByID(uuid: String) = repo.getDetailSeller(uuid).asLiveData()
}