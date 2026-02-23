package com.betterV2.api

import Bike
import Station
import kotlinx.coroutines.*
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject

object StationsApi {
    private val client = OkHttpClient()

    suspend fun fetchStations(
        topLat: Double = 49.055, topLon: Double = 2.662,
        botLat: Double = 48.572, botLon: Double = 1.898
    ): List<Station> = withContext(Dispatchers.IO) {
        val url = "https://www.velib-metropole.fr/api/map/details"
            .toHttpUrl().newBuilder()
            .addQueryParameter("gpsTopLatitude", topLat.toString())
            .addQueryParameter("gpsTopLongitude", topLon.toString())
            .addQueryParameter("gpsBotLatitude", botLat.toString())
            .addQueryParameter("gpsBotLongitude", botLon.toString())
            .build()
        val req = Request.Builder().url(url)
            .header("Authorization", "Basic bW9iYTok...")
            .header("User-Agent", "okhttp/5.1.0")
            .header("Accept-Encoding", "gzip")
            .build()
        val body = client.newCall(req).execute().body.string()
        JSONArray(body).let { arr ->
            List(arr.length()) { Station.fromJson(arr.getJSONObject(it)) }
        }
    }

    suspend fun fetchBikes(stationName: String): List<Bike> = withContext(Dispatchers.IO) {
        val json = JSONObject().apply {
            put("disponibility", "yes")
            put("stationName", stationName)
        }.toString()
        val body = json.toRequestBody("application/json; charset=UTF-8".toMediaType())

        val url = "https://www.velib-metropole.fr/api/secured/searchStation"
        val req = Request.Builder().url(url)
            .header("Accept-Encoding", "gzip")
            .header("Authorization", "Bearer null")
            .header("User-Agent", "okhttp/5.1.0")
            .header("Content-Type", "application/json; charset=utf-8")
            .header("Connection", "Keep-Alive")
            .post(body)
            .build()
        val response = client.newCall(req).execute().body.string()
        JSONArray(response).let { arr ->
            List(arr.length()) { Bike.fromJson(arr.getJSONObject(it)) }
        }
    }
}
