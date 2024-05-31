package com.example.tammela.data.model

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson
import com.google.gson.JsonParseException

data class UserAuth (
    val system: String,
    val user: String,
    val op_status: String,
    val reply: String
) {
    class Deserializer : ResponseDeserializable<UserAuth> {
        override fun deserialize(content: String): UserAuth? {
            return try {
                Gson().fromJson(content, UserAuth::class.java)
            } catch (e: JsonParseException) {
                e.printStackTrace()
                null
            }
        }
    }
}