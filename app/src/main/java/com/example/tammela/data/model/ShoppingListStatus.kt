package com.example.tammela.data.model

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson
import com.google.gson.JsonParseException

data class ShoppingListStatus(
    val user: String,
    val reply: String,
    val op_status: String,
) {
    class Deserializer : ResponseDeserializable<ShoppingListStatus> {
        override fun deserialize(content: String): ShoppingListStatus? {
            return try {
                Gson().fromJson(content, ShoppingListStatus::class.java)
            } catch (e: JsonParseException) {
                e.printStackTrace()
                null
            }
        }
    }
}