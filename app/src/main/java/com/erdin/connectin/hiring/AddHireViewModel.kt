package com.erdin.connectin.hiring


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.erdin.connectin.projects.ProjectsApiService
import com.erdin.connectin.projects.ProjectsResponse
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class AddHireViewModel : ViewModel(), CoroutineScope {

    val addHireLiveData = MutableLiveData<Boolean>()
    val listSpinLiveData = MutableLiveData<List<SpinProject>>()
    val msgToastLiveData = MutableLiveData<String>()
    val showToastLiveData = MutableLiveData<Boolean>()

    private lateinit var service: AddHireApiService
    private lateinit var mService: ProjectsApiService

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setAddHireService(service: AddHireApiService?) {
        if (service != null) {
            this.service = service
        }
    }

    fun setProjectsService(service: ProjectsApiService?) {
        if (service != null) {
            this.mService = service
        }
    }

    fun addHire(idProject : String, idEngineer: String?, fee: String, projectJob: String) {
            launch {
                val response = withContext(Dispatchers.IO) {
                    try {
                        service?.addHire(idProject, idEngineer, fee, projectJob)
                    } catch (e: Throwable) {
                        e.printStackTrace()

                        withContext(Dispatchers.Main) {
                            msgToastLiveData.value = "Add Hire Failed!"
                            showToastLiveData.value = true
                        }
                    }
                }

                if (response is AddHireResponse) {
                    if (response.status == 200) {
                        addHireLiveData.value = true

                        msgToastLiveData.value = "Add hire Success"
                        showToastLiveData.value = true
                    } else if (response is Throwable) {
                        Log.d("errorApi", response.message ?: "Error")
                    }
                }
            }

    }

    fun getSpinnerProject() {
        launch {
            val response = withContext(Dispatchers.IO) {
                try {
                    mService?.getProjectList()
                } catch (e: Throwable) {
                    e.printStackTrace()

                    withContext(Dispatchers.Main) {
                        msgToastLiveData.value = "Add hire failed!"
                        showToastLiveData.value = true
                    }
                }
            }

            if (response is ProjectsResponse) {
                listSpinLiveData.value = response.result?.map {
                    SpinProject(it.idProject, it.projectName)
                }

            } else if (response is Throwable) {
                Log.d("errorApi", response.message ?: "Error")
            }
        }
    }
}