package com.mukesh.countrylistdemo.data.repository.auth

import android.util.Log
import com.mukesh.countrylistdemo.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

/**
Created by Mukesh on 27/02/23
 An interceptor class for auth api client. It will intercept the auth api request and add a required header
 **/

class AuthInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var builder = chain.request().newBuilder()
        builder = builder
            .addHeader("Content-Type", "application/json")
            .addHeader("api-token", BuildConfig.TOKEN)
            .addHeader("user-email", BuildConfig.EMAIL)
        return chain.proceed(builder.build())
    }

}