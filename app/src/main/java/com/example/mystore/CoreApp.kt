package com.example.mystore

import android.app.Application
import com.example.mystore.api.MyRetrofitAPI
import com.example.mystore.api.UserAgentInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class CoreApp : Application() {

    override fun onCreate() {
        super.onCreate()

        val userAgent : String = "MyStore"

        val client: OkHttpClient = OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .retryOnConnectionFailure(false)
            .addInterceptor(UserAgentInterceptor(userAgent))
            .build()

        val retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl(Constants.Default.API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofitAPI = retrofit.create(MyRetrofitAPI::class.java)
    }

    companion object {
        @JvmStatic
        var retrofitAPI: MyRetrofitAPI? = null
            private set
    }
}