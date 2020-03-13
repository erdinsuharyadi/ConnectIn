package com.erdin.connectin.projects

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.erdin.connectin.R
import com.erdin.connectin.databinding.ActivityProjectDetailsBinding

class ProjectDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProjectDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_project_details)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val extras = intent.getParcelableExtra<ProjectsModel>("project_items")

        binding.tvProjectName.text = extras.projectName
        binding.tvProjectDesc.text = extras.description
        binding.tvProjectPeriod.text = extras.period
        binding.tvProjectDate.text = extras.created

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