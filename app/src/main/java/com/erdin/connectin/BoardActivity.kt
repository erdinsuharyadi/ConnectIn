package com.erdin.connectin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.erdin.connectin.auth.LoginActivity
import com.erdin.connectin.auth.RegisterActivity
import com.erdin.connectin.databinding.ActivityBoardBinding

class BoardActivity: AppCompatActivity() {
    private lateinit var binding: ActivityBoardBinding
    private lateinit var pagerAdapter: ViewPagerBoardAdapter
    private lateinit var sharedPreference: SharedPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_board)
        pagerAdapter = ViewPagerBoardAdapter(supportFragmentManager)
        binding.vpBoard.adapter = pagerAdapter

        // cek login
        sharedPreference = SharedPreference(this)
        if (sharedPreference.getValueBoolean(SharedPreference.KEY_LOGIN, false)) {
            startActivity(
                Intent(this, MainActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            )
            finish()
        }

        binding.btnCreate.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}