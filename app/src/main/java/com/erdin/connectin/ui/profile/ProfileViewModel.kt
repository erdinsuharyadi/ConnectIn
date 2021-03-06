package com.erdin.connectin.ui.profile

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.erdin.connectin.SharedPreference
import com.erdin.connectin.auth.AuthApiService
import com.erdin.connectin.auth.LogoutResponse
import com.erdin.connectin.profile.ProfileApiService
import com.erdin.connectin.profile.ProfileResponse
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class ProfileViewModel : ViewModel(), CoroutineScope {

    val dataLogoLiveData = MutableLiveData<String>()
    val dataNameLiveData = MutableLiveData<String>()
    val dataCompNameLiveData = MutableLiveData<String>()
    val dataCompInfoLiveData = MutableLiveData<String>()
    val dataCompLocationLiveData = MutableLiveData<String>()
    val dataCompEmailLiveData = MutableLiveData<String>()
    val isLogoutLiveData = MutableLiveData<Boolean>()
    val msgToastLiveData = MutableLiveData<String>()
    val showToastLiveData = MutableLiveData<Boolean>()

    private lateinit var service: ProfileApiService
    private lateinit var sharedPreference: SharedPreference
    private lateinit var mService: AuthApiService


    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setSharedPreference(sharedPreference: SharedPreference) {
        this.sharedPreference = sharedPreference
    }

    fun setProfileService(service: ProfileApiService?) {
        if (service != null) {
            this.service = service
        }
    }

    fun setAuthService(service: AuthApiService) {
        if (service != null) {
            this.mService = service
        }
    }

    fun profileApi() {
        launch {
            val response = withContext(Dispatchers.IO) {
                try {
                    service?.getUserProfile(sharedPreference.getValueString(SharedPreference.KEY_USERNAME))
                } catch (e: Throwable) {
                    e.printStackTrace()

                    withContext(Dispatchers.Main) {
                        msgToastLiveData.value = "Get profile detail failed"
                        showToastLiveData.value = true
                    }
                }
            }

            if(response is ProfileResponse) {
                dataLogoLiveData.value = response.result[0].logo
                dataNameLiveData.value = response.result[0].name
                dataCompNameLiveData.value = response.result[0].nameCompany
                dataCompInfoLiveData.value = response.result[0].description
                dataCompEmailLiveData.value = response.result[0].email
                dataCompLocationLiveData.value = response.result[0].location
            }
        }
    }

    fun requestLogout() {
        launch {
            val response = withContext(Dispatchers.IO) {
                try {
                    mService?.requestLogout()
                } catch (e: Throwable) {
                    e.printStackTrace()

                    withContext(Dispatchers.Main) {
                        msgToastLiveData.value = "Username & Password is wrong"
                        showToastLiveData.value = true
                    }
                }
            }

            if (response is LogoutResponse) {
                if (response.status == 200) {
                    sharedPreference.clearSharedPreference()
                    isLogoutLiveData.value = true
                }
            }
        }
    }


}