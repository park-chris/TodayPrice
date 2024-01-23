package com.crystal.todayprice

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.crystal.todayprice.adapter.HomeAdapter
import com.crystal.todayprice.component.BaseActivity
import com.crystal.todayprice.component.ToolbarType
import com.crystal.todayprice.data.ListItem
import com.crystal.todayprice.data.Market
import com.crystal.todayprice.databinding.ActivityMainBinding
import com.crystal.todayprice.repository.ListItemRepositoryImpl
import com.crystal.todayprice.repository.MarketRepositoryImpl
import com.crystal.todayprice.ui.MarketActivity
import com.crystal.todayprice.viewmodel.ListItemViewModel
import com.crystal.todayprice.viewmodel.MarketViewModel

const val TAG = "TestLog"
class MainActivity : BaseActivity(ToolbarType.MENU) {
    private lateinit var binding: ActivityMainBinding

    private val marketViewModel: MarketViewModel by viewModels {
        MarketViewModel.MarketViewModelFactory(MarketRepositoryImpl())
    }

    private val listItemViewModel: ListItemViewModel by viewModels {
        ListItemViewModel.ListItemViewModelFactory(ListItemRepositoryImpl())
    }

    private lateinit var adapter: HomeAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        baseBinding.contentLayout.addView(binding.root)

        adapter = HomeAdapter()

        listItemViewModel.listItem.observe(this, Observer { listItem ->
            listItem?.let {
                setMarketList(it)
                Log.e(TAG, "list: $it")
            }
        })
//        marketViewModel.markets.observe(this, Observer {markets ->
//            markets?.let {
//                setMarketList(markets)
//            }
//        })

    }

    override fun onResume() {
        super.onResume()

//        marketViewModel.getAllMarkets()

        listItemViewModel.getHomeListItem()

    }

    private fun moveToMarketActivity(market: Market) {
        val intent = Intent(this, MarketActivity::class.java)
        intent.putExtra(MarketActivity.MARKET_NAME, market)
        startActivity(intent)
    }

    private fun setMarketList(listItem: List<ListItem>) {
        binding.recyclerView.adapter = adapter

        adapter.submitList(listItem)
    }
}
