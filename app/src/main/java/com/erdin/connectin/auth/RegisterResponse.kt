package com.erdin.connectin.auth

import com.google.gson.annotations.SerializedName

data class RegisterResponse(val status: Int?, val result: DataRes?) {
    data class DataRes(
        @SerializedName("msg")
        val message: String?,
        val token: String?)
}