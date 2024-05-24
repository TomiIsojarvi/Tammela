package com.example.tammela.data.model

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson
import com.google.gson.JsonParseException

data class RemoteHistory(
    val rowId: Int,
    val time: String,
    val user: String,
    val state: String
) {
    class Deserializer : ResponseDeserializable<Array<RemoteHistory>> {
        override fun deserialize(content: String): Array<RemoteHistory>? {
            return try {
                Gson().fromJson(content, Array<RemoteHistory>::class.java)
            } catch (e: JsonParseException) {
                e.printStackTrace()
                null
            }
        }
    }
}