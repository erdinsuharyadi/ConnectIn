package com.erdin.connectin.auth

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.erdin.connectin.MainActivity
import com.erdin.connectin.SharedPreference
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class LoginViewModel : ViewModel(), CoroutineScope {

    val isLoginLiveData = MutableLiveData<Boolean>()
    val msgToastLiveData = MutableLiveData<String>()
    val showToastLiveData = MutableLiveData<Boolean>()

    private lateinit var sharedPreference: SharedPreference
    private lateinit var service: AuthApiService


    override val coroutineContext: CoroutineContext
        get() = Job() +Dispatchers.Main

    fun setSharedPreference(sharedPreference: SharedPreference) {
        this.sharedPreference = sharedPreference
    }

    fun setLoginService(service: AuthApiService?) {
        if (service != null) {
            this.service = service
        }
    }

    fun requestLogin(username: String, password: String) {
        launch {
            val response = withContext(Dispatchers.IO) {
                try {
                    service?.loginRequest(username, password)
                } catch (e: Throwable) {
                    e.printStackTrace()

                    withContext(Dispatchers.Main) {
                        msgToastLiveData.value = "Username & Password is wrong"
                        showToastLiveData.value = true
                    }
                }
            }

            if (response is LoginResponse) {
                if (response.result?.level == "1") {
                    sharedPreference.save(SharedPreference.KEY_TOKEN, response.result?.token)
                    sharedPreference.save(SharedPreference.KEY_COMP, response.result?.idCompany)
                    sharedPreference.save(SharedPreference.KEY_USERNAME, username)
                    sharedPreference.save(SharedPreference.KEY_LOGIN, true)

                    isLoginLiveData.value = true
                } else {
                    msgToastLiveData.value = "You are not authorized"
                    showToastLiveData.value = true
                }

            } else if (response is Throwable) {
                Log.d("errorApi", response.message ?: "Error")
            }
        }
    }
}