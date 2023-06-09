package com.c23ps291.heiwan.data

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.c23ps291.heiwan.data.local.entity.AnimalEntity
import com.c23ps291.heiwan.data.local.roomdb.AnimalDatabase
import com.c23ps291.heiwan.data.model.AnimalResponse
import com.c23ps291.heiwan.data.model.DetailAnimalResponse
import com.c23ps291.heiwan.data.model.PredResponse
import com.c23ps291.heiwan.data.remote.AnimalRemoteMediator
import com.c23ps291.heiwan.data.remote.api.ApiService
import com.c23ps291.heiwan.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MultipartBody
import retrofit2.HttpException
import java.io.File

@OptIn(ExperimentalPagingApi::class)
class HeiwanRepository(
    private val animalDatabase: AnimalDatabase,
    private val apiService: ApiService
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
}