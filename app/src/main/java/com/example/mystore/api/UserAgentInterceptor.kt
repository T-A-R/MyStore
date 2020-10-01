package com.example.mystore.api

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class UserAgentInterceptor(private val mUserAgent: String) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder().header("User-Agent", mUserAgent).build()
        return chain.proceed(request)
    }
}