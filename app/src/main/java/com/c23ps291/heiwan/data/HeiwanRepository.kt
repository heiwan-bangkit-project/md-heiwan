package com.c23ps291.heiwan.data

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.c23ps291.heiwan.data.local.entity.AnimalEntity
import com.c23ps291.heiwan.data.local.roomdb.AnimalDatabase
import com.c23ps291.heiwan.data.model.AddEditDeleteResponse
import com.c23ps291.heiwan.data.model.AnimalResponse
import com.c23ps291.heiwan.data.model.DeleteAnimal
import com.c23ps291.heiwan.data.model.DetailAnimalResponse
import com.c23ps291.heiwan.data.model.PredResponse
import com.c23ps291.heiwan.data.model.ProductsResponse
import com.c23ps291.heiwan.data.model.Seller
import com.c23ps291.heiwan.data.model.SellerResponse
import com.c23ps291.heiwan.data.remote.AnimalRemoteMediator
import com.c23ps291.heiwan.data.remote.api.ApiService
import com.c23ps291.heiwan.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException

@OptIn(ExperimentalPagingApi::class)
class HeiwanRepository(
    private val animalDatabase: AnimalDatabase,
    private val apiService: ApiService,
) {

    fun getAnimals(): Flow<PagingData<AnimalEntity>> {

        return Pager(
            config = PagingConfig(pageSize = 10),
            remoteMediator = AnimalRemoteMediator(
                animalDatabase,
                apiService
            ),
            pagingSourceFactory = {
                animalDatabase.animalDao().getAnimals()
            }
        ).flow
    }

    fun getAnimalsByName(name: String): Flow<Resource<AnimalResponse>> = flow {
        try {
            emit(Resource.Loading())
            val response = apiService.getAnimalsByName(name)
            emit(Resource.Success(response))
        } catch (exception: Exception) {
            val e = (exception as? HttpException)?.response()?.errorBody()?.string()
            emit(Resource.Error(e.toString()))
        }
    }.flowOn(Dispatchers.IO)

    fun getDetailAnimal(id: String): Flow<Resource<DetailAnimalResponse>> = flow {
        try {
            emit(Resource.Loading())
            val response = apiService.getAnimalById(id)
            emit(Resource.Success(response))
        } catch (exception: Exception) {
            val e = (exception as? HttpException)?.response()?.errorBody()?.string()
            emit(Resource.Error(e.toString()))
        }
    }.flowOn(Dispatchers.IO)

    fun getPred(image: MultipartBody.Part): Flow<Resource<PredResponse>> = flow {
        try {
            emit(Resource.Loading())
            val response = apiService.getPred(image)
            emit(Resource.Success(response))
        } catch (exception: Exception) {
            val e = (exception as? HttpException)?.response()?.errorBody()?.string()
            emit(Resource.Error(e.toString()))
        }
    }.flowOn(Dispatchers.IO)

    fun setAddSeller(
        seller: Seller,
    ): Flow<Resource<AddEditDeleteResponse>> = flow {
        try {
            emit(Resource.Loading())
            val response = apiService.setAddSellerBody(seller)
            emit(Resource.Success(response))
        } catch (exception: Exception) {
            val e = (exception as? HttpException)?.response()?.errorBody()?.string()
            emit(Resource.Error(e.toString()))
        }
    }.flowOn(Dispatchers.IO)

    fun setAddAnimal(
        name: RequestBody,
        description: RequestBody,
        price: RequestBody,
        file: MultipartBody.Part,
        userId: RequestBody,
    ): Flow<Resource<AddEditDeleteResponse>> = flow {
        try {
            emit(Resource.Loading())
            val response = apiService.setAddAnimal(name, description, price, file, userId)
            emit(Resource.Success(response))
        } catch (exception: Exception) {
            val e = (exception as? HttpException)?.response()?.errorBody()?.string()
            emit(Resource.Error(e.toString()))
        }
    }.flowOn(Dispatchers.IO)


    fun setUpdateAnimal(
        id: RequestBody,
        name: RequestBody,
        description: RequestBody,
        price: RequestBody,
        file: MultipartBody.Part,
        userId: RequestBody,
    ): Flow<Resource<AddEditDeleteResponse>> = flow {
        try {
            emit(Resource.Loading())
            val response = apiService.setUpdateAnimal(id, name, description, price, file, userId)
            emit(Resource.Success(response))
        } catch (exception: Exception) {
            Log.e("TAG", "setUpdateAnimal: exception $exception")
            val e = (exception as? HttpException)?.response()?.errorBody()?.string()
            Log.e("TAG", "setUpdateAnimal: exception $exception")
            emit(Resource.Error(e.toString()))
        }
    }.flowOn(Dispatchers.IO)

    fun setDeleteAnimal(
        deleteAnimal: DeleteAnimal,
    ): Flow<Resource<AddEditDeleteResponse>> = flow {
        try {
            emit(Resource.Loading())
            val response = apiService.setDeleteAnimal(deleteAnimal)
            emit(Resource.Success(response))
        } catch (exception: Exception) {
            val e = (exception as? HttpException)?.response()?.errorBody()?.string()
            emit(Resource.Error(e.toString()))
        }
    }.flowOn(Dispatchers.IO)

    fun getDetailSeller(id: String): Flow<Resource<SellerResponse>> = flow {
        try {
            emit(Resource.Loading())
            val response = apiService.getSellerById(id)
            emit(Resource.Success(response))
        } catch (exception: Exception) {
            val e = (exception as? HttpException)?.response()?.errorBody()?.string()
            emit(Resource.Error(e.toString()))
        }
    }.flowOn(Dispatchers.IO)


    fun getProducts(id: String): Flow<Resource<ProductsResponse>> = flow {
        try {
            emit(Resource.Loading())
            val response = apiService.getProducts(id)
            emit(Resource.Success(response))
        } catch (exception: Exception) {
            val e = (exception as? HttpException)?.response()?.errorBody()?.string()
            emit(Resource.Error(e.toString()))
        }
    }.flowOn(Dispatchers.IO)
}