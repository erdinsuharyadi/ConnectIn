package com.erdin.connectin.ui.projects

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.erdin.connectin.ApiClient
import com.erdin.connectin.R
import com.erdin.connectin.databinding.FragmentProjectBinding
import com.erdin.connectin.engineers.EngineersApiService
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
        projectsViewModel = ViewModelProvider(this).get(ProjectsViewModel::class.java)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_project, container, false)
        binding.rvProjects.adapter = ProjectsAdapter()
        binding.rvProjects.layoutManager = LinearLayoutManager(parentFragment?.context, RecyclerView.VERTICAL, false)
        binding.toolbar.setTitle("Projects")


        val mContext = parentFragment?.context!!
        val service = ApiClient.getApiClient(mContext)?.create(ProjectsApiService::class.java)
        projectsViewModel.setContext(mContext)
        projectsViewModel.setService(service)
        projectsViewModel.projectListApi()

        binding.fabAddProject.setOnClickListener {
            val intent = Intent(parentFragment?.context, AddProjectActivity::class.java)
            startActivityForResult(intent, 1)
        }

        subscribeLiveData()

        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            projectsViewModel.projectListApi()
        }
    }

    private fun subscribeLiveData() {
        projectsViewModel.projectLiveData.observe(viewLifecycleOwner, Observer {
            (binding.rvProjects.adapter as ProjectsAdapter).addList(it)
        })

        projectsViewModel.isLoadingLiveData.observe(viewLifecycleOwner, Observer {
            binding.pbProjects.visibility = if(it) View.VISIBLE else View.GONE
        })
    }
}