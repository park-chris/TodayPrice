package com.crystal.todayprice.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.crystal.todayprice.R
import com.crystal.todayprice.component.BaseActivity
import com.crystal.todayprice.component.ToolbarType
import com.crystal.todayprice.databinding.ActivityCategoryBinding

class CategoryActivity : BaseActivity(ToolbarType.BACK) {
    private lateinit var binding: ActivityCategoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        baseBinding.contentLayout.addView(binding.root)

    }
}