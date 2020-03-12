package com.erdin.connectin.hiring

data class AddHireResponse(val status: Int?, val result: DataRes?) {
    data class DataRes(val affectedRows: Int)
}