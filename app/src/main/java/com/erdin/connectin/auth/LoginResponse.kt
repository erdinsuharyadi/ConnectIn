package com.erdin.connectin.auth

data class LoginResponse(val result: DataRes?) {
    data class DataRes(val token: String,
                       val level: String,
                       val idCompany: Int)
}
