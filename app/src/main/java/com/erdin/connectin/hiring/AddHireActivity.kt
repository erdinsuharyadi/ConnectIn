package com.erdin.connectin.hiring

import android.content.Intent
import android.os.Bundle
import android.util.Log
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

        viewModel = ViewModelProvider(this).get(AddHireViewModel::class.java)

        initSpinnerProject()

        val service = ApiClient.getApiClient(this)?.create(AddHireApiService::class.java)
        viewModel.setContext(this)
        viewModel.setAddHireService(service)

        binding.spinnerProject.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                projectId = listSpin[position].idProject.toString()
                Toast.makeText(this@AddHireActivity, "Spinner selected : ${projectId}", Toast.LENGTH_LONG).show()
            }
        }

        val engineerId = intent.extras?.getString("id_eng")
        val projectFee = binding.etProjectFee.text.toString()
        val projectJob = binding.etProjectJob.text.toString()


        binding.btnSubmit.setOnClickListener {
            viewModel.addHire(projectId, engineerId, projectFee, projectJob)
        }

        subscribeLiveData()



    }


    private fun initSpinnerProject() {
        val service = ApiClient.getApiClient(this)?.create(ProjectsApiService::class.java)

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

                listSpin = response.result?.map {
                    SpinProject(it.idProject, it.projectName)
                }

                val adapter = ArrayAdapter(this@AddHireActivity,
                    android.R.layout.simple_spinner_item, listSpin.map { it.projectName })

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinnerProject.adapter = adapter


            } else if (response is Throwable) {
                Log.d("errorApi", response.message ?: "Error")
            }
        }
    }

    private fun subscribeLiveData() {
        viewModel.addHireLiveData.observe(this, Observer {
            if(it) {
                onBackPressed()
                finish()
            }
        })
    }
}