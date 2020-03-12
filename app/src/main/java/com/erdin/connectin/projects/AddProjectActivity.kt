package com.erdin.connectin.projects

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.erdin.connectin.ApiClient
import com.erdin.connectin.R
import com.erdin.connectin.SharedPreference
import com.erdin.connectin.databinding.ActivityAddProjectBinding
import com.erdin.connectin.hiring.AddHireApiService
import com.erdin.connectin.hiring.AddHireViewModel
import java.util.*

class AddProjectActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddProjectBinding
    private lateinit var sharedPreference: SharedPreference
    private lateinit var viewModel: AddProjectViewModel
    private lateinit var selectedDate: String

    companion object {
        var year: Int = 0
        var month: Int = 0
        var day: Int = 0
        var idCompany: String? = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_project)

        viewModel = ViewModelProvider(this).get(AddProjectViewModel::class.java)

        sharedPreference = SharedPreference(this)

        val cal = Calendar.getInstance()
        year = cal.get(Calendar.YEAR)
        month = cal.get(Calendar.MONTH)
        day = cal.get(Calendar.DAY_OF_MONTH)

        val service = ApiClient.getApiClient(this)?.create(ProjectsApiService::class.java)

        viewModel.setContext(this)
        viewModel.setAddProjectService(service)

        selectedDate = "$year-$month-$day"


        binding.tvDate.setOnClickListener {
            showDatePicker()
        }

        val projectName = binding.etProjectName.text.toString()
        val projectDesc = binding.etDescription.text.toString()
        val period = binding.etPeriod.text.toString()
        val idCompany = sharedPreference.getValueInt(SharedPreference.KEY_COMP)

        binding.btnSubmit.setOnClickListener {
            viewModel.addProjectApi(binding.etProjectName.text.toString(), binding.etDescription.text.toString(), binding.etPeriod.text.toString(), selectedDate, "${idCompany}")
        }
    }

    private fun showDatePicker() {
        DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { _, selectedYear, selectedMonth, selectedDay ->
                year = selectedYear
                month = selectedMonth
                day = selectedDay
                selectedDate = "$selectedYear-$selectedMonth-$selectedDay"
                binding.tvDate.text = selectedDate
            }, year, month, day
        ).show()
    }
}