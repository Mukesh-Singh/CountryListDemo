package com.mukesh.countrylistdemo.ui.countries.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import com.mukesh.countrylistdemo.data.model.Country
import com.mukesh.countrylistdemo.data.repository.CountryRepository
import com.mukesh.countrylistdemo.data.repository.error.ApplicationError
import com.mukesh.countrylistdemo.data.repository.helper.Resource
import com.mukesh.countrylistdemo.ui.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*

import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

/**
 * Created by Mukesh on 28/02/23
 */

@OptIn(ExperimentalCoroutinesApi::class)
class CountryListViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    val instantTaskExecuterRule = InstantTaskExecutorRule()

    @Mock
    lateinit var countryRepository: CountryRepository




    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }


    @Test
    fun test_getCountryList_Empty() = runTest {
        Mockito.`when`(countryRepository.getCountryList()).thenReturn(Resource.Success(emptyList<Country>()))
        val countryListViewModel = CountryListViewModel(countryRepository)
        countryListViewModel.getCountryList()
        testDispatcher.scheduler.advanceUntilIdle()
        val result = countryListViewModel.countryList.getOrAwaitValue()
        Assert.assertEquals(0, result.size)
    }

    @Test
    fun test_getCountryList_Data() = runTest {
        val list= mutableListOf<Country>()
        list.add(Country("India",91,"IN"))
        Mockito.`when`(countryRepository.getCountryList()).thenReturn(Resource.Success(list))
        val countryListViewModel = CountryListViewModel(countryRepository)
        countryListViewModel.getCountryList()
        testDispatcher.scheduler.advanceUntilIdle()
        val result = countryListViewModel.countryList.getOrAwaitValue()
        Assert.assertEquals("India", result.get(0).countryName)
    }

    @Test
    fun test_getCountryList_Error() = runTest {
        val errorMessage = "Test error message"
        Mockito.`when`(countryRepository.getCountryList()).thenReturn(Resource.Error(
            ApplicationError.create(message = errorMessage)
        ))
        val countryListViewModel = CountryListViewModel(countryRepository)
        countryListViewModel.getCountryList()
        val result = countryListViewModel.eventFlow.first() as CountryListViewModel.Event.ErrorEvent
        Assert.assertEquals(errorMessage,  result.message)

    }

    @Test
    fun test_getDataLoadingProgress() = runTest {
        Mockito.`when`(countryRepository.getCountryList()).thenReturn(Resource.Success(emptyList<Country>()))
        val countryListViewModel = CountryListViewModel(countryRepository)
        countryListViewModel.getCountryList()
        testDispatcher.scheduler.advanceUntilIdle()
        val result = countryListViewModel.dataLoadingProgress.getOrAwaitValue(time = 5 )
        Assert.assertEquals(false,  result)
    }

    @Test
    fun test_triggerEvent()= runTest {
        val country = Country("India",91,"IN")
        val countryListViewModel = CountryListViewModel(countryRepository)
        countryListViewModel.triggerEvent(CountryListViewModel.Event.CountrySelectedEvent(country))
        val result = countryListViewModel.eventFlow.first() as CountryListViewModel.Event.CountrySelectedEvent
        Assert.assertEquals("India",  result.country.countryName)
    }
}