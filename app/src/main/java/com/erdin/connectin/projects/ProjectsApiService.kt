package com.erdin.connectin.projects

import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ProjectsApiService {
    @GET("/project/")
    suspend fun getProjectList() : ProjectsResponse

    @FormUrlEncoded
    @POST("/project/")
    suspend fun addProject(@Field("project_name") projectName: String?,
                   @Field("description") projectDesc: String?,
                   @Field("period") period: String?,
                   @Field("deadline") deadline: String?,
                   @Field("id_company") idCompany: String?) : AddProjectResponse
}