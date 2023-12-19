package com.crystal.todayprice.repository

import androidx.lifecycle.MutableLiveData
import com.crystal.todayprice.data.Market

interface MarketRepository {
    suspend fun getAllMarkets(): List<Market>
}