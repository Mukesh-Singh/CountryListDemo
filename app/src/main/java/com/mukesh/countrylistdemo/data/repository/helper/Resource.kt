package com.mukesh.countrylistdemo.data.repository.helper

import com.mukesh.countrylistdemo.data.repository.error.ApplicationError

/**
Created by Mukesh on 27/02/23
 **/

sealed class Resource<T>(
    val data: T? = null,
    val error: ApplicationError? = null
) {

    // We'll wrap our data in this 'Success'
    // class in case of success response from api
    class Success<T>(data: T) : Resource<T>(data = data)

    // We'll pass error message wrapped in this 'Error'
    // class to the UI in case of failure response
    class Error<T>(error: ApplicationError?) : Resource<T>(error = error)

    // We'll just pass object of this Loading
    // class, just before making an api call
    class Loading<T> : Resource<T>()

}