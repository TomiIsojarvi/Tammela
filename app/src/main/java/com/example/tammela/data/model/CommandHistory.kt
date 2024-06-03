package com.example.tammela.data.model

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson
import com.google.gson.JsonParseException

data class CommandHistory(
    val rowId: Int,
    val time: String,
    val user: String,
    val state: String,
    val device: String
) {
    class Deserializer : ResponseDeserializable<Array<CommandHistory>> {
        override fun deserialize(content: String): Array<CommandHistory>? {
            return try {
                Gson().fromJson(content, Array<CommandHistory>::class.java)
            } catch (e: JsonParseException) {
                e.printStackTrace()
                null
            }
        }
    }
}