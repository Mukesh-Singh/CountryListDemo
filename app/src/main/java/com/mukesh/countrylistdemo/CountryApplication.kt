package com.mukesh.countrylistdemo

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp

/**
Created by Mukesh on 27/02/23
 **/

@HiltAndroidApp
class CountryApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.TOKEN.trim().isEmpty() || BuildConfig.EMAIL.trim().isEmpty()){
            Log.e("BuildError", "Please visit this url https://www.universal-tutorial.com/rest-apis/free-rest-api-for-country-state-city and generate a token, then put that token and email id build.gradle(:app) file in TOKEN and EMAIL variable.")
        }
    }
}