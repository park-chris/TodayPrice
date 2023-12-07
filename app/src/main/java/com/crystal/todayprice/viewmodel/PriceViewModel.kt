package com.crystal.todayprice.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.crystal.todayprice.data.ListNecessariesPricesResponse
import com.crystal.todayprice.repository.PriceRepository
import com.crystal.todayprice.callback.ResultCallback

class PriceViewModel(private val priceRepository: PriceRepository) : ViewModel() {

    fun getAllItems(callback: ResultCallback<ListNecessariesPricesResponse>) {
        priceRepository.getAllItems(callback)
    }

    class PriceViewModelFactory(private val priceRepository: PriceRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return PriceViewModel(priceRepository) as T
        }
    }

}