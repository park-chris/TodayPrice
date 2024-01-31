package com.crystal.todayprice.ui

import android.os.Bundle
import android.util.Log
import com.crystal.todayprice.component.BaseActivity
import com.crystal.todayprice.component.ToolbarType
import com.crystal.todayprice.component.TransitionMode
import com.crystal.todayprice.databinding.ActivityReviewBinding

private const val TAG = "TestLog"

class ReviewActivity : BaseActivity(ToolbarType.ONLY_BACK, TransitionMode.HORIZON) {

    private lateinit var binding: ActivityReviewBinding

    private var marketId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityReviewBinding.inflate(layoutInflater)
        baseBinding.contentLayout.addView(binding.root)

        marketId = intent.getIntExtra(MarketActivity.MARKET_ID, -1)




    }
}