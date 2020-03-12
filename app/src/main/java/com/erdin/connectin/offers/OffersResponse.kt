package com.erdin.connectin.offers

import com.google.gson.annotations.SerializedName

data class OffersResponse(val result: List<Offer>) {

    data class Offer(
        @SerializedName("id_project")
        val idProject: String?,
        @SerializedName("id_user")
        val idUser: String?,
        @SerializedName("id_company")
        val idCompany: String?,
        @SerializedName("name")
        val companyName: String?,
        val username: String?,
        @SerializedName("project_name")
        val projectName: String?,
        val description: String?,
        val period: String?,
        val deadline: String?,
        val status: String?,
        @SerializedName("id_project_eng")
        val idProjectEng: String?,
        @SerializedName("id_eng")
        val idEngineer: String?,
        @SerializedName("name_eng")
        val nameEngineer: String?,
        val fee: String?,
        @SerializedName("project_job")
        val projectJob: String?,
        @SerializedName("sts_project_eng")
        val statusProject: String?,
        @SerializedName("createProjEng")
        val created: String?,
        val photo: String?)
}