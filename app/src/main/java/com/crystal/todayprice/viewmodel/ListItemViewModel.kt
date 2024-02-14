package com.crystal.todayprice.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.crystal.todayprice.data.ListItem
import com.crystal.todayprice.repository.ListItemRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListItemViewModel(private val listItemRepository: ListItemRepository): ViewModel() {

    private val _listItem = MutableLiveData<List<ListItem>>()
    val listItem: LiveData<List<ListItem>> by lazy { _listItem }

    fun getHomeListItem() {
        CoroutineScope(Dispatchers.IO).launch {
            val markets = listItemRepository.getHomeList()
            _listItem.postValue(markets)
        }
    }

    fun getSearchListItem(query: String?) {
        CoroutineScope(Dispatchers.IO).launch {
            val markets = listItemRepository.searchQuery(query)
            _listItem.postValue(markets)
        }
    }

    class ListItemViewModelFactory(private val listItemRepository: ListItemRepository): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ListItemViewModel(listItemRepository) as T
        }
    }

}