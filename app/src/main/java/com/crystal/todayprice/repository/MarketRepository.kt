package com.crystal.todayprice.repository

import com.crystal.todayprice.data.Market

interface MarketRepository {
    suspend fun getAllMarkets(): List<Market>
    fun countMarketReview(marketId: Int, count: Int)

}