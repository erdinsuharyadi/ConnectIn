package com.erdin.connectin.projects

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class AddProjectViewModel: ViewModel(), CoroutineScope {

    val addProjectLiveData = MutableLiveData<Boolean>()
    val msgToastLiveData = MutableLiveData<String>()
    val showToastLiveData = MutableLiveData<Boolean>()

    private lateinit var service: ProjectsApiService

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setAddProjectService(service: ProjectsApiService?) {
        if (service != null) {
            this.service = service
        }
    }

    fun addProjectApi(projectName: String, projectDesc: String, period: String, deadline: String, idCompany: String) {
        launch {
            val response = withContext(Dispatchers.IO) {
                try {
                    service?.addProject(projectName, projectDesc, period, "2020-06-20", idCompany)
                } catch (e: Throwable) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main) {
                        msgToastLiveData.value = "Add Project failed!"
                        showToastLiveData.value = true
                    }
                }
            }

            if(response is AddProjectResponse) {
                if (response.result?.affectedRows == 1) {
                    addProjectLiveData.value = true
                } else if (response is Throwable) {
                    Log.d("errorApi", response.message ?: "Error")
                }
            }
        }
    }

}