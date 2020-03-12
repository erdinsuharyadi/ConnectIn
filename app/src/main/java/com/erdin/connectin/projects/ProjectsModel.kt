package com.erdin.connectin.projects

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProjectsModel(
    val idProject: Int?,
    val idUser: String,
    val idCompany: Int?,
    val projectName: String,
    val description: String,
    val period: String,
    val deadline: String,
    val status: String,
    val progress: String,
    val created: String) : Parcelable