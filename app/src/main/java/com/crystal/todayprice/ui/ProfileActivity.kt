package com.crystal.todayprice.ui

import android.content.Intent
import android.os.Bundle
import com.crystal.todayprice.component.BaseActivity
import com.crystal.todayprice.component.ToolbarType
import com.crystal.todayprice.component.TransitionMode
import com.crystal.todayprice.databinding.ActivityProfileBinding
import com.google.android.material.navigation.NavigationView

class ProfileActivity : BaseActivity(ToolbarType.ONLY_BACK, TransitionMode.HORIZON), NavigationView.OnNavigationItemSelectedListener  {

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileBinding.inflate(layoutInflater)
        baseBinding.contentLayout.addView(binding.root)
        binding.navigationView.setNavigationItemSelectedListener(this)
        binding.navigationView.elevation = 0f
    }

    override fun onResume() {
        super.onResume()
        userDataManager.user?.let {
            binding.item = userDataManager.user
        }
        setupEvent()
    }

    override fun onDestroy() {
        binding.navigationView.setNavigationItemSelectedListener(null)
        super.onDestroy()
    }

    private fun setupEvent() {
        binding.editTextView.setOnClickListener {
            startActivity(Intent(this, EditNameActivity::class.java))
        }
    }

}