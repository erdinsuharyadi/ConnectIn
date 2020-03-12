package com.erdin.connectin.offers

import retrofit2.http.GET
import retrofit2.http.Path

interface OffersApiService {
    @GET("/project/comp/{idCompany}")
    suspend fun getOfferList(@Path("idCompany") idCompany: String?) : OffersResponse
}