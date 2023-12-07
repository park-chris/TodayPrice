package com.crystal.todayprice.repository

import com.crystal.todayprice.api.RetrofitManager
import com.crystal.todayprice.data.ListNecessariesPricesResponse
import com.crystal.todayprice.test.Result

const val TAG = "PriceRepositoryImpl"

class PriceRepositoryImpl: PriceRepository {

    override fun getAllItems(): Result<ListNecessariesPricesResponse> {
        TODO("Not yet implemented")
    }
}