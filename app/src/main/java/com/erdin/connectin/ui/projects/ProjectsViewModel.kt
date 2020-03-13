package com.erdin.connectin.ui.projects

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.erdin.connectin.projects.ProjectsApiService
import com.erdin.connectin.projects.ProjectsModel
import com.erdin.connectin.projects.ProjectsResponse
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class ProjectsViewModel : ViewModel(), CoroutineScope {

    val projectLiveData = MutableLiveData<List<ProjectsModel>>()
    val isLoadingLiveData = MutableLiveData<Boolean>()

    private lateinit var service: ProjectsApiService
    private lateinit var mContext: Context

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setContext(context: Context) {
        this.mContext = context
    }

    fun setService(service: ProjectsApiService?) {
        if (service != null) {
            this.service = service
        }
    }

    fun projectListApi() {
        launch {
            isLoadingLiveData.value = true
            val response = withContext(Dispatchers.IO) {
                try {
                    service?.getProjectList()
                } catch (e: Throwable) {
                    e.printStackTrace()

                    withContext(Dispatchers.Main) {
                        Toast.makeText(mContext, "Get profile detail failed", Toast.LENGTH_LONG).show()
                    }
                }
            }

            if (response is ProjectsResponse) {
                Log.d("responAPI", response.toString())
                val list = response.result?.map {
                    ProjectsModel(it.idProject,
                        it.idUser.orEmpty(),
                        it.idCompany,
                        it.projectName.orEmpty(),
                        it.description.orEmpty(),
                        it.period.orEmpty(),
                        it.deadline.orEmpty(),
                        it.status.orEmpty(),
                        it.progress.orEmpty(),
                        it.created.orEmpty())
                }

                projectLiveData.value = list
                isLoadingLiveData.value = false

            } else if (response is Throwable) {
                Log.d("errorApi", response.message ?: "Error")
            }
        }
    }



}