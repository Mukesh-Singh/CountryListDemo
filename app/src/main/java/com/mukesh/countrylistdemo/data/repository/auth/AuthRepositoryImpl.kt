package com.mukesh.countrylistdemo.data.repository.auth

import com.mukesh.countrylistdemo.data.repository.BaseRepositoryImpl
import com.mukesh.countrylistdemo.data.repository.datasource.remote.RemoteDataSource
import com.mukesh.countrylistdemo.data.repository.auth.model.ResponseAuthToken
import com.mukesh.countrylistdemo.data.repository.helper.Resource

/**
Created by Mukesh on 27/02/23
 **/

class AuthRepositoryImpl(private val authDataSource: AuthDataSource): BaseRepositoryImpl(),AuthRepository {
    override suspend fun getAuthToken(): Resource<ResponseAuthToken> {
        return safeApiCall {authDataSource.getAuthCode()}
    }


}