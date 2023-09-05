package com.dave.krom.network_request.interfaces

import com.dave.krom.data.DbAnime
import com.dave.krom.data.DbImageResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*


interface Interface {


    @Multipart
    @POST("search")
    suspend fun uploadImage(
        @Part file: MultipartBody.Part
    ): Response<DbImageResponse>

    @GET("v4/top/anime")
    suspend fun getAnime(): Response<DbAnime>



}