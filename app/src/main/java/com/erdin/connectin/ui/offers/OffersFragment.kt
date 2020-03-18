package com.erdin.connectin.ui.offers

import android.os.Bundle
import android.util.Log
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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.erdin.connectin.ApiClient
import com.erdin.connectin.R
import com.erdin.connectin.SharedPreference
import com.erdin.connectin.databinding.FragmentOffersBinding
import com.erdin.connectin.engineers.EngineersAdapter
import com.erdin.connectin.engineers.EngineersApiService
import com.erdin.connectin.engineers.EngineersModel
import com.erdin.connectin.engineers.EngineersResponse
import com.erdin.connectin.offers.OffersAdapter
import com.erdin.connectin.offers.OffersApiService
import com.erdin.connectin.offers.OffersModel
import com.erdin.connectin.offers.OffersResponse
import kotlinx.coroutines.*

class OffersFragment : Fragment() {

    private lateinit var binding: FragmentOffersBinding
    private lateinit var offersViewModel: OffersViewModel
    private lateinit var sharedPreference: SharedPreference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        offersViewModel = ViewModelProvider(this).get(OffersViewModel::class.java)
        sharedPreference = SharedPreference(parentFragment?.context!!)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_offers, container, false)
        binding.toolbar.setTitle("Offers")
        binding.rvOffers.adapter = OffersAdapter()
        binding.rvOffers.layoutManager = LinearLayoutManager(parentFragment?.context, RecyclerView.VERTICAL, false)

        val mContext = parentFragment?.context!!
        val service = ApiClient.getApiClient(mContext)?.create(OffersApiService::class.java)
        offersViewModel.setSharedPreference(sharedPreference)
        offersViewModel.setOffersService(service)
        offersViewModel.offersApi()
        subscribeLiveData()

        return binding.root
    }

    fun subscribeLiveData() {
        offersViewModel.offersLiveData.observe(viewLifecycleOwner, Observer {
            (binding.rvOffers.adapter as OffersAdapter).addList(it)
        })

        offersViewModel.isLoadingLiveData.observe(viewLifecycleOwner, Observer {
            binding.pbOfffers.visibility = if(it) View.VISIBLE else View.GONE
        })
    }
}