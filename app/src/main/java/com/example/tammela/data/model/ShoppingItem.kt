package com.example.tammela.data.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.GsonBuilder
import com.google.gson.InstanceCreator
import com.google.gson.JsonParseException
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

data class ShoppingItem(
    val rowId: Int,
    val user: String,
    val item: String,
    val createDate: String,
    val status: String,
    val closed: String,
    val closedBy: String,
) {
    var isSelected by mutableStateOf(false)

    fun toggleSelection() {
        isSelected = !isSelected
    }

    class Deserializer : ResponseDeserializable<List<ShoppingItem>> {
        override fun deserialize(content: String): List<ShoppingItem>? {
            return try {
                val gsonBuilder = GsonBuilder()
                gsonBuilder.registerTypeAdapter(ShoppingItem::class.java, ShoppingItemInstanceCreator())
                val gson = gsonBuilder.create()
                val type = object : TypeToken<List<ShoppingItem>>() {}.type
                gson.fromJson<List<ShoppingItem>>(content, type)
            } catch (e: JsonParseException) {
                e.printStackTrace()
                null
            }
        }
    }

    class ShoppingItemInstanceCreator : InstanceCreator<ShoppingItem> {
        override fun createInstance(type: Type): ShoppingItem {
            return ShoppingItem(0, "", "", "", "", "", "").apply {
                isSelected = false
            }
        }
    }
}
