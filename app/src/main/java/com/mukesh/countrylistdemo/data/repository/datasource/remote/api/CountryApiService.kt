package com.mukesh.countrylistdemo.data.repository.datasource.remote.api

import com.mukesh.countrylistdemo.data.repository.datasource.remote.network_model.ResponseCountry
import com.mukesh.countrylistdemo.data.repository.datasource.remote.network_model.ResponseProvince
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
Created by Mukesh on 27/02/23
 **/

interface CountryApiService {
    @GET ("countries/")
    suspend fun getCountries(): Response<List<ResponseCountry>>

    @GET ("states/{state}")
    suspend fun getProvinceList(@Path("state") state: String): Response<List<ResponseProvince>>

}