package com.crystal.todayprice.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.crystal.todayprice.data.Item
import com.crystal.todayprice.data.Price
import com.crystal.todayprice.repository.ItemRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ItemViewModel(private val itemRepository: ItemRepository): ViewModel() {

    private val _prices = MutableLiveData<List<Price>>()
    val prices: LiveData<List<Price>> by lazy { _prices }
    private val _items = MutableLiveData<List<Item>>()
    val items: LiveData<List<Item>> by lazy { _items }

    fun getItem(marketId: Int, itemId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val prices = itemRepository.getItem(marketId, itemId)
            _prices.postValue(prices)
        }
    }

    fun getItems(marketId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val items = itemRepository.getItems(marketId)
            _items.postValue(items)
        }
    }

    class ItemViewModelFactory(private val itemRepository: ItemRepository): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ItemViewModel(itemRepository) as T
        }
    }
}