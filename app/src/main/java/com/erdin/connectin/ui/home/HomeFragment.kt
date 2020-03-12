package com.erdin.connectin.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.erdin.connectin.ApiClient
import com.erdin.connectin.R
import com.erdin.connectin.auth.AuthApiService
import com.erdin.connectin.databinding.FragmentHomeBinding
import com.erdin.connectin.engineers.*
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        binding.rvCharacter.adapter = EngineersAdapter()
        binding.rvCharacter.layoutManager = GridLayoutManager(parentFragment?.context, 3, RecyclerView.VERTICAL, false)

        val mContext = parentFragment?.context!!
        val service = ApiClient.getApiClient(mContext)?.create(EngineersApiService::class.java)
        homeViewModel.setEngineerService(service)
        homeViewModel.setContext(mContext)
        homeViewModel.engineerListApi()
        subscribeLiveData()
        return binding.root
    }

    private fun subscribeLiveData() {
        homeViewModel.engineerLiveData.observe(viewLifecycleOwner, Observer {
            (binding.rvCharacter.adapter as EngineersAdapter).addList(it)
        })

    }


}