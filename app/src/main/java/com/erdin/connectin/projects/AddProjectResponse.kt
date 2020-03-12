package com.erdin.connectin.projects

data class AddProjectResponse (val status: Int?, val result: DataRes?) {
    data class DataRes(val affectedRows: Int)
}