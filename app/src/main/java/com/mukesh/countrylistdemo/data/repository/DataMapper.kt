package com.mukesh.countrylistdemo.data.repository

import com.mukesh.countrylistdemo.data.model.Country
import com.mukesh.countrylistdemo.data.model.Province
import com.mukesh.countrylistdemo.data.repository.datasource.remote.network_model.ResponseCountry
import com.mukesh.countrylistdemo.data.repository.datasource.remote.network_model.ResponseProvince

/**
Created by Mukesh on 27/02/23
 **/

fun ResponseCountry.toCountry(): Country{
    return Country(this.countryName, this.countryPhoneCode,this.countryShortName)
}

fun List<ResponseCountry>.toCountryList(): List<Country>{
    val list = mutableListOf<Country>()
    this.forEach { list.add(it.toCountry()) }
    return list
}

fun ResponseProvince.toProvince(): Province{
    return Province(this.stateName)
}

fun List<ResponseProvince>.toProvinceList(): List<Province>{
    val list = mutableListOf<Province>()
    this.forEach { list.add(it.toProvince()) }
    return list
}