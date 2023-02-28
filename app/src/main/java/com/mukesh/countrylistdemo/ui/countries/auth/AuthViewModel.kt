package com.mukesh.countrylistdemo.ui.countries.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mukesh.countrylistdemo.data.repository.auth.AuthRepository
import com.mukesh.countrylistdemo.data.repository.auth.AuthTokenManager
import com.mukesh.countrylistdemo.data.repository.auth.model.ResponseAuthToken
import com.mukesh.countrylistdemo.data.repository.helper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Mukesh on 27/02/23
 * Authentication ViewModel
 */
@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val authTokenManager: AuthTokenManager

): ViewModel() {
    private val _authToken: MutableLiveData<Resource<ResponseAuthToken>> = MutableLiveData()
    val authToken : LiveData<Resource<ResponseAuthToken>> = _authToken

    init {
        getAuthToken()
    }

    /**
     * Get the Auth token from repository
     */
    private fun getAuthToken(){
        _authToken.value = Resource.Loading()
        viewModelScope.launch {
            val tokenResponse = authRepository.getAuthToken()
            if (tokenResponse is Resource.Success){
                tokenResponse.data?.auth_token?.let {
                    authTokenManager.saveToken(it)
                }
            }else if (tokenResponse is Resource.Error){
                authTokenManager.saveToken(null)
            }
            _authToken.postValue(tokenResponse)
        }
    }
}