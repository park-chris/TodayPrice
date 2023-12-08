package com.crystal.todayprice.repository

import com.crystal.todayprice.data.ListNecessariesPricesResponse
import retrofit2.Response

interface PriceRepository {
    suspend fun getAllItems(): ListNecessariesPricesResponse
}