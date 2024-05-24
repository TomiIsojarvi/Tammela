package com.example.tammela.data.model

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson
import com.google.gson.JsonParseException

data class RemoteStatus(
    val System: String,
    val user: String,
    val op_status: String,
    val reply: String,
    val extra: String
) {
    class Deserializer : ResponseDeserializable<RemoteStatus> {
        override fun deserialize(content: String): RemoteStatus? {
            return try {
                Gson().fromJson(content, RemoteStatus::class.java)
            } catch (e: JsonParseException) {
                e.printStackTrace()
                null
            }
        }
    }
}