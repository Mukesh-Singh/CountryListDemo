package com.mukesh.countrylistdemo.data.repository.error

/**
Created by Mukesh on 27/02/23
 **/

data class ApplicationError private constructor(
    val code: Int? = null,
    val message: String? = null,
    val throwable: Throwable?= null
){
    companion object{
        const val HTTP_EXCEPTION = 1000;
        const val IO_EXCEPTION = 1001;
        const val UNKNOWN_EXCEPTION = 1000;
        fun create(code: Int? = null, message: String? = null, throwable: Throwable?= null): ApplicationError{
            return ApplicationError(code,message,throwable)
        }
    }
}
