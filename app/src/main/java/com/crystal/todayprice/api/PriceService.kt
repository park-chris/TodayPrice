package com.crystal.todayprice.api

import com.crystal.todayprice.data.ListNecessariesPricesResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PriceService {
    @GET("ListNecessariesPricesService/1/5/")
    suspend fun getAllItems(): ListNecessariesPricesResponse

    @GET("ListNecessariesPricesService/{startIndex}/{endIndex}/{marketName}")
    suspend fun getMarketItems(
        @Path(value = "startIndex") startIndex: Int,
        @Path(value = "endIndex") endIndex: Int,
        @Path(value = "marketName") marketName: String?,
    ): ListNecessariesPricesResponse

}