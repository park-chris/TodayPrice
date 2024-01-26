package com.crystal.todayprice.ui

import android.os.Bundle
import com.crystal.todayprice.MainActivity
import com.crystal.todayprice.component.BaseActivity
import com.crystal.todayprice.component.ToolbarType
import com.crystal.todayprice.component.TransitionMode
import com.crystal.todayprice.data.Notice
import com.crystal.todayprice.databinding.ActivityNoticeBinding
import com.crystal.todayprice.util.CommonUtil.Companion.intentSerializable

class NoticeActivity  : BaseActivity(ToolbarType.ONLY_BACK, TransitionMode.HORIZON) {

    private lateinit var binding: ActivityNoticeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNoticeBinding.inflate(layoutInflater)
        baseBinding.contentLayout.addView(binding.root)

        val notice = intent.intentSerializable(MainActivity.NOTICE_OBJECT, Notice::class.java)

        notice?.let {
            binding.item = it
        }

    }
}