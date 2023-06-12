package com.c23ps291.heiwan.ui.seller.product.crudproduct

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.c23ps291.heiwan.data.HeiwanRepository
import com.c23ps291.heiwan.data.model.DeleteAnimal
import com.c23ps291.heiwan.data.model.Seller
import okhttp3.MultipartBody
import okhttp3.RequestBody

class AddUpdateProductViewModel(repository: HeiwanRepository) : ViewModel() {

    private val repo = repository

    fun getSeller(id: String) = repo.getDetailSeller(id).asLiveData()

    fun getAnimal(id: String) = repo.getDetailAnimal(id).asLiveData()

    fun setDeleteAnimal(
        deleteAnimal: DeleteAnimal,
    ) = repo.setDeleteAnimal(deleteAnimal).asLiveData()

    fun setAddSeller(
        seller: Seller,
    ) = repo.setAddSeller(seller).asLiveData()

    fun setAddAnimal(
        name: RequestBody,
        description: RequestBody,
        price: RequestBody,
        file: MultipartBody.Part,
        userId: RequestBody,
    ) = repo.setAddAnimal(name, description, price, file, userId).asLiveData()

    fun setUpdateAnimal(
        id: RequestBody,
        name: RequestBody,
        description: RequestBody,
        price: RequestBody,
        file: MultipartBody.Part,
        userId: RequestBody,
    ) = repo.setUpdateAnimal(id, name, description, price, file, userId).asLiveData()
}