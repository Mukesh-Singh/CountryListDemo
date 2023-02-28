package com.mukesh.countrylistdemo.data.repository.auth

import android.content.SharedPreferences


import javax.inject.Inject

/**
Created by Mukesh on 27/02/23
 **/

class AuthTokenManager @Inject constructor(
    private val preference: SharedPreferences
){
    companion object{
        private const val AUTH_TOKEN ="AUTH_TOKEN"
    }
    fun getAuthToken(): String?{
        return preference.getString(AUTH_TOKEN,null)
    }
    fun saveToken(token: String?){
        preference.edit().putString(AUTH_TOKEN,token).apply()
    }
}