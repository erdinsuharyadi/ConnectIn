package com.erdin.connectin.ui.profile

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.erdin.connectin.*
import com.erdin.connectin.auth.AuthApiService
import com.erdin.connectin.databinding.FragmentProfileBinding
import com.erdin.connectin.engineers.EngineersApiService
import com.erdin.connectin.profile.ProfileApiService
import com.squareup.picasso.Picasso

class ProfileFragment: Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var sharedPreference: SharedPreference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        binding.toolbar.title = "Profile"


        val mContext = parentFragment?.context!!
        val service = ApiClient.getApiClient(mContext)?.create(ProfileApiService::class.java)
        val authService = ApiClient.getApiClient(mContext)?.create(AuthApiService::class.java)
        sharedPreference = SharedPreference(mContext)
        profileViewModel.setSharedPreference(sharedPreference)
        profileViewModel.setProfileService(service)
        if (authService != null) profileViewModel.setAuthService(authService)
        profileViewModel.profileApi()

        subscribeLiveData()

        binding.btnLogout.setOnClickListener {
            AlertDialog.Builder(mContext)
                .setTitle("Confirm Logout")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes") { _, i ->
                    profileViewModel.requestLogout()
                }
                .setNegativeButton("No") { dialog, i ->
                    dialog.cancel()
                }
                .show()
        }

        return binding.root
    }

    private fun subscribeLiveData() {
        profileViewModel.dataLogoLiveData.observe(viewLifecycleOwner, Observer {
            Picasso.get()
                .load(it)
                .into(binding.ivProfile)
        })

        profileViewModel.dataCompNameLiveData.observe(viewLifecycleOwner, Observer {
            binding.tvCompanyName.text = it
        })

        profileViewModel.dataCompInfoLiveData.observe(viewLifecycleOwner, Observer {
            binding.tvCompInfo.text = it
        })

        profileViewModel.dataCompLocationLiveData.observe(viewLifecycleOwner, Observer {
            binding.tvCompLocation.text = it
        })

        profileViewModel.dataCompEmailLiveData.observe(viewLifecycleOwner, Observer {
            binding.tvCompEmail.text = it
        })

        profileViewModel.dataNameLiveData.observe(viewLifecycleOwner, Observer {
            binding.tvProfileName.text = it
        })

        profileViewModel.isLogoutLiveData.observe(viewLifecycleOwner, Observer {
            if (it) {
                val intent = Intent(parentFragment?.context, BoardActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }
        })

        profileViewModel.showToastLiveData.observe(viewLifecycleOwner, Observer {
            profileViewModel.msgToastLiveData.observe(viewLifecycleOwner, Observer {
                Toast.makeText(parentFragment?.context, it, Toast.LENGTH_LONG).show()
            })
        })
    }

}