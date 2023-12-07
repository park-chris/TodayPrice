package com.crystal.todayprice.ui.category

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
//import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.crystal.todayprice.api.RetrofitManager
import com.crystal.todayprice.component.BaseActivity
import com.crystal.todayprice.component.ToolbarType
import com.crystal.todayprice.data.ListNecessariesPricesResponse
import com.crystal.todayprice.databinding.ActivityCategoryBinding
import com.crystal.todayprice.repository.PriceRepositoryImpl
import com.crystal.todayprice.repository.TAG
import com.crystal.todayprice.test.Result
import com.crystal.todayprice.viewmodel.PriceViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryActivity : BaseActivity(ToolbarType.BACK) {
    private lateinit var binding: ActivityCategoryBinding


    private val priceViewModel: PriceViewModel by viewModels {
        PriceViewModel.PriceViewModelFactory(PriceRepositoryImpl())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCategoryBinding.inflate(layoutInflater)
        baseBinding.contentLayout.addView(binding.root)

    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "CategoryActivity onResume()")

//        priceViewModel.getAllItems()

        val itemRequest = RetrofitManager.priceService.getAllItems()
        itemRequest.enqueue(object : Callback<ListNecessariesPricesResponse> {
            override fun onResponse(
                call: Call<ListNecessariesPricesResponse>,
                response: Response<ListNecessariesPricesResponse>
            ) {
                Result.Success( response.body())
                Log.e(TAG, "ee : ${response.body()}")
                Log.e(TAG, "ee : ${response}")
            }

            override fun onFailure(call: Call<ListNecessariesPricesResponse>, t: Throwable) {
                Result.Failure(Exception("API request failed"))
                Log.e(TAG, "error : ${t}")
            }
        })
//        itemRequest.enqueue(object : Callback<NecessaryPriceResponse> {
//            override fun onResponse(
//                call: Call<NecessaryPriceResponse>,
//                response: Response<NecessaryPriceResponse>
//            ) {
//                Result.Success( response.body())
//                Log.e(TAG, "ee : ${response.body()}")
//                Log.e(TAG, "ee : ${response}")
//            }
//
//            override fun onFailure(call: Call<NecessaryPriceResponse>, t: Throwable) {
//                Result.Failure(Exception("API request failed"))
//                Log.e(TAG, "error : ${t}")
//            }
//        })
//        priceViewModel.items.observe(this, Observer { result ->
//            when (result) {
//                is Result.Success -> {
//                    val items = result.data
//                    Log.e(TAG, "items: $items")
//                }
//                is Result.Failure -> {
//                    Log.e(TAG, "Failure ${result.exception}")
//                }
//                else -> {
//                    Log.e(TAG, "items are not fetching")
//                }
//            }
//        })

    }

}