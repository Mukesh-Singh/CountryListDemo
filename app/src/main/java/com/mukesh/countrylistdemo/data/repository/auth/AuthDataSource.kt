package com.mukesh.countrylistdemo.data.repository.auth


import com.mukesh.countrylistdemo.data.repository.auth.model.ResponseAuthToken
import com.mukesh.countrylistdemo.data.repository.datasource.remote.network_model.ResponseCountry
import retrofit2.Response

/**
Created by Mukesh on 27/02/23
 **/

interface AuthDataSource {
    suspend fun getAuthCode(): Response<ResponseAuthToken>
}