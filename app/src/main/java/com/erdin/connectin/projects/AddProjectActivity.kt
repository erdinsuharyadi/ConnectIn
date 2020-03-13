package com.erdin.connectin.projects

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.erdin.connectin.ApiClient
import com.erdin.connectin.MainActivity
import com.erdin.connectin.R
import com.erdin.connectin.SharedPreference
import com.erdin.connectin.databinding.ActivityAddProjectBinding
import com.erdin.connectin.ui.projects.ProjectsFragment
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

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

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

        val idCompany = sharedPreference.getValueInt(SharedPreference.KEY_COMP)

        binding.btnSubmit.setOnClickListener {
        Log.d("tgs", selectedDate)
            viewModel.addProjectApi(binding.etProjectName.text.toString(), binding.etDescription.text.toString(), selectedDate, "", "${idCompany}")
        }

        subscribeLiveData()
    }

    private fun showDatePicker() {
        DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { _, selectedYear, selectedMonth, selectedDay ->
                year = selectedYear
                month = selectedMonth
                day = selectedDay
                selectedDate = "$selectedDay-$selectedMonth-$selectedYear"
                binding.tvDate.text = selectedDate
            }, year, month, day
        ).show()
    }

    private fun subscribeLiveData() {
        viewModel.addProjectLiveData.observe(this, androidx.lifecycle.Observer {
            if (it) {
                val resulIntent = Intent()
                setResult(Activity.RESULT_OK, resulIntent)
                onBackPressed()
                finish()
            }
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