package com.c23ps291.heiwan.data

import com.c23ps291.heiwan.data.model.AnimalResponse
import com.c23ps291.heiwan.data.model.DetailAnimalResponse
import com.c23ps291.heiwan.data.remote.ApiService
import com.c23ps291.heiwan.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException

class HeiwanRepository(
    private val apiService: ApiService
) {
    fun getAnimals(): Flow<Resource<AnimalResponse>> = flow {
        try {
            emit(Resource.Loading())
            val response = apiService.getAnimals()
            emit(Resource.Success(response))
        } catch (exception: Exception) {
            val e = (exception as? HttpException)?.response()?.errorBody()?.string()
            emit(Resource.Error(e.toString()))
        }
    }.flowOn(Dispatchers.IO)

    fun getAnimalsByName(name: String): Flow<Resource<AnimalResponse>> = flow {
        try {
            emit(Resource.Loading())
            val response = apiService.getAnimals()// ganti ke getbyname kl udah jdi
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


}