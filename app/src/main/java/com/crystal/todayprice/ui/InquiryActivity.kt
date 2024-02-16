package com.crystal.todayprice.ui

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.crystal.todayprice.R
import com.crystal.todayprice.component.BaseActivity
import com.crystal.todayprice.component.ToolbarType
import com.crystal.todayprice.component.TransitionMode
import com.crystal.todayprice.data.Item
import com.crystal.todayprice.data.Market
import com.crystal.todayprice.data.Price
import com.crystal.todayprice.databinding.ActivityInquiryBinding
import com.crystal.todayprice.databinding.ActivityItemBinding
import com.crystal.todayprice.repository.ItemRepositoryImpl
import com.crystal.todayprice.util.CommonUtil.Companion.intentSerializable
import com.crystal.todayprice.util.TextUtil
import com.crystal.todayprice.viewmodel.ItemViewModel

class InquiryActivity: BaseActivity(ToolbarType.ONLY_BACK, TransitionMode.HORIZON)  {

    private lateinit var binding: ActivityInquiryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        binding = ActivityInquiryBinding.inflate(layoutInflater)
        baseBinding.contentLayout.addView(binding.root)

        setSpinner()
    }

    override fun onResume() {
        super.onResume()

    }

    private fun setSpinner() {
        val items = resources.getStringArray(R.array.inquiry_spinner_type)
        val adapter = ArrayAdapter(this, R.layout.item_spinner, items)

        adapter.setDropDownViewResource(R.layout.item_spinner)

        binding.typeSpinner.adapter = adapter

//        binding.typeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
//                val selectedItem = parent.getItemAtPosition(position).toString()
//                // 선택된 항목에 대한 작업 수행
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>) {
//                // 아무것도 선택되지 않았을 때의 동작
//            }
//
//        }

    }

}