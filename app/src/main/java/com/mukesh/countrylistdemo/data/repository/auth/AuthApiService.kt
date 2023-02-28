package com.mukesh.countrylistdemo.data.repository.auth

import com.mukesh.countrylistdemo.data.repository.auth.model.ResponseAuthToken
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.HeaderMap

/**
Created by Mukesh on 27/02/23
 **/

interface AuthApiService {
    @GET("getaccesstoken/")
    suspend fun getAuthToken(): Response<ResponseAuthToken>
}