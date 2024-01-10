package com.crystal.todayprice.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.crystal.todayprice.data.Item
import com.crystal.todayprice.repository.ItemRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ItemViewModel(private val itemRepository: ItemRepository): ViewModel() {

    private val _item = MutableLiveData<Item>()
    val item: LiveData<Item> by lazy { _item }

    fun getItem(marketId: Int, itemId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val item = itemRepository.getItem(marketId, itemId)
            item?.let {
                _item.postValue(it)
            }
        }
    }

    class ItemViewModelFactory(private val itemRepository: ItemRepository): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ItemViewModel(itemRepository) as T
        }
    }
}