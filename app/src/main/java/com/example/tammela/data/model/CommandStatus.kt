package com.example.tammela.data.model

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson
import com.google.gson.JsonParseException

data class CommandStatus(
    val System: String,
    val user: String,
    val op_status: String,
    val reply: String,
    val device: String,
    val extra: String
) {
    class Deserializer : ResponseDeserializable<CommandStatus> {
        override fun deserialize(content: String): CommandStatus? {
            return try {
                Gson().fromJson(content, CommandStatus::class.java)
            } catch (e: JsonParseException) {
                e.printStackTrace()
                null
            }
        }
    }
}