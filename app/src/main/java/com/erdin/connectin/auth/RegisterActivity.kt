package com.erdin.connectin.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.erdin.connectin.ApiClient
import com.erdin.connectin.R
import com.erdin.connectin.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)

        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_register
        )


        viewModel.setContext(this)
        val service = ApiClient.getApiClient(this)?.create(AuthApiService::class.java)
        viewModel.setRegisService(service!!)

        binding.btnRegister.setOnClickListener {

            viewModel.requestRegister(binding.etUsername.text.toString(), binding.etPassword.text.toString(), binding.etEmail.text.toString(), binding.etName.text.toString())
        }

        binding.tvAlreadyacc.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        subscribeLiveData()

    }

    private fun subscribeLiveData() {
        viewModel.regisLiveData.observe(this, Observer {
            if(it) {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        })
    }
}