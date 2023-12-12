package com.crystal.todayprice.ui.market

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import com.crystal.todayprice.adapter.ItemAdapter
import com.crystal.todayprice.component.BaseActivity
import com.crystal.todayprice.component.ToolbarType
import com.crystal.todayprice.data.NecessaryPrice
import com.crystal.todayprice.databinding.ActivityMarketBinding
import com.crystal.todayprice.repository.PriceRepositoryImpl
import com.crystal.todayprice.repository.TAG
import com.crystal.todayprice.viewmodel.PriceViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MarketActivity : BaseActivity(ToolbarType.BACK) {
    private lateinit var binding: ActivityMarketBinding

    private val priceViewModel: PriceViewModel by viewModels {
        PriceViewModel.PriceViewModelFactory(PriceRepositoryImpl())
    }

    private val adapter: ItemAdapter = ItemAdapter {
        Toast.makeText(this, "${it.itemName} is clicked!!", Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMarketBinding.inflate(layoutInflater)
        baseBinding.contentLayout.addView(binding.root)

    }

    override fun onResume() {
        super.onResume()

        binding.itemRecyclerView.adapter = adapter

        lifecycleScope.launch {
            priceViewModel.itemPagingDataFlow
                .collectLatest { items ->
                    adapter.submitData(items)
                    Log.e(TAG, "itemPagingDataFlow launch")
                }
        }

        priceViewModel.handleQuery("인현시장")


    }
}