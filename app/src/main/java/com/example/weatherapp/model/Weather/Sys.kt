package com.example.weatherapp.model.Weather

import com.google.gson.annotations.SerializedName


//TODO
// Create data class Sys (Refer to API Response)
// Hint: Refer to Wind Data Class

data class Sys(
    @SerializedName("type")
    val type: Int = 0,

    @SerializedName("id")
    val id: Int = 0,

    @SerializedName("country")
    val country: String = "",

    @SerializedName("sunrise")
    val sunrise: Long = 0,

    @SerializedName("sunset")
    val sunset: Long = 0
)