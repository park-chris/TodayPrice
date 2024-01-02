package com.crystal.todayprice.ui.item

import android.content.ClipData.Item
import android.os.Bundle
import android.widget.Toast
import com.crystal.todayprice.component.BaseActivity
import com.crystal.todayprice.component.ToolbarType
import com.crystal.todayprice.component.TransitionMode
import com.crystal.todayprice.data.Market
import com.crystal.todayprice.data.NecessaryPrice
import com.crystal.todayprice.databinding.ActivityItemBinding
import com.crystal.todayprice.util.CommonUtil.Companion.intentSerializable

class ItemActivity: BaseActivity(ToolbarType.HOME, TransitionMode.HORIZON)  {
    
    private lateinit var binding: ActivityItemBinding
    
    private var item: NecessaryPrice? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        binding = ActivityItemBinding.inflate(layoutInflater)
        baseBinding.contentLayout.addView(binding.root)
        
        item = intent.intentSerializable(ITEM_NAME, NecessaryPrice::class.java)
        
        item?.let {
            Toast.makeText(this, "item: $item", Toast.LENGTH_SHORT).show()
            binding.item = it
        }
    }

    companion object {
        const val ITEM_NAME = "item_name"
    }
    
}