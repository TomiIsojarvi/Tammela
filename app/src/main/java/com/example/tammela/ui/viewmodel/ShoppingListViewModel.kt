package com.example.tammela.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tammela.data.model.ShoppingItem
import com.example.tammela.data.model.ShoppingListStatus
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.result.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ShoppingListViewModel : ViewModel() {
    private val _status = MutableStateFlow(ShoppingListStatus("", "", ""))
    val status: StateFlow<ShoppingListStatus> = _status

    private var _shoppingList = MutableStateFlow<Array<ShoppingItem>?>(emptyArray())
    var shoppingList : StateFlow<Array<ShoppingItem>?> = _shoppingList

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun deleteItemFromShoppingList(user: String, id: Int) {
        val url = "https://www.isoseppo.fi/eTammela/api/shoppingList/shoppingItem.php?user=$user&ope=delete&id=$id"
        val header = emptyMap<String, String>()

        viewModelScope.launch(Dispatchers.IO) {
            Fuel.get(url)
                .header(header)
                .responseObject(ShoppingListStatus.Deserializer()) { _, _, result ->
                    when (result) {
                        is Result.Failure -> {
                            val ex = result.getException()
                            // Handle the error appropriately
                            println("Error fetching status: ${ex.message}")
                        }
                        is Result.Success -> {
                            val (data, _) = result
                            data?.let {
                                _status.value = it
                                _shoppingList.value = ShoppingItem.Deserializer().deserialize(it.reply) ?: emptyArray()
                            }
                        }
                    }
                }
        }
    }

    fun editItemFromShoppingList(user: String, id: Int, itemDesc: String) {
        val url = "https://www.isoseppo.fi/eTammela/api/shoppingList/shoppingItem.php?user=$user&desc=$itemDesc&ope=edit&id=$id"
        val header = emptyMap<String, String>()

        viewModelScope.launch(Dispatchers.IO) {
            Fuel.get(url)
                .header(header)
                .responseObject(ShoppingListStatus.Deserializer()) { _, _, result ->
                    when (result) {
                        is Result.Failure -> {
                            val ex = result.getException()
                            // Handle the error appropriately
                            println("Error fetching status: ${ex.message}")
                        }
                        is Result.Success -> {
                            val (data, _) = result
                            data?.let {
                                _status.value = it
                                _shoppingList.value = ShoppingItem.Deserializer().deserialize(it.reply) ?: emptyArray()
                            }
                        }
                    }
                }
        }
    }

    fun addItemToShoppingList(user : String, itemDesc: String) {
        val url = "https://www.isoseppo.fi/eTammela/api/shoppingList/shoppingItem.php?user=$user&desc=$itemDesc&ope=add"
        val header = emptyMap<String, String>()

        viewModelScope.launch(Dispatchers.IO) {
            Fuel.get(url)
                .header(header)
                .responseObject(ShoppingListStatus.Deserializer()) { _, _, result ->
                    when (result) {
                        is Result.Failure -> {
                            val ex = result.getException()
                            // Handle the error appropriately
                            println("Error fetching status: ${ex.message}")
                        }
                        is Result.Success -> {
                            val (data, _) = result
                            data?.let {
                                _status.value = it
                                _shoppingList.value = ShoppingItem.Deserializer().deserialize(it.reply) ?: emptyArray()
                            }
                        }
                    }
                }
        }
    }

    fun getShoppingList() {
        val url = "https://www.isoseppo.fi/eTammela/api/shoppingList/shoppingItem.php?ope=get"
        val header = emptyMap<String, String>()

        viewModelScope.launch(Dispatchers.IO) {
            Fuel.get(url)
                .header(header)
                .responseObject(ShoppingListStatus.Deserializer()) { _, _, result ->
                    when (result) {
                        is Result.Failure -> {
                            val ex = result.getException()
                            // Handle the error appropriately
                            println("Error fetching status: ${ex.message}")
                        }
                        is Result.Success -> {
                            val (data, _) = result
                            data?.let {
                                _status.value = it
                                _shoppingList.value = ShoppingItem.Deserializer().deserialize(it.reply) ?: emptyArray()
                            }
                        }
                    }
                }
        }
    }
}