package com.erdin.connectin.offers

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.erdin.connectin.R
import com.erdin.connectin.databinding.ActivityOfferDetailsBinding

class OfferDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOfferDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_offer_details)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val extras = intent.getParcelableExtra<OffersModel>("offer_items")

        val statusProject = when (extras.statusProject) {
            "1" -> "On Confirm"
            "2" -> "Accepted"
            "3" -> "Negotiation"
            else -> "Decline"
        }

        binding.tvProjectName.text = extras.projectName
        binding.tvEngName.text = extras.nameEngineer
        binding.tvProjectJob.text = extras.projectJob
        binding.tvProjectFee.text = extras.fee
        binding.tvCreatedAt.text = extras.created
        binding.tvOfferStatus.text = statusProject
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