package com.example.tammela.data.model

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson
import com.google.gson.JsonParseException

data class Sensor(
    val name: String,
    val time: String,
    val temperature: Double,
    val humidity: Double,
    val battery: Double,
    val mac: String,
    val rssi: Int
) {
    class Deserializer : ResponseDeserializable<Array<Sensor>> {
        override fun deserialize(content: String): Array<Sensor>? {
            return try {
                Gson().fromJson(content, Array<Sensor>::class.java)
            } catch (e: JsonParseException) {
                e.printStackTrace()
                null
            }
        }
    }
}