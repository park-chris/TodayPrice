package com.crystal.todayprice.repository

import android.util.Log
import com.crystal.todayprice.api.RetrofitManager
import com.crystal.todayprice.data.ListNecessariesPricesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

const val TAG = "PriceRepositoryImpl"

class PriceRepositoryImpl : PriceRepository {

    override fun getAllItems(callback: (ListNecessariesPricesResponse?) -> Unit) {

        val itemRequest = RetrofitManager.priceService.getAllItems()

        itemRequest.enqueue(object : Callback<ListNecessariesPricesResponse> {
            override fun onResponse(
                call: Call<ListNecessariesPricesResponse>,
                response: Response<ListNecessariesPricesResponse>
            ) {
                Log.e(TAG, "ee : ${response.body()}")
                callback(response.body())
            }

            override fun onFailure(call: Call<ListNecessariesPricesResponse>, t: Throwable) {
                callback(null)
            }
        })
    }
}