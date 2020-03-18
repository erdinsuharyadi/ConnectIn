package com.erdin.connectin.auth

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class RegisterViewModel : ViewModel(), CoroutineScope {

    val regisLiveData = MutableLiveData<Boolean>()
    val msgToastLiveData = MutableLiveData<String>()
    val showToastLiveData = MutableLiveData<Boolean>()
    
    private lateinit var service: AuthApiService

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setRegisService(service: AuthApiService) {
        if (service != null) {
            this.service = service
        }
    }

    fun requestRegister(username: String, password: String, email: String, name: String) {
        launch {
            val response = withContext(Dispatchers.IO) {
                try {
                    service?.regisRequest(username, password, email, name, "1")
                } catch (e : Throwable) {
                    e.printStackTrace()

                    withContext(Dispatchers.Main) {
                        msgToastLiveData.value = "Register failed!"
                        showToastLiveData.value = true
                    }
                }
            }

            if(response is RegisterResponse) {
                if(response.status == 200) {
                    regisLiveData.value = true
                }
            } else if (response is Throwable) {
                Log.d("errorApi", response.message ?: "Error")
            }
        }
    }
}