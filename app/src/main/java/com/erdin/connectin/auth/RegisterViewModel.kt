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

    private lateinit var service: AuthApiService
    private lateinit var mContext: Context

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main


    fun setContext(context: Context) {
        this.mContext = context
    }

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
                        Toast.makeText(mContext, "Register failed!", Toast.LENGTH_LONG).show()
                    }
                }
            }

            if(response is RegisterResponse) {
                if(response.status == 200) {
                    regisLiveData.value = true

                    Toast.makeText(mContext, "Login Success", Toast.LENGTH_LONG).show()
                }
            } else if (response is Throwable) {

                Log.d("errorApi", response.message ?: "Error")
            }
        }
    }
}