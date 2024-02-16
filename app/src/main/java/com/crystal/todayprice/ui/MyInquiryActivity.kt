package com.crystal.todayprice.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.crystal.todayprice.adapter.InquiryAdapter
import com.crystal.todayprice.component.BaseActivity
import com.crystal.todayprice.component.ToolbarType
import com.crystal.todayprice.component.TransitionMode
import com.crystal.todayprice.databinding.ActivityMyInquiryBinding
import com.crystal.todayprice.repository.InquiryRepositoryImpl
import com.crystal.todayprice.viewmodel.InquiryViewModel

class MyInquiryActivity : BaseActivity(ToolbarType.ONLY_BACK, TransitionMode.HORIZON) {

    private lateinit var binding: ActivityMyInquiryBinding

    private val inquiryViewModel: InquiryViewModel by viewModels {
        InquiryViewModel.InquiryViewModelFactory(InquiryRepositoryImpl())
    }

    private lateinit var adapter: InquiryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMyInquiryBinding.inflate(layoutInflater)
        baseBinding.contentLayout.addView(binding.root)
    }


    override fun onResume() {
        super.onResume()

        baseBinding.progressBar.visibility = View.VISIBLE

        observeData()
        setRecyclerView()
    }

    private fun setRecyclerView() {
        userDataManager.user ?: return
        adapter = InquiryAdapter { inquiry ->

        }
        binding.recyclerView.adapter = adapter
        inquiryViewModel.getMyInquiries(userDataManager.user!!.id)
    }

    private fun observeData() {
        inquiryViewModel.inquiries.observe(this, Observer {
            if (it.isNotEmpty()) {
                adapter.submitList(it)
                binding.infoTextView.visibility = View.GONE
            } else {
                binding.infoTextView.visibility = View.VISIBLE
            }
            baseBinding.progressBar.visibility = View.GONE
        })
    }

}