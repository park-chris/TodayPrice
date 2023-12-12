package com.crystal.todayprice.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.crystal.todayprice.data.ListNecessariesPricesResponse
import com.crystal.todayprice.data.NecessaryPrice
import com.crystal.todayprice.repository.PriceRepository
import com.crystal.todayprice.repository.TAG
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class PriceViewModel(private val priceRepository: PriceRepository) : ViewModel() {

    private val _items = MutableLiveData<ListNecessariesPricesResponse>()
    val items: LiveData<ListNecessariesPricesResponse> by lazy { _items }

    private val queryFlow = MutableSharedFlow<String>()

    val itemPagingDataFlow = queryFlow
        .flatMapLatest {
            getMarketItems(it)
        }
        .cachedIn(viewModelScope)
    private fun getMarketItems(query: String?): Flow<PagingData<NecessaryPrice>> = priceRepository.getMarketItems(query)
    fun handleQuery(query: String) {
        viewModelScope.launch {
            queryFlow.emit(query)
        }
    }
    fun getAllItems() {

        viewModelScope.launch {
            println(Thread.currentThread().name)
            try {
                println(Thread.currentThread().name)
                val result = priceRepository.getAllItems()
                _items.value = result
            } catch (e: Exception) {
                Log.e(TAG, "error: $e")
            }
        }
    }
    class PriceViewModelFactory(private val priceRepository: PriceRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return PriceViewModel(priceRepository) as T
        }
    }

}