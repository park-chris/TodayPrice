package com.crystal.todayprice.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.crystal.todayprice.data.Item
import com.crystal.todayprice.data.Market
import com.crystal.todayprice.repository.MarketRepository
import com.crystal.todayprice.util.TextUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MarketViewModel(private val marketRepository: MarketRepository): ViewModel() {

    private val _markets = MutableLiveData<List<Market>>()
    val markets: LiveData<List<Market>> by lazy { _markets }

    fun getMarkets() {
        CoroutineScope(Dispatchers.IO).launch {
            val markets = marketRepository.getAllMarkets()
            _markets.postValue(markets)
        }
    }

    fun getFilterItems(borough: String): List<Market> {
        val list = markets.value ?: return emptyList()
        return list.filter { item: Market -> item.borough == borough }
    }

    class MarketViewModelFactory(private val marketRepository: MarketRepository): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MarketViewModel(marketRepository) as T
        }
    }

}