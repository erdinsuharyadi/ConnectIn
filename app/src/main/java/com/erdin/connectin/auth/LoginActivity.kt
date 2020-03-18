package com.erdin.connectin.auth

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.erdin.connectin.ApiClient
import com.erdin.connectin.MainActivity
import com.erdin.connectin.R
import com.erdin.connectin.SharedPreference
import com.erdin.connectin.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel
    private lateinit var sharedPreference: SharedPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_login
        )

        sharedPreference = SharedPreference(applicationContext)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        viewModel.setSharedPreference(sharedPreference)

        binding.tvNotacc.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

        val service = ApiClient.getApiClient(this)?.create(AuthApiService::class.java)
        viewModel.setLoginService(service)
        
        binding.btnLogin.setOnClickListener {
            if(TextUtils.isEmpty(binding.etUsername.text)) {
                binding.etUsername.error = "Required"
                return@setOnClickListener
            }

            if(TextUtils.isEmpty(binding.etPassword.text)) {
                binding.etPassword.error = "Required"
                return@setOnClickListener
            }

            viewModel.requestLogin(binding.etUsername.text.toString(), binding.etPassword.text.toString())
        }

        subscribeLiveData()
    }

    private fun subscribeLiveData() {
        viewModel.isLoginLiveData.observe(this, Observer {
            if (it) {
                Toast.makeText(this, "Login Success", Toast.LENGTH_LONG).show()
                startActivity(
                    Intent(this, MainActivity::class.java)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                )
                finish()
            }
        })

        viewModel.showToastLiveData.observe(this, Observer {
            viewModel.msgToastLiveData.observe(this, Observer {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            })
        })
    }


}