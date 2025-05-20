package com.example.weatherapp.model.Weather
import com.google.gson.annotations.SerializedName

//TODO
// Create data class CurrentWeatherResponse (Refer to API Response)
// Hint: Refer to Wind Data Class

data class CurrentWeatherResponse(
    @SerializedName("coord")
    val coord: Coord = Coord(),

    @SerializedName("weather")
    val weather: List<WeatherItem> = listOf(),

    @SerializedName("base")
    val base: String = "",

    @SerializedName("main")
    val main: Main = Main(),

    @SerializedName("visibility")
    val visibility: Int = 0,

    @SerializedName("wind")
    val wind: Wind = Wind(),

    @SerializedName("dt")
    val dt: Long = 0,

    @SerializedName("sys")
    val sys: Sys = Sys(),

    @SerializedName("timezone")
    val timezone: Int = 0,

    @SerializedName("id")
    val id: Int = 0,

    @SerializedName("name")
    val name: String = "",

    @SerializedName("cod")
    val cod: Int = 0
)