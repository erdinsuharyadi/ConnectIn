package com.erdin.connectin

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor(val mContext : Context) : Interceptor {

    private lateinit var sharedPreference: SharedPreference

    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        sharedPreference = SharedPreference(mContext)
        val token = sharedPreference.getValueString(SharedPreference.KEY_TOKEN) ?: ""

        proceed(
            request()
                .newBuilder()
                .addHeader("x-access-token", token)
                .build()
        )
    }

}