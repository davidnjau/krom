package com.dave.krom.network_request.interfaces

import com.dave.krom.data.DbAnime
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*


interface Interface {


//    @POST("auth/login/")
//    suspend fun loginUser(@Body user: UserLogin): Response<AuthResponse>

    @GET("v4/top/anime")
    suspend fun getAnime(): Response<DbAnime>



}