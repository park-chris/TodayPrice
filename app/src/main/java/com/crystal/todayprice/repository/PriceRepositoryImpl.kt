package com.crystal.todayprice.repository

import android.util.Log
import com.crystal.todayprice.api.RetrofitManager
import com.crystal.todayprice.data.ListNecessariesPricesResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

const val TAG = "PriceRepositoryImpl"

class PriceRepositoryImpl : PriceRepository {
    override suspend fun getAllItems(): ListNecessariesPricesResponse =
        withContext(Dispatchers.IO) {
            RetrofitManager.priceService.getAllItems()
        }

}