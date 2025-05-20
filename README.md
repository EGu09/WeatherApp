# Weather App
## EEP 523 – Homework Assignment 3

### Overview
This Android application retrieves and displays real-time weather information for any city using the OpenWeatherMap API (v2.5). Upon launch, the app displays weather data for the user's current location. Users can also search for weather details by entering a city name.

### Features
- Displays current weather information for the user's device location (e.g., Seattle by default).
- Allows users to enter a city name and retrieve weather information.
- Displays the following weather details:
  - City name
  - Current temperature
  - Weather condition (e.g., cloudy, sunny)
  - Minimum and maximum temperatures
  - Sunrise and sunset times
  - Wind speed
  - Pressure
  - Humidity
- Error handling includes:
  - Empty input field: Displays "City Name cannot be blank"
  - Invalid city name: Displays "City not found"
  - No internet connection: Displays "Please connect to internet"

### UI Design
- Designed using Figma.
- Layout includes:
  - A weather information card showing all required data.
  - A text input field for entering a city name.
  - A button to trigger the API call.
  - Visual feedback for error scenarios (e.g., toast messages).

### How It Works
1. On launch, the app requests location permission.
2. Uses FusedLocationProviderClient to get current device coordinates.
3. Makes a network request to the OpenWeatherMap API using Retrofit.
4. Parses the response and displays data using model classes.
5. Updates the UI on the main thread.

### Time Spent
- Total time spent: approximately 12 hours
  - UI/UX design in Figma: 2 hours
  - API integration and data parsing: 3 hours
  - Location services integration: 2 hours
  - UI implementation and error handling: 3 hours
  - Testing and documentation: 2 hours

### Challenges Faced
- Handling asynchronous location and network calls efficiently.
- Converting sunrise and sunset UNIX timestamps to human-readable format.
- Mapping complex JSON response into Kotlin data classes.
- Ensuring UI updates occur on the main thread without lag.

### Resources Used
- OpenWeatherMap API documentation: https://openweathermap.org/current
- Android Location API: https://developer.android.com/training/location
- Retrofit: https://square.github.io/retrofit/
- Stack Overflow and Android Developers documentation for troubleshooting and implementation guidance
