package com.crystal.todayprice

import android.content.Intent
import android.os.Bundle
import com.crystal.todayprice.component.BaseActivity
import com.crystal.todayprice.component.ToolbarType
import com.crystal.todayprice.databinding.ActivityMainBinding
import com.crystal.todayprice.ui.market.MarketActivity

class MainActivity : BaseActivity(ToolbarType.MENU) {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        baseBinding.contentLayout.addView(binding.root)

        binding.itemButton.setOnClickListener {
            startActivity(Intent(this, MarketActivity::class.java))
        }
    }
}