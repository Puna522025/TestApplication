package com.example.testapplication

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.testapplication.ui.main.dto.City
import com.example.testapplication.ui.main.viewmodel.MainViewModel
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.example.testapplication.ui.main.viewmodel.StateData
import io.mockk.MockKAnnotations
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class MainViewModelTest {
    @Rule
    @JvmField
    var instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    private lateinit var mainViewModel: MainViewModel

    val mapper: ObjectMapper = jacksonObjectMapper()

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)

        mainViewModel = MainViewModel(mapper)
    }

    @Test
    fun `return city data when data is as expected`() {
        mainViewModel.parseCityDetails(cityDetailsJson)
        val cityData: StateData<City>? = mainViewModel.getCityDetailsObservable().value

        assertThat(cityData).isNotNull
        assertThat(cityData?.getData()?.cityDetails?.rank).isEqualTo(7)
    }

    @Test
    fun `return city data when data is with extra invalid details`() {
        mainViewModel.parseCityDetails(cityDetailsJsonWithInvalidDetails)
        val cityData: StateData<City>? = mainViewModel.getCityDetailsObservable().value

        assertThat(cityData).isNotNull
        assertThat(cityData?.getData()?.cityDetails?.rank).isEqualTo(7)
    }

    @Test
    fun `return city data when data is corrupted`() {
        mainViewModel.parseCityDetails(cityDetailsJsonWithCorruptedData)
        val cityData: StateData<City>? = mainViewModel.getCityDetailsObservable().value

        assertThat(cityData).isNotNull
        assertThat(cityData?.getStatus()).isEqualTo(StateData.DataStatus.ERROR)
    }

    companion object {
        val cityDetailsJson = "{\"city\": {\n" +
                "\"name\": \"Paris\",\n" +
                "\"rank\": 7,\n" +
                "\"temperature\": 17.00,\n" +
                "\"location\": {\"lat\": 48.8588376, \"lon\": 2.2768491}\n" +
                "}\n" +
                "}"

        val cityDetailsJsonWithInvalidDetails = "{\"city\": {\n" +
                "\"name\": \"Paris\",\n" +
                "\"rank\": 7,\n" +
                "\"temperature\": 17.00,\n" +
                "\"place\": \"Eiffel Tower\",\n" +
                "\"location\": {\"lat\": 48.8588376, \"lon\": 2.2768491}\n" +
                "}\n" +
                "}"

        val cityDetailsJsonWithCorruptedData = "{\"city\": {\n" +
                "\"name\": \"Paris\",\n" +
                "\"rank\": 7,\n" +
                "\"temperature\": 17.00,\n" +
                "\"location\": {\"lat\": 48.8588376, \"lon\": 2.2768491}\n" +
                "}\n"
    }
}