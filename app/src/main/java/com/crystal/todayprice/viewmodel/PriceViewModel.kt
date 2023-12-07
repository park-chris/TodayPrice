package com.crystal.todayprice.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.crystal.todayprice.data.ListNecessariesPricesResponse
import com.crystal.todayprice.repository.PriceRepository

class PriceViewModel(private val priceRepository: PriceRepository) : ViewModel() {

    private val _items = MutableLiveData<ListNecessariesPricesResponse>()
    val items: LiveData<ListNecessariesPricesResponse> by lazy { _items }

    fun getAllItems() {
        priceRepository.getAllItems {result ->
            if (result != null) {
                _items.postValue(result)
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