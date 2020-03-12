package com.erdin.connectin.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.erdin.connectin.ApiClient
import com.erdin.connectin.R
import com.erdin.connectin.databinding.FragmentProfileBinding
import com.erdin.connectin.engineers.EngineersApiService
import com.erdin.connectin.profile.ProfileApiService
import com.squareup.picasso.Picasso

class ProfileFragment: Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var profileViewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        val mContext = parentFragment?.context!!
        val service = ApiClient.getApiClient(mContext)?.create(ProfileApiService::class.java)

        profileViewModel.setContext(mContext)
        profileViewModel.setService(service)

        profileViewModel.profileApi()

        subscribeLiveData()

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
    }

}