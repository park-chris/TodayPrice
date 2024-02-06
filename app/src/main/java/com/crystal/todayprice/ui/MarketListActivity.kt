package com.crystal.todayprice.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.crystal.todayprice.R
import com.crystal.todayprice.adapter.MarketAdapter
import com.crystal.todayprice.component.BaseActivity
import com.crystal.todayprice.component.ToolbarType
import com.crystal.todayprice.component.TransitionMode
import com.crystal.todayprice.data.Market
import com.crystal.todayprice.databinding.ActivityMarketListBinding
import com.crystal.todayprice.repository.MarketRepositoryImpl
import com.crystal.todayprice.util.GridSpacingItemDecoration
import com.crystal.todayprice.viewmodel.MarketViewModel
import com.google.android.material.chip.Chip
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MarketListActivity: BaseActivity(ToolbarType.ONLY_BACK, TransitionMode.HORIZON) {

    private lateinit var binding: ActivityMarketListBinding

    private val marketViewModel: MarketViewModel by viewModels {
        MarketViewModel.MarketViewModelFactory(MarketRepositoryImpl())
    }

    private val adapter: MarketAdapter = MarketAdapter {
        moveToMarket(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMarketListBinding.inflate(layoutInflater)
        baseBinding.contentLayout.addView(binding.root)
        baseBinding.progressBar.visibility = View.VISIBLE

        setMarkets()
        observeList()
        marketViewModel.getMarkets()
        addChip()
    }

    private fun observeList() {
        lifecycleScope.launch {
            marketViewModel.markets.observe(this@MarketListActivity, Observer {
                it?.let {
                    adapter.submitList(it)
                    baseBinding.progressBar.visibility = View.GONE
                }
            })
        }
    }

    private fun setMarkets() {
        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.horizontal_padding)
        val spanCount = 2
        val includeEdge = true

        binding.itemRecyclerView.layoutManager = GridLayoutManager(this, spanCount)
        val itemDecoration = GridSpacingItemDecoration(spanCount, spacingInPixels, includeEdge)
        binding.itemRecyclerView.addItemDecoration(itemDecoration)
        binding.itemRecyclerView.adapter = adapter
    }
    private fun moveToMarket(market: Market) {
        val intent = Intent(this, MarketActivity::class.java)
        intent.putExtra(MarketActivity.MARKET_OBJECT, market)
        startActivity(intent)
    }

    private fun addChip() {

        val boroughArray = resources.getStringArray(R.array.market_borough_array)

        for (borough in boroughArray) {
            binding.chipGroup.addView(Chip(this).apply {
                text = borough
                textSize = 15F
                chipStrokeColor = ContextCompat.getColorStateList(this@MarketListActivity, R.color.stroke_chip_state)
                chipStrokeWidth = 2F
                isCheckable = true
                isCheckedIconVisible = false
                isChecked = borough == resources.getString(R.string.all)
                chipBackgroundColor =
                    ContextCompat.getColorStateList(this@MarketListActivity, R.color.bg_chip_state)
                setTextColor(ContextCompat.getColorStateList(this@MarketListActivity, R.color.text_chip_state))

                this.setOnClickListener {
                    if (!this.isChecked) {
                        this.isChecked = true
                        return@setOnClickListener
                    }
                    if (text.toString() == resources.getString(R.string.all)) {
                        submitList(marketViewModel.markets.value ?: emptyList())
                    }else {
                        submitList(marketViewModel.getFilterItems(borough))
                    }
                }
            })
        }
    }

    private fun submitList(list: List<Market>) {
        adapter.submitList(list)
        binding.infoTextView.isVisible = list.isEmpty()

        lifecycleScope.launch {
            delay(300)
            binding.itemRecyclerView.scrollToPosition(0)
        }
    }
}
