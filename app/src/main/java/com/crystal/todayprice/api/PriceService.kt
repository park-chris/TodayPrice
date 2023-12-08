package com.crystal.todayprice.api

import com.crystal.todayprice.data.ListNecessariesPricesResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface PriceService {
    @GET("ListNecessariesPricesService/1/5/")
    suspend fun getAllItems(): ListNecessariesPricesResponse
}