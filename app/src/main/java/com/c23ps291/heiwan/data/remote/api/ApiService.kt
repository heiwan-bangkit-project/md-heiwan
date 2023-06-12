package com.c23ps291.heiwan.data.remote.api

import com.c23ps291.heiwan.data.model.AddEditDeleteResponse
import com.c23ps291.heiwan.data.model.AnimalResponse
import com.c23ps291.heiwan.data.model.DeleteAnimal
import com.c23ps291.heiwan.data.model.DetailAnimalResponse
import com.c23ps291.heiwan.data.model.PredResponse
import com.c23ps291.heiwan.data.model.ProductsResponse
import com.c23ps291.heiwan.data.model.Seller
import com.c23ps291.heiwan.data.model.SellerResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ApiService {

    @GET("get-animals")
    suspend fun getAnimals(
        @Query("page") page: Int? = null,
    ): AnimalResponse

    @GET("get-animal-by-name")
    suspend fun getAnimalsByName(
        @Query("name") page: String? = null,
    ): AnimalResponse

    @GET("get-animal")
    suspend fun getAnimalById(
        @Query("id") id: String? = null,
    ): DetailAnimalResponse

    @Multipart
    @POST("pred")
    suspend fun getPred(
        @Part image: MultipartBody.Part,
    ): PredResponse

    @Multipart
    @POST("store-seller")
    suspend fun setAddSeller(
        @Part("uuid") userId: RequestBody,
        @Part("name") name: RequestBody,
        @Part("email") email: RequestBody,
        @Part("phone") phone: RequestBody,
    ): AddEditDeleteResponse

    @POST("store-seller")
    suspend fun setAddSellerBody(
        @Body seller: Seller,
    ): AddEditDeleteResponse

    @Multipart
    @POST("store-animal")
    suspend fun setAddAnimal(
        @Part("name") name: RequestBody,
        @Part("description") description: RequestBody,
        @Part("price") price: RequestBody,
        @Part file: MultipartBody.Part,
        @Part("uuid") userId: RequestBody,
    ): AddEditDeleteResponse

    @Multipart
    @POST("update-animal")
    suspend fun setUpdateAnimal(
        @Part("id") id: RequestBody,
        @Part("name") name: RequestBody,
        @Part("description") description: RequestBody,
        @Part("price") price: RequestBody,
        @Part file: MultipartBody.Part,
        @Part("uuid") userId: RequestBody,
    ): AddEditDeleteResponse

    @POST("delete-animal")
    suspend fun setDeleteAnimal(
        @Body deleteAnimal: DeleteAnimal,
    ): AddEditDeleteResponse

    @GET("get-seller")
    suspend fun getSellerById(
        @Query("uuid") id: String? = null,
    ): SellerResponse

    @GET("products")
    suspend fun getProducts(
        @Query("uuid") id: String? = null,
    ): ProductsResponse


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