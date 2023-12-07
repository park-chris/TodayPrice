package com.crystal.todayprice.repository

import com.crystal.todayprice.data.ListNecessariesPricesResponse
import com.crystal.todayprice.callback.ResultCallback

interface PriceRepository {
    fun getAllItems(callback: ResultCallback<ListNecessariesPricesResponse>)
}