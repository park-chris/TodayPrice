package com.crystal.todayprice.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.crystal.todayprice.data.ListNecessariesPricesResponse
import com.crystal.todayprice.repository.PriceRepository
import com.crystal.todayprice.repository.TAG
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class PriceViewModel(private val priceRepository: PriceRepository) : ViewModel() {

    private val _items = MutableLiveData<ListNecessariesPricesResponse>()
    val items: LiveData<ListNecessariesPricesResponse> by lazy { _items }

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