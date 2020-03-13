package com.erdin.connectin.engineers

import retrofit2.http.GET
import retrofit2.http.Query

interface EngineersApiService {
    @GET("/engineer")
    suspend fun getEngineer(@Query("skill") skill: String?) : EngineersResponse
}