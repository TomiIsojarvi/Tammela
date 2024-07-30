package com.example.tammela.ui.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
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

    private var _shoppingList = mutableStateListOf<ShoppingItem>()
    var shoppingList: MutableList<ShoppingItem> = _shoppingList

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun getSelectedItems() = shoppingList.filter { it.isSelected }

    fun deleteItemsFromShoppingList(user: String, items: List<ShoppingItem>) {
        for (item in items) {
            deleteItem(user, item.rowId)
        }
    }

    fun deleteItem(user: String, id: Int) {
        val url = "https://www.isoseppo.fi/eTammela/api/shoppingList/shoppingItem.php?user=$user&ope=delete&id=$id"
        val header = emptyMap<String, String>()

        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
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
                                val items = ShoppingItem.Deserializer().deserialize(it.reply) ?: emptyList()
                                _shoppingList.clear()
                                _shoppingList.addAll(items)
                            }
                        }
                    }
                    _isLoading.value = false
                }
        }
    }

    fun editItemFromShoppingList(user: String, id: Int, itemDesc: String) {
        val url = "https://www.isoseppo.fi/eTammela/api/shoppingList/shoppingItem.php?user=$user&desc=$itemDesc&ope=edit&id=$id"
        val header = emptyMap<String, String>()

        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
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
                                val items = ShoppingItem.Deserializer().deserialize(it.reply) ?: emptyList()
                                _shoppingList.clear()
                                _shoppingList.addAll(items)
                            }
                        }
                    }
                    _isLoading.value = false
                }
        }
    }

    fun addItemToShoppingList(user: String, itemDesc: String) {
        val url = "https://www.isoseppo.fi/eTammela/api/shoppingList/shoppingItem.php?user=$user&desc=$itemDesc&ope=add"
        val header = emptyMap<String, String>()

        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
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
                                //println("Status: ${it.status}, Message: ${it.message}, Reply: ${it.reply}")
                                val items = ShoppingItem.Deserializer().deserialize(it.reply) ?: emptyList()
                                _shoppingList.clear()
                                _shoppingList.addAll(items)
                            }
                        }
                    }
                    _isLoading.value = false
                }
        }
    }

    fun getShoppingList() {
        val url = "https://www.isoseppo.fi/eTammela/api/shoppingList/shoppingItem.php?ope=get"
        val header = emptyMap<String, String>()

        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
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
                                val items = ShoppingItem.Deserializer().deserialize(it.reply) ?: emptyList()
                                _shoppingList.clear()
                                _shoppingList.addAll(items)
                            }
                        }
                    }
                    _isLoading.value = false
                }
        }
    }
}
