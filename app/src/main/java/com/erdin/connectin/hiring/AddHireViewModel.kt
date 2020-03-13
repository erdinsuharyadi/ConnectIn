package com.erdin.connectin.hiring

import android.R
import android.content.Context
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.erdin.connectin.projects.ProjectsApiService
import com.erdin.connectin.projects.ProjectsModel
import com.erdin.connectin.projects.ProjectsResponse
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class AddHireViewModel : ViewModel(), CoroutineScope {

    val addHireLiveData = MutableLiveData<Boolean>()
    val listSpinLiveData = MutableLiveData<List<SpinProject>>()
    val adapterSpinnerLiveData = MutableLiveData<ArrayAdapter<String?>>()

    private lateinit var mContext: Context
    private lateinit var service: AddHireApiService
    private lateinit var mService: ProjectsApiService

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

    fun setProjectsService(service: ProjectsApiService?) {
        if (service != null) {
            this.mService = service
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

    fun getSpinnerProject() {
        launch {
            val response = withContext(Dispatchers.IO) {
                try {
                    mService?.getProjectList()
                } catch (e: Throwable) {
                    e.printStackTrace()

                    withContext(Dispatchers.Main) {
                        Toast.makeText(mContext, "Add hire failed!", Toast.LENGTH_LONG).show()
                    }
                }
            }

            if (response is ProjectsResponse) {
                listSpinLiveData.value = response.result?.map {
                    SpinProject(it.idProject, it.projectName)
                }

                val adapter = ArrayAdapter(mContext,
                    R.layout.simple_spinner_item, listSpinLiveData.value!!.map { it.projectName })

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    adapterSpinnerLiveData.value = adapter
            } else if (response is Throwable) {
                Log.d("errorApi", response.message ?: "Error")
            }
        }
    }
}