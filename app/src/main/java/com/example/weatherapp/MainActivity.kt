package com.example.weatherapp

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.cardview.widget.CardView
import com.example.weatherapp.model.Weather.CurrentWeatherResponse
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {

    // API Key and URL
    private val API_KEY = "a1cba2bd3462b930be955c7a6adf640c"
    private val BASE_URL = "https://api.openweathermap.org/data/2.5/weather"
    private val TAG = "MainActivity"

    // UI Components
    private lateinit var cityEditText: EditText
    private lateinit var searchButton: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var weatherContainer: RelativeLayout
    private lateinit var errorContainer: RelativeLayout
    private lateinit var errorMessageTextView: TextView

    // Card components
    private lateinit var mainWeatherCard: CardView
    private lateinit var sunTimesCard: CardView
    private lateinit var weatherDetailsCard: CardView

    // Weather data display components
    private lateinit var cityNameTextView: TextView
    private lateinit var dateTextView: TextView
    private lateinit var weatherIcon: ImageView
    private lateinit var temperatureTextView: TextView
    private lateinit var weatherConditionTextView: TextView
    private lateinit var minMaxTempTextView: TextView
    private lateinit var sunriseTextView: TextView
    private lateinit var sunsetTextView: TextView
    private lateinit var windTextView: TextView
    private lateinit var pressureTextView: TextView
    private lateinit var humidityTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Initialize views
        initializeViews()

        // Initially hide the weather information and error message views
        weatherContainer.visibility = View.GONE
        errorContainer.visibility = View.GONE
        progressBar.visibility = View.GONE

        // Set search button click listener
        searchButton.setOnClickListener {
            val cityName = cityEditText.text.toString().trim()

            // Dismiss keyboard
            dismissKeyboard()

            // Check if city name is not empty
            if (cityName.isNotEmpty()) {
                // Show progress bar while loading
                progressBar.visibility = View.VISIBLE
                weatherContainer.visibility = View.GONE
                errorContainer.visibility = View.GONE

                // Build URL with API key
                val urlString = "$BASE_URL?q=$cityName&units=metric&appid=$API_KEY"

                // Fetch weather data
                fetchWeatherData(urlString).start()
            } else {
                // Show error if city name is empty
                showError(getString(R.string.error_city_blank))
            }
        }

        // Get default city weather on start
        fetchCurrentLocationWeather()
    }

    private fun initializeViews() {
        // UI control elements
        cityEditText = findViewById(R.id.cityEditText)
        searchButton = findViewById(R.id.searchButton)
        progressBar = findViewById(R.id.progressBar)
        weatherContainer = findViewById(R.id.weatherContainer)
        errorContainer = findViewById(R.id.errorContainer)
        errorMessageTextView = findViewById(R.id.errorText)

        // Card components
        mainWeatherCard = findViewById(R.id.mainWeatherCard)
        sunTimesCard = findViewById(R.id.sunTimesCard)
        weatherDetailsCard = findViewById(R.id.weatherDetailsCard)

        // Weather display elements
        cityNameTextView = findViewById(R.id.cityNameText)
        dateTextView = findViewById(R.id.dateText)
        weatherIcon = findViewById(R.id.weatherIcon)
        temperatureTextView = findViewById(R.id.temperatureText)
        weatherConditionTextView = findViewById(R.id.weatherConditionText)
        minMaxTempTextView = findViewById(R.id.minMaxTempText)
        sunriseTextView = findViewById(R.id.sunriseText)
        sunsetTextView = findViewById(R.id.sunsetText)
        windTextView = findViewById(R.id.windText)
        pressureTextView = findViewById(R.id.pressureText)
        humidityTextView = findViewById(R.id.humidityText)
    }

    private fun dismissKeyboard() {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val currentFocusedView = currentFocus
        currentFocusedView?.let {
            inputMethodManager.hideSoftInputFromWindow(
                it.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }

    fun fetchWeatherData(urlString: String): Thread {
        return Thread {
            try {
                // Create Gson instance for JSON parsing
                val gson = Gson()

                // Initialize URL
                val url = URL(urlString)

                // Open connection
                val connection = url.openConnection() as HttpURLConnection

                // Check if connection is successful
                if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                    Log.d(TAG, "Connection successful")

                    // Read response
                    val reader = BufferedReader(InputStreamReader(connection.inputStream))
                    val response = StringBuilder()
                    var line = reader.readLine()

                    while (line != null) {
                        response.append(line)
                        line = reader.readLine()
                    }

                    // Close reader
                    reader.close()

                    // Parse JSON response
                    val weatherResponse = gson.fromJson(response.toString(), CurrentWeatherResponse::class.java)

                    // Update UI with weather data
                    updateWeatherUI(weatherResponse)

                } else {
                    // Handle error response
                    Log.e(TAG, "Connection failed with response code: ${connection.responseCode}")

                    if (connection.responseCode == HttpURLConnection.HTTP_NOT_FOUND) {
                        showError(getString(R.string.error_city_not_found))
                    } else {
                        showError(getString(R.string.error_something_wrong))
                    }
                }

                // Disconnect
                connection.disconnect()

            } catch (e: Exception) {
                Log.e(TAG, "Exception in network call: ${e.message}")
                e.printStackTrace()

                if (!isNetworkAvailable()) {
                    showError(getString(R.string.error_no_internet))
                } else {
                    showError(getString(R.string.error_something_wrong))
                }
            }
        }
    }

    private fun updateWeatherUI(weather: CurrentWeatherResponse) {
        runOnUiThread {
            kotlin.run {
                // Hide progress and error containers
                progressBar.visibility = View.GONE
                errorContainer.visibility = View.GONE

                // Show weather container
                weatherContainer.visibility = View.VISIBLE

                // Update city name
                cityNameTextView.text = weather.name

                // Update date
                dateTextView.text = getCurrentDate()

                // Update temperature
                temperatureTextView.text = getString(R.string.temperature_format, weather.main.temp.toInt())

                // Update weather condition
                val weatherDescription = weather.weather.firstOrNull()?.description ?: ""
                weatherConditionTextView.text = weatherDescription.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
                }

                // Update min/max temperature
                minMaxTempTextView.text = getString(
                    R.string.min_max_temp_format,
                    weather.main.temp_min.toInt(),
                    weather.main.temp_max.toInt()
                )

                // Update sunrise and sunset times
                sunriseTextView.text = getString(
                    R.string.sunrise_format,
                    formatTime(weather.sys.sunrise)
                )
                sunsetTextView.text = getString(
                    R.string.sunset_format,
                    formatTime(weather.sys.sunset)
                )

                // Update weather details
                windTextView.text = getString(R.string.wind_format, weather.wind.speed)
                pressureTextView.text = getString(R.string.pressure_format, weather.main.pressure)
                humidityTextView.text = getString(R.string.humidity_format, weather.main.humidity)

                // We're not changing the weather icon since we're using it as an info icon now
                // This method is no longer needed but kept for compatibility
                // setWeatherIcon(weather.weather.firstOrNull()?.icon ?: "")

                // Apply card animations for better UX
                applyCardAnimations()
            }
        }
    }

    private fun applyCardAnimations() {
        // Simple fade-in animation for cards
        mainWeatherCard.alpha = 0f
        sunTimesCard.alpha = 0f
        weatherDetailsCard.alpha = 0f

        mainWeatherCard.animate().alpha(1f).setDuration(300).start()
        sunTimesCard.animate().alpha(1f).setDuration(300).setStartDelay(100).start()
        weatherDetailsCard.animate().alpha(1f).setDuration(300).setStartDelay(200).start()
    }

    // This method is no longer needed but kept for backward compatibility
    private fun setWeatherIcon(iconCode: String) {
        // We're now using the weather icon as a static info icon
        // No need to change it based on weather conditions
    }

    private fun showError(message: String) {
        runOnUiThread {
            kotlin.run {
                // Hide progress and weather containers
                progressBar.visibility = View.GONE
                weatherContainer.visibility = View.GONE

                // Show error container and message
                errorContainer.visibility = View.VISIBLE
                errorMessageTextView.text = message

                // Show toast message
                Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun formatTime(timestamp: Long): String {
        val date = Date(timestamp * 1000)
        val format = SimpleDateFormat("hh:mm a", Locale.getDefault())
        return format.format(date)
    }

    private fun getCurrentDate(): String {
        val date = Date()
        val format = SimpleDateFormat("d MMMM yyyy", Locale.getDefault())
        return format.format(date)
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
            return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        } else {
            // For older devices
            @Suppress("DEPRECATION")
            val networkInfo = connectivityManager.activeNetworkInfo
            @Suppress("DEPRECATION")
            return networkInfo != null && networkInfo.isConnected
        }
    }

    private fun fetchCurrentLocationWeather() {
        // For now, just default to Seattle as mentioned in the homework
        val defaultCity = getString(R.string.default_city)

        // Build URL
        val urlString = "$BASE_URL?q=$defaultCity&units=metric&appid=$API_KEY"

        // Show progress bar
        progressBar.visibility = View.VISIBLE

        // Fetch weather data
        fetchWeatherData(urlString).start()
    }
}