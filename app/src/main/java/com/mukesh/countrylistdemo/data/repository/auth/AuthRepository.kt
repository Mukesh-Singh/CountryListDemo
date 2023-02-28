package com.mukesh.countrylistdemo.data.repository.auth

import com.mukesh.countrylistdemo.data.repository.auth.model.ResponseAuthToken
import com.mukesh.countrylistdemo.data.repository.helper.Resource


/**
Created by Mukesh on 27/02/23
 **/

interface AuthRepository {
    suspend fun getAuthToken(): Resource<ResponseAuthToken>
}