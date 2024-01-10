package com.crystal.todayprice.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.crystal.todayprice.data.Item
import com.crystal.todayprice.data.Price
import com.crystal.todayprice.repository.ItemRepository
import com.crystal.todayprice.repository.TAG
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ItemViewModel(private val itemRepository: ItemRepository): ViewModel() {

    private val _prices = MutableLiveData<List<Price>>()
    val prices: LiveData<List<Price>> by lazy { _prices }

    fun getItem(marketId: Int, itemId: Int) {
        Log.e(TAG, "getItem start")
        CoroutineScope(Dispatchers.IO).launch {
            val prices = itemRepository.getItem(marketId, itemId)
            _prices.postValue(prices)
        }
    }

    class ItemViewModelFactory(private val itemRepository: ItemRepository): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ItemViewModel(itemRepository) as T
        }
    }
}