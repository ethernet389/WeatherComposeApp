package com.example.weather.data

import android.util.Log
import org.json.JSONObject

data class WeatherData(
    val city: String = "",
    val days: ArrayList<DailyWeatherData> = ArrayList(),
    val current: CurrentWeatherData = CurrentWeatherData()
){
    companion object{
        //obj is a root of response body
        fun parseJSON(json: String): WeatherData{
            if (json.isEmpty()) return WeatherData()
            val obj = JSONObject(json)
            val jsonDays = obj.getJSONObject("forecast").getJSONArray("forecastday")
            val days = ArrayList<DailyWeatherData>()
            for (i in 0 until jsonDays.length()){
                days.add(
                    DailyWeatherData.parseJSON(
                        jsonDays[i].toString()
                    )
                )
            }
            return WeatherData(
                city = obj.getJSONObject("location").getString("name"),
                days = days,
                current = CurrentWeatherData.parseJSON(json)
            ).also { Log.d("Weather", it.toString()) }
        }
    }
}

data class CurrentWeatherData(
    val updateTime: String = "None",
    val temp: String = "-0",
    val description: String = "None",
    val iconUrl: String = ""
){
    companion object{
        //obj is a root of response body
        fun parseJSON(json: String): CurrentWeatherData{
            val obj = JSONObject(json).getJSONObject("current")
            return CurrentWeatherData(
                updateTime = obj.getString("last_updated"),
                temp = obj.getString("temp_c"),
                description = obj.getJSONObject("condition").getString("text"),
                iconUrl = obj.getJSONObject("condition").getString("icon")
            ).also { Log.d("CurrentWeather", it.toString()) }
        }
    }
}

data class DailyWeatherData(
    val date: String = "None",
    val maxTemp: String = "-0",
    val minTemp: String = "-0",
    val description: String = "None",
    val iconUrl: String = "",
    val hours: ArrayList<HourlyWeatherData> = ArrayList()
){
    companion object{
        //obj is a "forecastday" item
        fun parseJSON(json: String): DailyWeatherData{
            if (json.isEmpty()) return DailyWeatherData()
            val obj = JSONObject(json)
            val day = obj.getJSONObject("day")
            val jsonHours = obj.getJSONArray("hour")
            val hours = ArrayList<HourlyWeatherData>()
            for (i in 0 until jsonHours.length())
                hours.add(
                    HourlyWeatherData.parseJSON(
                        jsonHours[i].toString()
                    )
                )
            return DailyWeatherData(
                date = obj.getString("date"),
                maxTemp = day.getString("maxtemp_c"),
                minTemp = day.getString("mintemp_c"),
                description = day.getJSONObject("condition").getString("text"),
                iconUrl = day.getJSONObject("condition").getString("icon"),
                hours = hours
            ).also { Log.d("DailyWeather", it.toString()) }
        }
    }
}

data class HourlyWeatherData(
    val time: String = "None",
    val temp: String = "-0",
    val description: String = "",
    val iconUrl: String = ""
){
    companion object {
        //obj is a "hour" item
        fun parseJSON(json: String): HourlyWeatherData{
            if (json.isEmpty()) return HourlyWeatherData()
            val obj = JSONObject(json)
            return HourlyWeatherData(
                time = obj.getString("time").split(" ")[1],
                temp = obj.getString("temp_c"),
                description = obj.getJSONObject("condition").getString("text"),
                iconUrl = obj.getJSONObject("condition").getString("icon")
            ).also { Log.d("HourlyWeather", it.toString()) }
        }
    }
}