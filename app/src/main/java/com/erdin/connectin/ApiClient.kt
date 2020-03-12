package com.erdin.connectin

import android.content.Context
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {

    companion object {
        private const val BASE_URL = "https://api.tatas.tech"
        private var retrofit: Retrofit? = null


        fun getApiClient(mContext: Context): Retrofit? {

            if (retrofit == null) {
                val okHtppClient = OkHttpClient.Builder().addInterceptor(HeaderInterceptor(mContext)).build()

                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHtppClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

            }
            return retrofit
        }
    }


}