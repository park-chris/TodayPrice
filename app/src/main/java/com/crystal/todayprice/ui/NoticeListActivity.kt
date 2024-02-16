package com.crystal.todayprice.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.crystal.todayprice.MainActivity
import com.crystal.todayprice.adapter.NoticeAdapter
import com.crystal.todayprice.component.BaseActivity
import com.crystal.todayprice.component.ToolbarType
import com.crystal.todayprice.component.TransitionMode
import com.crystal.todayprice.data.Notice
import com.crystal.todayprice.databinding.ActivityNoticeBinding
import com.crystal.todayprice.databinding.ActivityNoticeListBinding
import com.crystal.todayprice.repository.ListItemRepositoryImpl
import com.crystal.todayprice.repository.NoticeRepositoryImpl
import com.crystal.todayprice.util.CommonUtil.Companion.intentSerializable
import com.crystal.todayprice.viewmodel.ListItemViewModel
import com.crystal.todayprice.viewmodel.NoticeViewModel

class NoticeListActivity : BaseActivity(ToolbarType.ONLY_BACK, TransitionMode.HORIZON) {

    private lateinit var binding: ActivityNoticeListBinding


    private val noticeViewModel: NoticeViewModel by viewModels {
        NoticeViewModel.NoticeViewModelFactory(NoticeRepositoryImpl())
    }

    private lateinit var adapter: NoticeAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNoticeListBinding.inflate(layoutInflater)
        baseBinding.contentLayout.addView(binding.root)

        observeData()
        setRecyclerView()
    }


    private fun observeData() {
        noticeViewModel.notices.observe(this, Observer {
            it?.let { adapter.submitList(it) }
        })
    }

    private fun setRecyclerView() {
        adapter = NoticeAdapter { notice ->
            moveToActivity(NoticeActivity::class.java, notice)
        }
        binding.itemRecyclerView.adapter = adapter
        noticeViewModel.getNotice()
    }

}