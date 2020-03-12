package com.erdin.connectin.projects

import com.google.gson.annotations.SerializedName

data class ProjectsResponse (val result: List<Project>) {

    data class Project(
        @SerializedName("id_project")
        val idProject: Int?,
        @SerializedName("id_user")
        val idUser: String?,
        @SerializedName("id_company")
        val idCompany: Int?,
        @SerializedName("project_name")
        val projectName: String?,
        val description: String?,
        val period: String?,
        val deadline: String?,
        val status: String?,
        val progress: String?,
        @SerializedName("createdAt")
        val created: String?)
}