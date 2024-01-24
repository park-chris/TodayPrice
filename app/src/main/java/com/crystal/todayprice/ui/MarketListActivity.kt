package com.crystal.todayprice.ui

import android.os.Bundle
import androidx.activity.viewModels
import com.crystal.todayprice.component.BaseActivity
import com.crystal.todayprice.component.ToolbarType
import com.crystal.todayprice.component.TransitionMode
import com.crystal.todayprice.databinding.ActivityMarketListBinding
import com.crystal.todayprice.repository.MarketRepositoryImpl
import com.crystal.todayprice.viewmodel.MarketViewModel

class MarketListActivity: BaseActivity(ToolbarType.BACK, TransitionMode.HORIZON) {

    private lateinit var binding: ActivityMarketListBinding

    private val marketViewModel: MarketViewModel by viewModels {
        MarketViewModel.MarketViewModelFactory(MarketRepositoryImpl())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMarketListBinding.inflate(layoutInflater)
        baseBinding.contentLayout.addView(binding.root)

    }
}
