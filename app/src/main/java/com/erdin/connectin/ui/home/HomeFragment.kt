package com.erdin.connectin.ui.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.erdin.connectin.ApiClient
import com.erdin.connectin.MainActivity
import com.erdin.connectin.R
import com.erdin.connectin.databinding.FragmentHomeBinding
import com.erdin.connectin.engineers.EngineersAdapter
import com.erdin.connectin.engineers.EngineersApiService
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import kotlinx.coroutines.*


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var coroutineScope: CoroutineScope

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as MainActivity).supportActionBar!!.hide()

        coroutineScope = CoroutineScope(Job() + Dispatchers.Main)

        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        val flexLayoutManager = FlexboxLayoutManager(context)
        flexLayoutManager.flexDirection = FlexDirection.ROW
        flexLayoutManager.justifyContent = JustifyContent.SPACE_BETWEEN

        binding.rvEngineers.adapter = EngineersAdapter()
        binding.rvEngineers.layoutManager = flexLayoutManager

        binding.etSearch.addTextChangedListener(object : TextWatcher {
            private var searchFor = ""

            override fun afterTextChanged(s: Editable?) = Unit
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                val searchText = s.toString().trim()
                if (searchText == searchFor)
                    return

                searchFor = searchText

                coroutineScope.launch {
                    delay(300)  //debounce timeOut
                    if (searchText != searchFor)
                        return@launch

                    homeViewModel.engineerListApi(searchFor)
                }
            }
        })

        binding.pbEngineer.visibility

        val mContext = parentFragment?.context!!
        val service = ApiClient.getApiClient(mContext)?.create(EngineersApiService::class.java)
        homeViewModel.setEngineerService(service)
        homeViewModel.engineerListApi("")
        subscribeLiveData()
        return binding.root
    }

    private fun subscribeLiveData() {
        homeViewModel.engineerLiveData.observe(viewLifecycleOwner, Observer {
            (binding.rvEngineers.adapter as EngineersAdapter).addList(it)
        })

        homeViewModel.isLoadingLiveData.observe(viewLifecycleOwner, Observer {
            binding.pbEngineer.visibility = if(it) View.VISIBLE else View.GONE
        })

    }


}