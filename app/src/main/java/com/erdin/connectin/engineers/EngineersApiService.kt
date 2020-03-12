package com.erdin.connectin.engineers

import retrofit2.http.GET

interface EngineersApiService {
    @GET("/engineer/")
    suspend fun getEngineer() : EngineersResponse
}