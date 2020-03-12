package com.erdin.connectin.offers

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OffersModel(val idProject: String?,
                       val idUser: String?,
                       val idCompany: String?,
                       val companyName: String?,
                       val username: String?,
                       val projectName: String?,
                       val description: String?,
                       val period: String?,
                       val deadline: String?,
                       val status: String?,
                       val idProjectEng: String?,
                       val idEngineer: String?,
                       val nameEngineer: String?,
                       val fee: String?,
                       val projectJob: String?,
                       val statusProject: String?,
                       val created: String?,
                       val photo: String?) : Parcelable