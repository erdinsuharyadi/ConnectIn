package com.erdin.connectin.ui.projects

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.erdin.connectin.ApiClient
import com.erdin.connectin.R
import com.erdin.connectin.databinding.FragmentProjectBinding
import com.erdin.connectin.offers.OffersAdapter
import com.erdin.connectin.projects.*
import kotlinx.coroutines.*

class ProjectsFragment : Fragment() {

    private lateinit var binding: FragmentProjectBinding
    private lateinit var projectsViewModel: ProjectsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        projectsViewModel =
            ViewModelProviders.of(this).get(ProjectsViewModel::class.java)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_project, container, false)
        binding.rvProjects.adapter = ProjectsAdapter()
        binding.rvProjects.layoutManager = LinearLayoutManager(parentFragment?.context, RecyclerView.VERTICAL, false)

        projectListApi()

        binding.fabAddProject.setOnClickListener {
            val intent = Intent(parentFragment?.context, AddProjectActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }


    fun projectListApi() {

        val service = ApiClient.getApiClient(parentFragment?.context!!)?.create(ProjectsApiService::class.java)

        val coroutineScope = CoroutineScope(Job() + Dispatchers.Main)

        coroutineScope.launch {

            val response = withContext(Dispatchers.IO) {
                try {
                    service?.getProjectList()
                } catch (e: Throwable) {
                    e.printStackTrace()
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

                (binding.rvProjects.adapter as ProjectsAdapter).addList(list)

            } else if (response is Throwable) {
                Log.d("errorApi", response.message ?: "Error")
            }
        }
    }
}