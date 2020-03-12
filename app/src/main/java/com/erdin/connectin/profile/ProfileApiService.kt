package com.erdin.connectin.profile

import com.erdin.connectin.engineers.EngineersResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ProfileApiService {
    @GET("/company/user/{username}")
    suspend fun getUserProfile(@Path("username") username: String?) : ProfileResponse
}