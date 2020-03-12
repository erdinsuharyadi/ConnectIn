package com.erdin.connectin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    abstract fun getLayoutId(): Int

    private lateinit var viewBase: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBase = inflater.inflate(getLayoutId(), container, false)
        return viewBase
    }
}