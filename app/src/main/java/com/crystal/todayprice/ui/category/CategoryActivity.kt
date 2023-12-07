package com.crystal.todayprice.ui.category

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.crystal.todayprice.component.BaseActivity
import com.crystal.todayprice.component.ToolbarType
import com.crystal.todayprice.data.ListNecessariesPricesResponse
import com.crystal.todayprice.databinding.ActivityCategoryBinding
import com.crystal.todayprice.repository.PriceRepositoryImpl
import com.crystal.todayprice.repository.TAG
import com.crystal.todayprice.callback.ResultCallback
import com.crystal.todayprice.viewmodel.PriceViewModel

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

        priceViewModel.getAllItems(object : ResultCallback<ListNecessariesPricesResponse> {
            override fun onSuccess(result: ListNecessariesPricesResponse) {
                Log.e(TAG, "result: $result")
            }
            override fun onFailure(error: Throwable) {
                Log.e(TAG, "error: ${error.message}")
            }
        })
    }

}