package com.example.weather.data

import android.util.Log
import androidx.compose.runtime.MutableState
import com.example.weather.Const
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

fun updateWeatherData(city: String, daysCount: Int, apiKey: String, state: MutableState<WeatherData>){
    val retrofit = Retrofit
        .Builder()
        .baseUrl("https://api.weatherapi.com/")
        .build()
    val service = retrofit.create(WeatherApi::class.java)
    service
        .requireWeatherData(city, daysCount, apiKey)
        .enqueue(WeatherCallback(state))
}

class WeatherCallback(private val state: MutableState<WeatherData>): Callback<ResponseBody>{
    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
        if (response.isSuccessful){
            if (response.body() == null) return
            val json = response.body()!!.string()
            Log.d("RetrofitSuccessful", json)
            val newData = WeatherData.parseJSON(json)
            Log.d("RetrofitData", newData.toString())
            state.value = newData
            return
        }
        Log.d("RetrofitNonSuccess", response.body()!!.string())
    }

    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
        Log.d("RetrofitFail", t.message.toString())
    }

}

interface WeatherApi {
    @GET("/v1/forecast.json?aqi=no&alerts=no")
    fun requireWeatherData(
        @Query("q") city: String = "Moscow",
        @Query("days") days: Int = 3,
        @Query("key") apiKey: String
    ): Call<ResponseBody>
}