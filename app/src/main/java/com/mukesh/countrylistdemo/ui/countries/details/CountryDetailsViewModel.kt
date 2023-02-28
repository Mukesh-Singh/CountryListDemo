package com.mukesh.countrylistdemo.ui.countries.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mukesh.countrylistdemo.data.model.Country
import com.mukesh.countrylistdemo.data.model.Province
import com.mukesh.countrylistdemo.data.repository.CountryRepository
import com.mukesh.countrylistdemo.ui.countries.list.CountryListViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
/**
* Created by Mukesh on 27/02/23
* Country details ViewModel
*/


@HiltViewModel
class CountryDetailsViewModel @Inject constructor(
    private val countryRepository: CountryRepository
) : ViewModel() {
    var selectedCountryName: String =""
    private set
    private val _provinceList: MutableLiveData<List<Province>> = MutableLiveData()
    val provinceList : LiveData<List<Province>> = _provinceList
    private val _dataLoadingProgress: MutableLiveData<Boolean> = MutableLiveData(false)
    val dataLoadingProgress : LiveData<Boolean> = _dataLoadingProgress
    private val _eventChannel =  Channel<Event>()
    val eventFlow = _eventChannel.receiveAsFlow()

    sealed class Event{
        data class ErrorEvent(val message: String): Event()
    }

    /**
     * Set selected country, and call the province list api
     */
    fun setSelectedCountryName(name: String){
        selectedCountryName = name
        getProvinceList()
    }

    /**
     * Get province list of selected country
     */
    private fun getProvinceList(){
        if (selectedCountryName.isEmpty())
            return
        _dataLoadingProgress.value = true
        viewModelScope.launch {
            val  response = countryRepository.getProvinceList(selectedCountryName)
            _dataLoadingProgress.postValue(false)
            response.data?.let { _provinceList.postValue(it) }
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