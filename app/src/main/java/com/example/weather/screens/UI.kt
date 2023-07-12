package com.example.weather.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.weather.R
import com.example.weather.data.WeatherData
import com.example.weather.ui.theme.BlueLight
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
@Composable
fun WeatherList(weatherState: MutableState<WeatherData>) {
    val tabList = listOf("Hours", "Days")
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 15.dp)
            .clip(RoundedCornerShape(10.dp))
    ) {
        TabRow(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp)),
            selectedTabIndex = pagerState.currentPage,
            indicator = { tabs ->
                TabRowDefaults.Indicator(
                    modifier = Modifier
                        .tabIndicatorOffset(tabs[pagerState.currentPage]),
                    color = Color.LightGray,
                )
            },
            divider = {},
            containerColor = BlueLight
        ) {
            tabList.forEachIndexed { index, item ->
                Tab(
                    selected = false,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    text = {
                        Text(text = item, fontSize = 20.sp, color = Color.White)
                    }
                )
            }
        }
        HorizontalPager(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            state = pagerState,
            pageCount = tabList.size
        ) { index ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {

            }
        }
    }
}

@Composable
fun WeatherCard(
    time: String,
    condition: String,
    temp: String = "",
    maxTemp: String = "",
    minTemp: String = "",
    icon: String
) {
    Card(
        modifier = Modifier
            .padding(top = 5.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = BlueLight)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(BlueLight)
                .clip(RoundedCornerShape(10.dp))
                .padding(3.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp)
            ) {
                Text(text = time, color = Color.Black)
                Text(text = condition, fontSize = 18.sp, color = Color.White)
            }
            Text(
                text = temp.ifEmpty {"${minTemp}/${maxTemp}"},
                fontSize = 30.sp,
                color = Color.White
            )
            AsyncImage(
                modifier = Modifier
                    .size(48.dp)
                    .padding(start = 8.dp, end = 8.dp),
                model = "https:${icon}",
                contentDescription = "Daily/Hourly Weather Icon",
                placeholder = painterResource(R.drawable.baseline_self_improvement_24)
            )
        }
    }
}