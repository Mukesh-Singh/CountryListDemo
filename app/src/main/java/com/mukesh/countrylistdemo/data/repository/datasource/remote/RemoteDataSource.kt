package com.mukesh.countrylistdemo.data.repository.datasource.remote


import com.mukesh.countrylistdemo.data.repository.datasource.remote.network_model.ResponseCountry
import com.mukesh.countrylistdemo.data.repository.datasource.remote.network_model.ResponseProvince
import retrofit2.Response

/**
Created by Mukesh on 27/02/23
 **/

interface RemoteDataSource {
    suspend fun getCountryList(): Response<List<ResponseCountry>>
    suspend fun getProvinceList(country: String): Response<List<ResponseProvince>>
}