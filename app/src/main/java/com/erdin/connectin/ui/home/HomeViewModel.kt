package com.erdin.connectin.ui.home

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.erdin.connectin.ApiClient
import com.erdin.connectin.auth.AuthApiService
import com.erdin.connectin.engineers.EngineersAdapter
import com.erdin.connectin.engineers.EngineersApiService
import com.erdin.connectin.engineers.EngineersModel
import com.erdin.connectin.engineers.EngineersResponse
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class HomeViewModel : ViewModel(), CoroutineScope {

    val engineerLiveData = MutableLiveData<List<EngineersModel>>()
    val isLoadingLiveData = MutableLiveData<Boolean>()

    private lateinit var mContext: Context
    private lateinit var service: EngineersApiService

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setContext(context: Context) {
        this.mContext = context
    }

    fun setEngineerService(service: EngineersApiService?) {
        if (service != null) {
            this.service = service
        }
    }

    fun engineerListApi(skill: String?) {
        launch {
            isLoadingLiveData.value = true
            val response = withContext(Dispatchers.IO) {
                try {
                    service.getEngineer(skill)
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }

            if (response is EngineersResponse) {

                val list = response.result.map {
                    EngineersModel(it.idEngineer.orEmpty(),it.nameEngineer.orEmpty(),
                        it.idUser.orEmpty(),it.email.orEmpty(),it.photo.orEmpty(),it.job.orEmpty(),
                        it.nameSkill.orEmpty(), it.dob.orEmpty(), it.showcase.orEmpty(), it.location.orEmpty())
                }

                engineerLiveData.value = list
                isLoadingLiveData.value = false
            } else if (response is Throwable) {
                Log.d("errorApi", response.message ?: "Error")
            }
        }
    }




}