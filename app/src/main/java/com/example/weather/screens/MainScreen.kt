package com.example.weather.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.weather.Const
import com.example.weather.R
import com.example.weather.data.WeatherData
import com.example.weather.data.updateWeatherData
import com.example.weather.ui.theme.BlueLight
import kotlin.math.max
import kotlin.math.roundToInt

@Composable
fun CurrentCard(weatherState: MutableState<WeatherData>) {
    val minTemp = try{
        weatherState.value.days[0].minTemp
    } catch (t: IndexOutOfBoundsException){
        "-0"
    }
    val maxTemp = try{
        weatherState.value.days[0].maxTemp
    } catch (t: IndexOutOfBoundsException){
        "-0"
    }
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = BlueLight),
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier
                        .padding(5.dp),
                    text = weatherState.value.current.updateTime,
                    fontSize = 15.sp,
                    color = Color.White
                )
                AsyncImage(
                    modifier = Modifier
                        .size(64.dp)
                        .padding(start = 8.dp, end = 8.dp),
                    model = "https:${weatherState.value.current.iconUrl}",
                    contentDescription = "Weather icon",
                    placeholder = painterResource(R.drawable.baseline_self_improvement_24)
                )
            }
            Text(
                text = weatherState.value.city,
                fontSize = 24.sp,
                color = Color.White
            )
            Text(
                text = "${weatherState.value.current.temp.toFloat().roundToInt()}°C",
                fontSize = 65.sp,
                color = Color.White
            )
            Text(
                text = weatherState.value.current.description,
                fontSize = 16.sp,
                color = Color.White
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = { /*TODO*/ }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_search_24),
                        contentDescription = "Search icon",
                        tint = Color.White
                    )
                }
                Text(
                    text = "${maxTemp.toFloat().roundToInt()}°C/${minTemp.toFloat().roundToInt()}°C",
                    fontSize = 16.sp,
                    color = Color.White
                )
                IconButton(
                    onClick = {
                        updateWeatherData("Irkutsk", 3, Const.API_KEY, weatherState)
                    }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_cloud_sync_24),
                        contentDescription = "Sync icon",
                        tint = Color.White
                    )
                }
            }
        }
    }
}
