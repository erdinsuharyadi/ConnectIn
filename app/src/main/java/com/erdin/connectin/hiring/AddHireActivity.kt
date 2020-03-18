package com.erdin.connectin.hiring

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.erdin.connectin.ApiClient
import com.erdin.connectin.R
import com.erdin.connectin.auth.LoginActivity
import com.erdin.connectin.databinding.ActivityAddHireBinding
import com.erdin.connectin.projects.ProjectsApiService
import com.erdin.connectin.projects.ProjectsResponse
import kotlinx.coroutines.*


class AddHireActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddHireBinding
    private lateinit var listSpin: List<SpinProject>
    private lateinit var viewModel: AddHireViewModel
    private lateinit var projectId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_hire)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel = ViewModelProvider(this).get(AddHireViewModel::class.java)

        viewModel.getSpinnerProject()

        val hireService = ApiClient.getApiClient(this)?.create(AddHireApiService::class.java)
        val projectService = ApiClient.getApiClient(this)?.create(ProjectsApiService::class.java)
        viewModel.setAddHireService(hireService)
        viewModel.setProjectsService(projectService)

        binding.spinnerProject.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                projectId = listSpin[position].idProject.toString()
            }
        }

        val engineerId = intent.extras?.getString("id_eng")
        val projectFee = binding.etProjectFee.text
        val projectJob = binding.etProjectJob.text

        binding.btnSubmit.setOnClickListener {
            viewModel.addHire(projectId, engineerId, "$projectFee", "$projectJob")
        }

        subscribeLiveData()
    }

    private fun subscribeLiveData() {
        viewModel.addHireLiveData.observe(this, Observer {
            if(it) {
                finish()
            }
        })

        viewModel.listSpinLiveData.observe(this, Observer {
            listSpin = it

            val adapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_item, it.map { it -> it.projectName })

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerProject.adapter = adapter
        })

        viewModel.showToastLiveData.observe(this, Observer {
            viewModel.msgToastLiveData.observe(this, Observer {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            })
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }
}