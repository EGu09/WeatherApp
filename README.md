# Weather App - EEP 523 Homework Assignment 3

## Overview

This Weather App is a simple, elegant mobile application built with Kotlin for Android that allows users to check current weather conditions for cities around the world. The app fetches real-time weather data from the OpenWeatherMap API and displays it in a clean, user-friendly interface.

## Features

- Search for weather by city name
- Display of current weather conditions including:
  - Current temperature
  - Weather condition (sunny, cloudy, etc.)
  - Min/max temperature
  - Sunrise and sunset times
  - Wind speed
  - Air pressure
  - Humidity percentage
- Automatic loading of local weather on startup
- Error handling for invalid city names, network issues, etc.
- Clean UI with responsive design

## Architecture

The application follows the MVVM (Model-View-ViewModel) architecture pattern:

- **Model**: Data classes that represent the structure of the API response
- **View**: XML layouts that define the UI
- **ViewModel**: Manages UI-related data and handles business logic

## Development Process

### Time Spent

The app took approximately 10 hours to complete:
- 2 hours for initial setup and API integration
- 3 hours for UI design and implementation
- 2 hours for error handling and edge cases
- 2 hours for refinement and testing
- 1 hour for documentation

### Most Challenging Parts

1. **JSON Parsing**: Dealing with nested JSON objects in the API response required careful model design. The "clouds" field in particular was tricky as it was an object rather than a primitive type.

2. **Error Handling**: Implementing robust error handling for various scenarios (invalid city, no internet, etc.) while maintaining a good user experience was challenging.

3. **UI Design**: Creating an appealing and readable UI that works well on different screen sizes required several iterations, especially for proper color contrast on the gradient background.

4. **Location Permissions**: Although the current implementation uses a default city (Seattle), the groundwork for implementing the location-based weather feature was complex due to Android's permission system.

## Resources Used

### Official Documentation

- [Android Developer Guides](https://developer.android.com/guide)
- [Kotlin Documentation](https://kotlinlang.org/docs/home.html)
- [OpenWeatherMap API Documentation](https://openweathermap.org/api)

### Libraries and Tools

- Retrofit/OkHttp for API calls (plans for future implementation)
- Gson for JSON parsing
- Material Components for UI elements

### Online Resources

- Stack Overflow:
  - [Working with HttpURLConnection in Kotlin](https://stackoverflow.com/questions/46177133/httpurlconnection-in-kotlin)
  - [Parsing JSON in Kotlin](https://stackoverflow.com/questions/41928803/how-to-parse-json-in-kotlin)
  - [Android Gradient Backgrounds](https://stackoverflow.com/questions/2016249/how-to-generate-random-color-in-android)

- Tutorials:
  - [MVVM Architecture in Android](https://www.geeksforgeeks.org/mvvm-model-view-viewmodel-architecture-pattern-in-android/)
  - [Weather App UI Design](https://www.behance.net/gallery/89849815/Weather-App-UI-Concept)
  - [Making HTTP Requests on Android](https://www.tutorialspoint.com/making-http-requests-in-android)

- GitHub Samples:
  - [android-architecture-components](https://github.com/android/architecture-components-samples)

## Future Improvements

- Implement the bonus feature to show weather for the device's current location
- Add a 5-day forecast
- Add unit tests for better code reliability
- Implement Retrofit for more elegant API calls
- Add more weather icons for different conditions
- Support for dark/light themes
- Add more detailed weather information
