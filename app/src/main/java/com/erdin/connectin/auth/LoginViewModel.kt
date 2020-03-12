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

    private lateinit var sharedPreference: SharedPreference
    private lateinit var service: AuthApiService
    private lateinit var mContext: Context

    override val coroutineContext: CoroutineContext
        get() = Job() +Dispatchers.Main


    fun setContext(context: Context) {
        this.mContext = context
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
                        Toast.makeText(mContext, "Username & Password is wrong", Toast.LENGTH_LONG).show()
                    }
                }
            }

            if (response is LoginResponse) {
                if (response.result?.level == "1") {
                    sharedPreference = SharedPreference(mContext)
                    sharedPreference.save(SharedPreference.KEY_TOKEN, response.result?.token)
                    sharedPreference.save(SharedPreference.KEY_COMP, response.result?.idCompany)
                    sharedPreference.save(SharedPreference.KEY_USERNAME, username)
                    sharedPreference.save(SharedPreference.KEY_LOGIN, true)


                    isLoginLiveData.value = true

                    Toast.makeText(mContext, "Login Success", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(mContext, "You are not authorized", Toast.LENGTH_LONG).show()
                }

            } else if (response is Throwable) {
                Log.d("errorApi", response.message ?: "Error")
            }
        }
    }
}