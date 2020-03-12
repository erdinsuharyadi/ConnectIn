package com.erdin.connectin.auth

import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthApiService {
    @FormUrlEncoded
    @POST("/auth/login")
    suspend fun loginRequest(@Field("username") username: String?,
                             @Field("password") password: String?) : LoginResponse

    @FormUrlEncoded
        @POST("/auth/register")
    suspend fun regisRequest(@Field("username") username: String?,
                     @Field("password") password: String?,
                     @Field("email") email: String?,
                     @Field("name") fullname: String?,
                     @Field("level") level: String?) : RegisterResponse

}