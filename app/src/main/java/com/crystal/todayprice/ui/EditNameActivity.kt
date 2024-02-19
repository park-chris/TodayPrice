package com.crystal.todayprice.ui


import android.os.Bundle
import com.crystal.todayprice.component.BaseActivity
import com.crystal.todayprice.component.ToolbarType
import com.crystal.todayprice.component.TransitionMode
import com.crystal.todayprice.databinding.ActivityEditNameBinding

class EditNameActivity : BaseActivity(ToolbarType.ONLY_BACK, TransitionMode.HORIZON) {

    private lateinit var binding: ActivityEditNameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditNameBinding.inflate(layoutInflater)
        baseBinding.contentLayout.addView(binding.root)

    }

    override fun onResume() {
        super.onResume()
    }

}