package com.crystal.todayprice.repository

import com.crystal.todayprice.data.ListNecessariesPricesResponse
import com.crystal.todayprice.test.Result

interface PriceRepository {
//    fun getAllItems():  Flow<NecessaryPriceResponse>
    fun getAllItems(): Result<ListNecessariesPricesResponse>
}