package com.mukesh.countrylistdemo.data.repository.datasource.remote

import com.mukesh.countrylistdemo.data.repository.auth.AuthTokenManager
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

/**
Created by Mukesh on 27/02/23
An interceptor class for Content api client. It will intercept the auth api request and add a required header
 **/

class HeaderInterceptor @Inject constructor(
    private val authTokenManager: AuthTokenManager
) : Interceptor {

    companion object{
        val TAG = HeaderInterceptor::class.java.simpleName
    }
    override fun intercept(chain: Interceptor.Chain): Response {
        var builder = chain.request().newBuilder()
        builder = builder
            .addHeader("Content-Type", "application/json")
            .addHeader("Authorization", ("Bearer " + authTokenManager.getAuthToken()))
        return chain.proceed(builder.build())
    }

}