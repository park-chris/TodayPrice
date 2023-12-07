package com.crystal.todayprice.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import com.crystal.todayprice.data.ListNecessariesPricesResponse
import com.crystal.todayprice.repository.PriceRepository
import kotlinx.coroutines.launch
import com.crystal.todayprice.test.Result

class PriceViewModel(private val priceRepository: PriceRepository): ViewModel() {

    private val _items = MutableLiveData<Result<ListNecessariesPricesResponse>>()
    val items: LiveData<Result<ListNecessariesPricesResponse>> by lazy { _items }

    fun getAllItems() {
        viewModelScope.launch {
//            try {
//                val result = priceRepository.getAllItems()
//                _items.value = result
//            } catch (e: Exception) {
//                _items.value = Result.Failure(e)
//            }
        }
    }

    class PriceViewModelFactory(private val priceRepository: PriceRepository): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return PriceViewModel(priceRepository) as T
        }
    }

}