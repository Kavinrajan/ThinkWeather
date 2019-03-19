package com.kvn.a.weather42.constant

import com.kvn.a.weather42.BuildConfig


object Constants {
    const val baseURL: String = BuildConfig.BASE_URL
    const val apiKEY = BuildConfig.weather_key
    val url = "http://api.openweathermap.org/data/2.5/forecast/"
    val COMPLETE_URL = "$baseURL&APPID=$apiKEY"

}
