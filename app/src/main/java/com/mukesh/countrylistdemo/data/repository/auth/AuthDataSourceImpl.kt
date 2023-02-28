package com.mukesh.countrylistdemo.data.repository.auth

import com.mukesh.countrylistdemo.data.repository.auth.model.ResponseAuthToken
import retrofit2.Response

/**
Created by Mukesh on 27/02/23
 **/

class AuthDataSourceImpl(private val authApiService: AuthApiService): AuthDataSource {
    override suspend fun getAuthCode(): Response<ResponseAuthToken> {
       return authApiService.getAuthToken()
    }


}