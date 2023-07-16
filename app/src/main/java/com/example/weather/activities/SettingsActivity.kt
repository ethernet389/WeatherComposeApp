@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.weather.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weather.R
import com.example.weather.Settings
import com.example.weather.screens.settings.OptionButton
import com.example.weather.screens.settings.OptionText
import com.example.weather.ui.theme.BlueLight
import com.example.weather.ui.theme.WeatherTheme
import com.yariksoffice.lingver.Lingver

class SettingsActivity : ComponentActivity() {
    private fun setNewLocale(language: String){
        Lingver.getInstance().setLocale(context = this, language = language)
    }

    private fun restart() {
        val i = Intent(this, MainActivity::class.java)
        startActivity(i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK))
    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Reaction to press on back button
        onBackPressedDispatcher
            .addCallback(this, object: OnBackPressedCallback(true){
                override fun handleOnBackPressed() {
                    val intent = Intent(
                        this@SettingsActivity,
                        MainActivity::class.java
                    )
                    this@SettingsActivity.startActivity(intent)
                    this@SettingsActivity.finish()
                }
            })
        val settingsPrefs = getSharedPreferences(Settings.PREFERENCE, MODE_PRIVATE)
        setContent {
            WeatherTheme {
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
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val weatherApiKey = remember {
                        mutableStateOf(
                            settingsPrefs.getString(Settings.WEATHER_API_KEY, "")!!
                        )
                    }
                    val cityName = remember {
                        mutableStateOf(
                            settingsPrefs.getString(Settings.CITY_NAME, "")!!
                        )
                    }
                    val language = remember {
                        mutableStateOf(
                            settingsPrefs.getString(Settings.APP_LANGUAGE, "en")!!
                        )
                    }
                    Column(
                        modifier = Modifier
                            .padding(5.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(BlueLight),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        OptionText(
                            label = getString(R.string.city_option_label),
                            placeholder = getString(R.string.city_option_placeholder),
                            iconId = R.drawable.baseline_home_24,
                            optionTextState = cityName
                        )
                        OptionText(
                            label = getString(R.string.weather_api_key_option_label),
                            placeholder = getString(R.string.weather_api_key_opion_placeholder),
                            iconId = R.drawable.baseline_key_24,
                            optionTextState = weatherApiKey
                        )
                        OutlinedCard(
                            modifier = Modifier
                                .padding(5.dp)
                                .fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp),
                            border = BorderStroke(1.dp, Color.White),
                            colors = CardDefaults
                                .outlinedCardColors(
                                    containerColor = Color(0x00000000)
                                )
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(5.dp)
                                    .fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically){
                                    Text(
                                        text = getString(R.string.language_option_header),
                                        fontSize = 20.sp,
                                        color = Color.White
                                    )
                                    Icon(
                                        modifier = Modifier.padding(start = 3.dp),
                                        painter = painterResource(R.drawable.baseline_language_24),
                                        contentDescription = "Language icon",
                                        tint = Color.White
                                    )
                                }
                                Row(
                                    modifier = Modifier
                                        .padding(5.dp)
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceAround,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    OptionButton(
                                        optionText = "Русский",
                                        onClick = {
                                            language.value = "ru"
                                        }
                                    )
                                    OptionButton(
                                        optionText = "English",
                                        onClick = {
                                            language.value = "en"
                                        }
                                    )
                                }
                            }
                        }
                        OutlinedButton(
                            modifier = Modifier
                                .padding(5.dp),
                            onClick = {
                                val settingsEditor = settingsPrefs.edit()
                                settingsEditor.putString(Settings.CITY_NAME, cityName.value)
                                settingsEditor.putString(Settings.WEATHER_API_KEY, weatherApiKey.value)
                                settingsEditor.putString(Settings.APP_LANGUAGE, language.value)
                                settingsEditor.apply()
                                setNewLocale(language.value)
                                restart()
                            },
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = Color(0x00000000),
                                contentColor = Color.White
                            ),
                            border = BorderStroke(1.dp, Color.White)
                        ) {
                            Row(
                                modifier = Modifier
                                    .padding(5.dp)
                            ){
                                Text(text = getString(R.string.update_settings_button))
                                Icon(
                                    painter = painterResource(R.drawable.baseline_update_24),
                                    contentDescription = "Update Icon"
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}