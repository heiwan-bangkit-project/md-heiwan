package com.c23ps291.heiwan.data.remote

import com.c23ps291.heiwan.data.model.AnimalResponse
import com.c23ps291.heiwan.data.model.DetailAnimalResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("get-animals")
    suspend fun getAnimals(
        @Header("Authorization") auth: String,
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null,
        @Query("location") location: Int? = null
    ): AnimalResponse

    @GET("get-animal-by-id/{id}")
    suspend fun getAnimalById(
        @Header("Authorization") auth: String,
        @Path("id") id : String
    ) : DetailAnimalResponse

//    @Multipart
//    @POST("store-animal")
//    suspend fun uploadAnimal(
//        @Header("Authorization") auth: String,
//        @Part file: MultipartBody.Part,
//        @Part("description") description: RequestBody
//    ) : UploadResponse

    //    @Multipart
//    @POST("update-animal")
//    suspend fun updateAnimal(
//        @Header("Authorization") auth: String,
//        @Part file: MultipartBody.Part,
//        @Part("description") description: RequestBody
//    ) : UploadResponse

    //    @Multipart
//    @POST("delete-animal")
//    suspend fun updateAnimal(
//        @Header("Authorization") auth: String,
//        @Part file: MultipartBody.Part,
//        @Part("id") description: RequestBody
//    ) : UploadResponse

}