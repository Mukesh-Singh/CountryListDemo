package com.mukesh.countrylistdemo.data.repository.datasource.remote.network_model


import com.google.gson.annotations.SerializedName

/**
 * Province model for api response
 */

data class ResponseProvince(
    @SerializedName("state_name")
    val stateName: String? = null // Haut-Ogooue
)