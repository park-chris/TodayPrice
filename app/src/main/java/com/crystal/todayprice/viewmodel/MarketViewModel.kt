package com.crystal.todayprice.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.crystal.todayprice.data.Market
import com.crystal.todayprice.repository.MarketRepository
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

    fun updateMarkets(markets: List<Market>) {
        _markets.postValue(markets)
    }

    fun countMarketReview(marketId: Int, count: Int) {
        marketRepository.countMarketReview(marketId, count)
    }

    class MarketViewModelFactory(private val marketRepository: MarketRepository): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MarketViewModel(marketRepository) as T
        }
    }

}