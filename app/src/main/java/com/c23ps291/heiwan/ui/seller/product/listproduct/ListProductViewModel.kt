package com.c23ps291.heiwan.ui.seller.product.listproduct

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.c23ps291.heiwan.data.HeiwanRepository

class ListProductViewModel(repository: HeiwanRepository) : ViewModel() {

    private val repo = repository

    fun getProducts(id: String) = repo.getProducts(id).asLiveData()
}