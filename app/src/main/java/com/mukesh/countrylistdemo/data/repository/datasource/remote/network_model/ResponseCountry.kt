package com.mukesh.countrylistdemo.data.repository.datasource.remote.network_model


import com.google.gson.annotations.SerializedName

/**
 * Country Model for api response
 */
data class ResponseCountry(
    @SerializedName("country_name")
    val countryName: String? = null, // Afghanistan
    @SerializedName("country_phone_code")
    val countryPhoneCode: Int? = null, // 93
    @SerializedName("country_short_name")
    val countryShortName: String? = null // AF
)