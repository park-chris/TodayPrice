package com.crystal.todayprice

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.crystal.todayprice.adapter.MarketAdapter
import com.crystal.todayprice.component.BaseActivity
import com.crystal.todayprice.component.ToolbarType
import com.crystal.todayprice.data.Market
import com.crystal.todayprice.databinding.ActivityMainBinding
import com.crystal.todayprice.repository.MarketRepositoryImpl
import com.crystal.todayprice.ui.MarketActivity
import com.crystal.todayprice.viewmodel.MarketViewModel

class MainActivity : BaseActivity(ToolbarType.MENU) {
    private lateinit var binding: ActivityMainBinding

    private val marketViewModel: MarketViewModel by viewModels {
        MarketViewModel.MarketViewModelFactory(MarketRepositoryImpl())
    }

    private lateinit var adapter: MarketAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        baseBinding.contentLayout.addView(binding.root)

        adapter = MarketAdapter { market ->
            moveToMarketActivity(market)
        }

        marketViewModel.getAllMarkets()

        marketViewModel.markets.observe(this, Observer {markets ->
            markets?.let {
                setMarketList(markets)
            }
        })
    }

    override fun onResume() {
        super.onResume()



    }

    private fun moveToMarketActivity(market: Market) {
        val intent = Intent(this, MarketActivity::class.java)
        intent.putExtra(MarketActivity.MARKET_NAME, market)
        startActivity(intent)
    }

    private fun setMarketList(markets: List<Market>) {
        binding.marketRecyclerView.adapter = adapter
        adapter.submitList(markets)
    }
}
