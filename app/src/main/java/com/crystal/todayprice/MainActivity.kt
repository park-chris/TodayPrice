package com.crystal.todayprice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import com.crystal.todayprice.component.BaseActivity
import com.crystal.todayprice.component.ToolbarType
import com.crystal.todayprice.databinding.ActivityMainBinding

class MainActivity : BaseActivity(ToolbarType.MENU) {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = LayoutInflater.from(this)
        inflater.inflate(R.layout.activity_main, contentFrameLayout, true)
        binding = ActivityMainBinding.inflate(layoutInflater)
    }
}