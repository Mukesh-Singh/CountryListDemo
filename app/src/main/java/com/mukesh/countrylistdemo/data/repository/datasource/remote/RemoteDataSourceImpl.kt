package com.mukesh.countrylistdemo.data.repository.datasource.remote

import com.mukesh.countrylistdemo.data.repository.datasource.remote.api.CountryApiService
import com.mukesh.countrylistdemo.data.repository.datasource.remote.network_model.ResponseCountry
import com.mukesh.countrylistdemo.data.repository.datasource.remote.network_model.ResponseProvince
import retrofit2.Response

/**
Created by Mukesh on 27/02/23
 **/

class RemoteDataSourceImpl(private val countryApiService: CountryApiService): RemoteDataSource {
    override suspend fun getCountryList(): Response<List<ResponseCountry>> {
        return countryApiService.getCountries()
    }

    override suspend fun getProvinceList(country: String): Response<List<ResponseProvince>> {
        return countryApiService.getProvinceList(country)
    }

}