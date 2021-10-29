package com.example.testapplication.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import com.example.testapplication.ui.main.dto.City
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import javax.inject.Inject

class MainViewModel @Inject constructor(val mapper: ObjectMapper) : ViewModel() {
    private val cityDetails: StateLiveData<City> = StateLiveData()

    fun parseCityDetails(stringToParse: String) {
        try {
            val city = mapper.readValue<City>(stringToParse)
            cityDetails.postSuccess(city)
        } catch (e: Exception) {
            cityDetails.postError(e)
        }
    }

    fun getCityDetailsObservable(): StateLiveData<City> {
        return cityDetails
    }
}