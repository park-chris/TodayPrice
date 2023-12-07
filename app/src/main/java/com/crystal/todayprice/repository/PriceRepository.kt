package com.crystal.todayprice.repository

import com.crystal.todayprice.data.ListNecessariesPricesResponse

interface PriceRepository {
    fun getAllItems(callback: (ListNecessariesPricesResponse?) -> Unit)
}