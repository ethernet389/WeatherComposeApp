package com.example.weather

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
import androidx.compose.ui.unit.dp
import com.example.weather.data.WeatherData
import com.example.weather.data.updateWeatherData
import com.example.weather.screens.CurrentCard
import com.example.weather.screens.WeatherList
import com.example.weather.ui.theme.WeatherTheme

class MainActivity : ComponentActivity() {
    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherTheme {
                val weatherState = remember{
                    mutableStateOf(WeatherData())
                }
                updateWeatherData("Irkutsk", 5, Const.API_KEY, weatherState)
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
                    CurrentCard(weatherState)
                    WeatherList(weatherState)
                }
            }
        }
    }
}

