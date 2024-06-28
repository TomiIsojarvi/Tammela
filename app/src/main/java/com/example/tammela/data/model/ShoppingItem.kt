package com.example.tammela.data.model

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson
import com.google.gson.JsonParseException

data class ShoppingItem(
    val rowId: Int,
    val user: String,
    val item: String,
    val createDate: String,
    val status: String,
    val closed: String,
    val closedBy: String
) {
    class Deserializer : ResponseDeserializable<Array<ShoppingItem>> {
        override fun deserialize(content: String): Array<ShoppingItem>? {
            return try {
                Gson().fromJson(content, Array<ShoppingItem>::class.java)
            } catch (e: JsonParseException) {
                e.printStackTrace()
                null
            }
        }
    }
}