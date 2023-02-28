package com.mukesh.countrylistdemo.data.repository.auth.model


import com.google.gson.JsonElement
import com.google.gson.annotations.SerializedName

data class ResponseError(
    @SerializedName("error")
    val error: JsonElement? = null
){
    data class Error(
        @SerializedName("expiredAt")
        val expiredAt: String? = null, // 2023-02-25T11:59:27.000Z
        @SerializedName("message")
        val message: String? = null, // jwt expired
        @SerializedName("name")
        val name: String? = null // TokenExpiredError
    )
}