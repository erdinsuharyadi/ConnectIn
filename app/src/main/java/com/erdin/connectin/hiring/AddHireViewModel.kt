package com.erdin.connectin.hiring

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class AddHireViewModel : ViewModel(), CoroutineScope {

    val addHireLiveData = MutableLiveData<Boolean>()

    private lateinit var mContext: Context
    private lateinit var service: AddHireApiService

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setContext(context: Context) {
        this.mContext = context
    }

    fun setAddHireService(service: AddHireApiService?) {
        if (service != null) {
            this.service = service
        }


    }

    fun addHire(idProject : String, idEngineer: String?, fee: String, projectJob: String) {
        launch {
            launch {
                val response = withContext(Dispatchers.IO) {
                    try {
                        service?.addHire(idProject, idEngineer, fee, projectJob)
                    } catch (e: Throwable) {
                        e.printStackTrace()

                        withContext(Dispatchers.Main) {
                            Toast.makeText(mContext, "Add hire failed!", Toast.LENGTH_LONG).show()
                        }
                    }
                }

                if (response is AddHireResponse) {
                    if (response.status == 200) {
                        addHireLiveData.value = true

                        Toast.makeText(mContext, "Add hire Success", Toast.LENGTH_LONG).show()
                    } else if (response is Throwable) {
                        Log.d("errorApi", response.message ?: "Error")
                    }
                }
            }
        }
    }
}