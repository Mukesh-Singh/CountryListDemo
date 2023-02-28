package com.mukesh.countrylistdemo.data.repository

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.mukesh.countrylistdemo.data.repository.auth.model.ResponseError
import com.mukesh.countrylistdemo.data.repository.error.ApplicationError
import com.mukesh.countrylistdemo.data.repository.helper.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

/**
Created by Mukesh on 27/02/23
 **/


abstract class BaseRepositoryImpl() {

    // we'll use this function in all
    // repos to handle api errors.
    suspend fun <T> safeApiCall(apiToBeCalled: suspend () -> Response<T>): Resource<T> {

        // Returning api response
        // wrapped in Resource class
        return withContext(Dispatchers.IO) {
            try {

                // Here we are calling api lambda
                // function that will return response
                // wrapped in Retrofit's Response class
                val response: Response<T> = apiToBeCalled()

                if (response.isSuccessful) {
                    // In case of success response we
                    // are returning Resource.Success object
                    // by passing our data in it.
                    Resource.Success(data = response.body()!!)
                } else {
                    // parsing api's own custom json error
                    // response in ExampleErrorResponse pojo
                    val errorResponse = convertErrorBody(response.code(),response.message(),response.errorBody())
                    // Simply returning api's own failure message
                    Resource.Error(error = errorResponse)
                }

            } catch (e: HttpException) {
                // Returning HttpException's message
                Resource.Error(ApplicationError.create(ApplicationError.HTTP_EXCEPTION, e.message ?: "Something went wrong",e))
            } catch (e: IOException) {
                // Returning no internet message
                Resource.Error(ApplicationError.create(ApplicationError.IO_EXCEPTION, e.message ?: "Please check your network connection",e))
            } catch (e: Exception) {
                // Returning 'Something went wrong' in case
                Resource.Error(ApplicationError.create(ApplicationError.UNKNOWN_EXCEPTION, e.message ?: "Please check your network connection",e))
            }
        }
    }

    private fun convertErrorBody(code: Int?, message: String?, errorBody: ResponseBody?): ApplicationError {
        var apiMsg: String? = message
        errorBody?.let {
            val stringObj = errorBody.string()
            val errorRes = Gson().fromJson<ResponseError>(stringObj,ResponseError::class.java)
            if (errorRes!=null){
                if (errorRes.error?.asJsonPrimitive?.isJsonObject == true){
                    val error = Gson().fromJson<ResponseError.Error>(errorRes.error,ResponseError.Error::class.java)
                    apiMsg = error.message
                }else{
                    apiMsg = errorRes.error?.asString
                }
            }
        }


        return ApplicationError.create(code, if (message.isNullOrEmpty())apiMsg?:"Something went wrong" else message, Throwable(apiMsg))
    }
}