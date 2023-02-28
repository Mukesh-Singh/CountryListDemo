package com.mukesh.countrylistdemo.ui.countries.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mukesh.countrylistdemo.data.model.Province
import com.mukesh.countrylistdemo.data.repository.CountryRepository
import com.mukesh.countrylistdemo.data.repository.helper.Resource
import com.mukesh.countrylistdemo.ui.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.Assert.*

import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

/**
 * Created by Mukesh on 28/02/23
 */
@OptIn(ExperimentalCoroutinesApi::class)
class CountryDetailsViewModelTest {

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
    fun test_getProvinceList_Empty() = runTest {
        val selectedCountry = "India"
        Mockito.`when`(countryRepository.getProvinceList(selectedCountry)).thenReturn(Resource.Success(emptyList<Province>()))
        val viewModel = CountryDetailsViewModel(countryRepository)
        viewModel.setSelectedCountryName(selectedCountry)
        testDispatcher.scheduler.advanceUntilIdle()
        val result = viewModel.provinceList.getOrAwaitValue()
        Assert.assertEquals(0, result.size)
    }

    @Test
    fun test_getProvinceList_With_Data() = runTest {
        val selectedCountry = "India"
        val stateList = mutableListOf<Province>()
        stateList.add(Province("Delhi"))
        Mockito.`when`(countryRepository.getProvinceList(selectedCountry)).thenReturn(Resource.Success(stateList))
        val viewModel = CountryDetailsViewModel(countryRepository)
        viewModel.setSelectedCountryName(selectedCountry)
        testDispatcher.scheduler.advanceUntilIdle()
        val result = viewModel.provinceList.getOrAwaitValue()
        Assert.assertEquals("Delhi", result.get(0).stateName)
    }
}