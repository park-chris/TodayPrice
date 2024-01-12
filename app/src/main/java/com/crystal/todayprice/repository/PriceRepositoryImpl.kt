package com.crystal.todayprice.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.crystal.todayprice.api.RetrofitManager
import com.crystal.todayprice.data.ItemDataSource
import com.crystal.todayprice.data.ListNecessariesPricesResponse
import com.crystal.todayprice.data.NecessaryPrice
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

const val TAG = "TestLog"

class PriceRepositoryImpl : PriceRepository {
    override suspend fun getAllItems(): ListNecessariesPricesResponse =
        withContext(Dispatchers.IO) {
            RetrofitManager.priceService.getAllItems()
        }

    override fun getMarketItems(marketName: String?): Flow<PagingData<NecessaryPrice>> {
        return Pager(
            config = PagingConfig(
                pageSize = ItemDataSource.defaultDisplay,
                initialLoadSize = ItemDataSource.defaultDisplay * 3,
                prefetchDistance = 5,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                ItemDataSource(marketName, RetrofitManager.priceService)
            }
        ).flow
    }

}