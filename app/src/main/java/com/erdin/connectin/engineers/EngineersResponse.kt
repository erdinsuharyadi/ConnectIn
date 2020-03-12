package com.erdin.connectin.engineers

import com.google.gson.annotations.SerializedName

data class EngineersResponse(val result: List<Engineer>) {

    data class Engineer(
        @SerializedName("id_eng")
        val idEngineer: String?,
        @SerializedName("name")
        val nameEngineer: String?,
        @SerializedName("id_user")
        val idUser: String?,
        val email: String?,
        val photo: String?,
        val job: String?,
        val dob: String?,
        @SerializedName("skill")
        val nameSkill: String?,
        val showcase: String?,
        val location: String?,
        val totalProject: String?,
        val successProject: String?,
        val rateSuccess: String?)

}