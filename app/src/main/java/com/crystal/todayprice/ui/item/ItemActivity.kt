package com.crystal.todayprice.ui.item

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.crystal.todayprice.component.BaseActivity
import com.crystal.todayprice.component.ToolbarType
import com.crystal.todayprice.component.TransitionMode
import com.crystal.todayprice.data.Item
import com.crystal.todayprice.data.NecessaryPrice
import com.crystal.todayprice.data.Price
import com.crystal.todayprice.databinding.ActivityItemBinding
import com.crystal.todayprice.repository.ItemRepositoryImpl
import com.crystal.todayprice.repository.PriceRepositoryImpl
import com.crystal.todayprice.repository.TAG
import com.crystal.todayprice.util.CommonUtil.Companion.intentSerializable
import com.crystal.todayprice.viewmodel.ItemViewModel
import com.crystal.todayprice.viewmodel.PriceViewModel

class ItemActivity: BaseActivity(ToolbarType.HOME, TransitionMode.HORIZON)  {
    
    private lateinit var binding: ActivityItemBinding

    private val itemViewModel: ItemViewModel by viewModels {
        ItemViewModel.ItemViewModelFactory(ItemRepositoryImpl())
    }

    private var necessaryPrice: NecessaryPrice? = null
    private var prices: List<Price> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        binding = ActivityItemBinding.inflate(layoutInflater)
        baseBinding.contentLayout.addView(binding.root)
        
        necessaryPrice = intent.intentSerializable(ITEM_NAME, NecessaryPrice::class.java)
        
        itemViewModel.prices.observe(this, Observer {
            prices = it
        })
        
        
        necessaryPrice?.let {
            itemViewModel.getItem(it.marketId.toInt(), it.itemId.toInt())
            binding.item = it
        }
    }

    companion object {
        const val ITEM_NAME = "item_name"
    }
    
}