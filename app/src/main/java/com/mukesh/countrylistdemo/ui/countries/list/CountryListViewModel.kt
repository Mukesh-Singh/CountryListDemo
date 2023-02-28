package com.mukesh.countrylistdemo.ui.countries.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mukesh.countrylistdemo.data.model.Country
import com.mukesh.countrylistdemo.data.repository.CountryRepository
import com.mukesh.countrylistdemo.data.repository.error.ApplicationError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Mukesh on 27/02/23
 * CountryList ViewModel created for country list UI and the logic on the screen
 */


@HiltViewModel
class CountryListViewModel @Inject constructor(
    private val countryRepository: CountryRepository
) : ViewModel() {
    private val _countryList: MutableLiveData<List<Country>> = MutableLiveData()
    val countryList : LiveData<List<Country>> = _countryList
    private val _dataLoadingProgress: MutableLiveData<Boolean> = MutableLiveData(false)
    val dataLoadingProgress : LiveData<Boolean> = _dataLoadingProgress
    private val _eventChannel =  Channel<Event>()
    val eventFlow = _eventChannel.receiveAsFlow()

    sealed class Event{
        data class CountrySelectedEvent(val country: Country): Event()
        data class ErrorEvent(val message: String): Event()
    }

    init {
        getCountryList()
    }

    /**
     * Get country list
     */
    fun getCountryList(){
        _dataLoadingProgress.value = true
        viewModelScope.launch {
            val  response = countryRepository.getCountryList()
            _dataLoadingProgress.postValue(false)
            response.data?.let { _countryList.postValue(it) }
            response.error?.let {
                val errorMessage = it.message?:it.throwable?.message
                if (!errorMessage.isNullOrEmpty()){
                    triggerEvent(Event.ErrorEvent(errorMessage))
                }
            }

        }
    }

    /**
     * Trigger any event
     */
    fun triggerEvent(event: Event) = viewModelScope.launch {
        _eventChannel.send(event)
    }

}