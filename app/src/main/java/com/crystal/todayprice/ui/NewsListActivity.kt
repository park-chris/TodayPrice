package com.crystal.todayprice.ui

import android.os.Bundle
import com.crystal.todayprice.component.BaseActivity
import com.crystal.todayprice.component.ToolbarType
import com.crystal.todayprice.component.TransitionMode
import com.crystal.todayprice.databinding.ActivityNewsListBinding

class NewsListActivity  : BaseActivity(ToolbarType.HOME, TransitionMode.HORIZON) {

    private lateinit var binding: ActivityNewsListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNewsListBinding.inflate(layoutInflater)
        baseBinding.contentLayout.addView(binding.root)

    }
}