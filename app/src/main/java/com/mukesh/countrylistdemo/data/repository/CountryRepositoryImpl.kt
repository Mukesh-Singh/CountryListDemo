package com.mukesh.countrylistdemo.data.repository

import com.mukesh.countrylistdemo.data.model.Country
import com.mukesh.countrylistdemo.data.model.Province
import com.mukesh.countrylistdemo.data.repository.datasource.remote.RemoteDataSource
import com.mukesh.countrylistdemo.data.repository.helper.Resource

/**
Created by Mukesh on 27/02/23
 **/

class CountryRepositoryImpl(private val remoteDataSource: RemoteDataSource): BaseRepositoryImpl(),CountryRepository {
    override suspend fun getCountryList(): Resource<List<Country>> {
        val response = safeApiCall{
            remoteDataSource.getCountryList()
        }
        response.data?.let {
            return Resource.Success(it.toCountryList())
        }?: kotlin.run {
            return Resource.Error(response.error)
        }
    }

    override suspend fun getProvinceList(country: String): Resource<List<Province>> {
        val response = safeApiCall{
            remoteDataSource.getProvinceList(country)
        }
        response.data?.let {
            return Resource.Success(it.toProvinceList())
        }?: kotlin.run {
            return Resource.Error(response.error)
        }
    }

}