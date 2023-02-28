package com.mukesh.countrylistdemo.data.repository

import com.mukesh.countrylistdemo.data.model.Country
import com.mukesh.countrylistdemo.data.model.Province
import com.mukesh.countrylistdemo.data.repository.helper.Resource


/**
Created by Mukesh on 27/02/23
 **/

interface CountryRepository {
    /**
     * Get the country list from repository
     */
    suspend fun getCountryList(): Resource<List<Country>>
    /**
     * Get the province list from repository
     */
    suspend fun getProvinceList(country: String): Resource<List<Province>>
}