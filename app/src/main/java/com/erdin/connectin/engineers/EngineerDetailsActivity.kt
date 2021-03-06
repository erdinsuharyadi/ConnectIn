package com.erdin.connectin.engineers


import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.erdin.connectin.R
import com.erdin.connectin.showcase.ShowcaseActivity
import com.erdin.connectin.databinding.ActivityEngineerDetailsBinding
import com.erdin.connectin.hiring.AddHireActivity
import com.squareup.picasso.Picasso


class EngineerDetailsActivity : AppCompatActivity() {

    private lateinit var binding : ActivityEngineerDetailsBinding

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_engineer_details)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val extras = intent.extras
        Picasso.get()
            .load(extras?.getString("photo_eng"))
            .into(binding.ivProfile)

        binding.tvEngName.text = extras?.getString("name_eng")
        binding.tvEngJob.text = extras?.getString("job_eng")
        binding.tvTotalProject.text = extras?.getString("total_project")
        binding.tvSuccessProject.text = extras?.getString("success_project")
        binding.tvRateProject.text = "${extras?.getString("rate_project")} %"
        binding.tvEngSkills.text = extras?.getString("skill_eng")
        binding.tvEngDob.text = extras?.getString("dob_eng")
        binding.tvEngEmail.text = extras?.getString("email_eng")
        binding.tvEngLocation.text = extras?.getString("loc_eng")

        binding.btnShowcase.setOnClickListener {
            val intent = Intent(this, ShowcaseActivity::class.java)
            intent.putExtra("showcase_eng", extras?.getString("showcase_eng"))
            startActivity(intent)
        }


        binding.btnHire.setOnClickListener {
            val intent = Intent(applicationContext, AddHireActivity::class.java)
            intent.putExtra("id_eng", extras?.getString("id_eng"))
            startActivity(intent)
        }

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