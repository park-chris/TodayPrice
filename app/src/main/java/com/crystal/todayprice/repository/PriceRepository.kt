package com.crystal.todayprice.repository

import androidx.paging.PagingData
import com.crystal.todayprice.data.ListNecessariesPricesResponse
import com.crystal.todayprice.data.NecessaryPrice
import kotlinx.coroutines.flow.Flow

interface PriceRepository {
    suspend fun getAllItems(): ListNecessariesPricesResponse
    fun getMarketItems(marketName: String?): Flow<PagingData<NecessaryPrice>>
}