package com.crystal.todayprice.ui.market

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.crystal.todayprice.R
import com.crystal.todayprice.adapter.ItemAdapter
import com.crystal.todayprice.component.BaseActivity
import com.crystal.todayprice.component.ToolbarType
import com.crystal.todayprice.component.TransitionMode
import com.crystal.todayprice.data.Market
import com.crystal.todayprice.data.NecessaryPrice
import com.crystal.todayprice.databinding.ActivityMarketBinding
import com.crystal.todayprice.repository.PriceRepositoryImpl
import com.crystal.todayprice.repository.TAG
import com.crystal.todayprice.ui.item.ItemActivity
import com.crystal.todayprice.util.CommonUtil.Companion.intentSerializable
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
        moveToItem(it)
    }

    private var market : Market? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMarketBinding.inflate(layoutInflater)
        baseBinding.contentLayout.addView(binding.root)

        market = intent.intentSerializable(MARKET_NAME, Market::class.java)

        market?.let {
//            setTitle(it.name)
//            setImageView(it.imgUrl)
//            setHeader()
        }

    }
    override fun onResume() {
        super.onResume()

        setItems()
    }

    private fun moveToItem(item: NecessaryPrice) {
        val intent = Intent(this, ItemActivity::class.java)
        intent.putExtra(ItemActivity.ITEM_NAME, item)
        startActivity(intent)
    }

    private fun setItems() {

        binding.itemRecyclerView.adapter = adapter

        lifecycleScope.launch {
            priceViewModel.itemPagingDataFlow
                .collectLatest { items ->
                    Log.e(TAG, "flow ${items}")
                    adapter.submitData(items)
                }
        }

        market?.let {
            priceViewModel.handleQuery(it.name)
        }
    }

    companion object {
        const val MARKET_NAME = "market_name"
    }
}