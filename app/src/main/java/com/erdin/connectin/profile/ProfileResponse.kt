package com.erdin.connectin.profile

import com.google.gson.annotations.SerializedName

data class ProfileResponse(val status: Int?, val result: List<DataRes>) {
    data class DataRes(@SerializedName("id_user")
                       val idUser: String,
                       val username: String,
                       val email: String,
                       val name: String,
                       val status: String,
                       val level: String,
                       @SerializedName("id_company")
                       val idCompany: Int,
                       @SerializedName("name_company")
                       val nameCompany: String,
                       val logo: String,
                       val location: String,
                       val description: String)
}