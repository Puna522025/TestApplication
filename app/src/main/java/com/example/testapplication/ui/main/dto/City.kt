package com.example.testapplication.ui.main.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class City(
    @JsonProperty("city") val cityDetails: CityDetails,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class CityDetails(
    @JsonProperty("name") val name: String,
    @JsonProperty("rank") val rank: Int,
    @JsonProperty("temperature") val temperature: Float
)