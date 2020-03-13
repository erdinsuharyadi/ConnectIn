package com.erdin.connectin.ui.offers

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.erdin.connectin.SharedPreference
import com.erdin.connectin.offers.OffersApiService
import com.erdin.connectin.offers.OffersModel
import com.erdin.connectin.offers.OffersResponse
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class OffersViewModel : ViewModel(), CoroutineScope {

    val offersLiveData = MutableLiveData<List<OffersModel>>()
    val isLoadingLiveData = MutableLiveData<Boolean>()

    private lateinit var mContext: Context
    private lateinit var service: OffersApiService
    private lateinit var sharedPreference: SharedPreference

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setContext(context: Context) {
        this.mContext = context
    }

    fun setOffersService(service: OffersApiService?) {
        if (service != null) {
            this.service = service
        }
    }

    fun offersApi() {
        launch {
            isLoadingLiveData.value = true
            val response = withContext(Dispatchers.IO) {
                try {
                    sharedPreference = SharedPreference(mContext)
                    service?.getOfferList(sharedPreference.getValueInt(SharedPreference.KEY_COMP).toString())
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }

            if (response is OffersResponse) {
                val list = response.result?.map {
                    OffersModel(it.idProject.orEmpty(),
                        it.idUser.orEmpty(),
                        it.idCompany.orEmpty(),
                        it.companyName.orEmpty(),
                        it.username.orEmpty(),
                        it.projectName.orEmpty(),
                        it.description.orEmpty(),
                        it.period.orEmpty(),
                        it.deadline.orEmpty(),
                        it.status.orEmpty(),
                        it.idProjectEng.orEmpty(),
                        it.idEngineer.orEmpty(),
                        it.nameEngineer.orEmpty(),
                        it.fee.orEmpty(),
                        it.projectJob.orEmpty(),
                        it.statusProject.orEmpty(),
                        it.created.orEmpty(),
                        it.photo.orEmpty())
                }

                offersLiveData.value = list
                isLoadingLiveData.value = false

            } else if (response is Throwable) {
                Log.d("errorApi", response.message ?: "Error")
            }
        }
    }
}