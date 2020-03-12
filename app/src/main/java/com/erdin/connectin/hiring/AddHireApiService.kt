package com.erdin.connectin.hiring

import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AddHireApiService {
    @FormUrlEncoded
    @POST("/hire")
    suspend fun addHire(@Field("id_project") idProject: String?,
                @Field("id_eng") idEngineer: String?,
                @Field("fee") fee: String?,
                @Field("project_job") projectJob: String?) : AddHireResponse
}