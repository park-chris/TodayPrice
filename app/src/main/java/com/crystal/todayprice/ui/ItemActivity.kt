package com.crystal.todayprice.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.crystal.todayprice.component.BaseActivity
import com.crystal.todayprice.component.ToolbarType
import com.crystal.todayprice.component.TransitionMode
import com.crystal.todayprice.data.Item
import com.crystal.todayprice.data.Market
import com.crystal.todayprice.databinding.ActivityItemBinding
import com.crystal.todayprice.repository.ItemRepositoryImpl
import com.crystal.todayprice.util.CommonUtil.Companion.intentSerializable
import com.crystal.todayprice.util.TextUtil
import com.crystal.todayprice.viewmodel.ItemViewModel

class ItemActivity: BaseActivity(ToolbarType.HOME, TransitionMode.HORIZON)  {

    private lateinit var binding: ActivityItemBinding

    private val itemViewModel: ItemViewModel by viewModels {
        ItemViewModel.ItemViewModelFactory(ItemRepositoryImpl())
    }

    private var item: Item? = null
    private var market: Market? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        binding = ActivityItemBinding.inflate(layoutInflater)
        baseBinding.contentLayout.addView(binding.root)

        item = intent.intentSerializable(ITEM_NAME, Item::class.java)
        market = intent.intentSerializable(MarketActivity.MARKET_OBJECT, Market::class.java)

        itemViewModel.prices.observe(this, Observer {
            if (it.isNotEmpty()) {
                binding.price = it.last()
                binding.graphView.setData(it.takeLast(4))
            }
        })

        item?.let {
            itemViewModel.getItem(market!!.id, it.itemId)
            binding.item = it
            binding.priceTextView.text = TextUtil.priceFormat(this, it.itemPrice.toDouble())
        }
    }

    companion object {
        const val ITEM_NAME = "item_name"
    }
    
}