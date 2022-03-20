package com.example.thirsty

import org.json.JSONObject as JSONObject

fun parse (response: String) : ArrayList<Marker> {
    val list = ArrayList<Marker>()
    val jObject = JSONObject(response)

    try {
        val jMarkers = jObject.getJSONArray("markers")
        for (i in 0 until jMarkers.length()) { //this took me like two hours. im mentally checked out
            val jID = (jMarkers[i] as JSONObject).getInt("id")
            val jName = (jMarkers[i] as JSONObject).getString("name")
            val jLat = (jMarkers[i] as JSONObject).getDouble("latitude")
            val jLng = (jMarkers[i] as JSONObject).getDouble("longitude")

            list += Marker(jID, jName, jLat, jLng)
        }
    } catch (e : Exception) {
        e.printStackTrace()
    }

    return list
}