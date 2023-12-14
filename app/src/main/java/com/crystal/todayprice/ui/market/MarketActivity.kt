package com.crystal.todayprice.ui.market

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.crystal.todayprice.adapter.ItemAdapter
import com.crystal.todayprice.component.BaseActivity
import com.crystal.todayprice.component.ToolbarType
import com.crystal.todayprice.component.TransitionMode
import com.crystal.todayprice.data.NecessaryPrice
import com.crystal.todayprice.databinding.ActivityMarketBinding
import com.crystal.todayprice.repository.PriceRepositoryImpl
import com.crystal.todayprice.repository.TAG
import com.crystal.todayprice.viewmodel.PriceViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MarketActivity : BaseActivity(ToolbarType.BACK, TransitionMode.HORIZON) {

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


        lifecycleScope.launch {
            priceViewModel.itemPagingDataFlow
                .collectLatest { items ->
                    Log.e(TAG, "flow ${items}")
                    adapter.submitData(items)
                }
        }

        binding.itemRecyclerView.adapter = adapter

        priceViewModel.handleQuery("성대전통시장")

        testLog()
    }

    private fun testLog() {
        adapter.addLoadStateListener { loadState ->
            when (loadState.refresh) {
                is LoadState.Loading -> {
                    // Refresh is loading
                    Log.i(TAG, "LoadState: Loading")
                }
                is LoadState.Error -> {
                    // Refresh has encountered an error
                    Log.i(TAG, "LoadState: Error : ${loadState}")
                }
                is LoadState.NotLoading -> {
                    // Refresh has completed (either successfully or with no data)
                    Log.i(TAG, "LoadState: NotLoading : ${loadState}")
                }
            }

            when (loadState.append) {
                is LoadState.Loading -> {
                    // Appending more data is in progress
                    // You can show a loading indicator for appending here
                    Log.i(TAG, "LoadState addpend: Loading")
                }
                is LoadState.Error -> {
                    // Appending more data encountered an error
                    // You can handle error state for appending here
                    Log.i(TAG, "LoadState addpend: Error $loadState")

                }
                is LoadState.NotLoading -> {
                    // Appending more data has completed (either successfully or with no more data)
                    // You can hide the loading indicator for appending here
                    Log.i(TAG, "LoadState addpend: NotLoading")
                }
            }
        }
    }
}