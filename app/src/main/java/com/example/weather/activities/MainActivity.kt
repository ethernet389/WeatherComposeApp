package com.example.weather.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.unit.dp
import com.example.weather.R
import com.example.weather.Settings
import com.example.weather.data.WeatherData
import com.example.weather.data.updateWeatherData
import com.example.weather.screens.main.CurrentWeatherCard
import com.example.weather.screens.main.ForecastWeatherList
import com.example.weather.ui.theme.WeatherTheme

class MainActivity : ComponentActivity() {
    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val settingsPrefs = getSharedPreferences(Settings.PREFERENCE, MODE_PRIVATE)
        setContent {
            WeatherTheme {
                val weatherState = remember{
                    mutableStateOf(WeatherData())
                }
                updateWeatherData(
                    city = settingsPrefs.getString(Settings.CITY_NAME, "Moscow")!!,
                    daysCount = 3,
                    apiKey = settingsPrefs.getString(Settings.WEATHER_API_KEY, "")!!,
                    language = settingsPrefs.getString(Settings.APP_LANGUAGE, "ru")!!,
                    state = weatherState
                )
                Image(
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(0.5f),
                    painter = painterResource(R.drawable.blue_sky_background),
                    contentDescription = "background",
                    contentScale = ContentScale.FillBounds
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(5.dp)
                ) {
                    CurrentWeatherCard(
                        weatherState = weatherState,
                        onSettingsClick = {
                            val intent = Intent(
                                this@MainActivity,
                                SettingsActivity::class.java
                            )
                            this@MainActivity.startActivity(intent)
                        },
                        onSyncClick = {
                            updateWeatherData(
                                city = settingsPrefs.getString(Settings.CITY_NAME, "Moscow")!!,
                                daysCount = 3,
                                apiKey = settingsPrefs.getString(Settings.WEATHER_API_KEY, "")!!,
                                language = settingsPrefs.getString(Settings.APP_LANGUAGE, "ru")!!,
                                state = weatherState
                            )
                        }
                    )
                    ForecastWeatherList(weatherState)
                }
            }
        }
    }
}

