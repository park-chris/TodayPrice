package com.crystal.todayprice.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.crystal.todayprice.R
import com.crystal.todayprice.adapter.InquiryAdapter
import com.crystal.todayprice.adapter.MarketAdapter
import com.crystal.todayprice.component.BaseActivity
import com.crystal.todayprice.component.CustomDialog
import com.crystal.todayprice.component.ToolbarType
import com.crystal.todayprice.component.TransitionMode
import com.crystal.todayprice.data.Market
import com.crystal.todayprice.databinding.ActivityMyFavoriteBinding
import com.crystal.todayprice.databinding.ActivityMyInquiryBinding
import com.crystal.todayprice.repository.InquiryRepositoryImpl
import com.crystal.todayprice.util.FirebaseCallback
import com.crystal.todayprice.util.GridSpacingItemDecoration
import com.crystal.todayprice.util.OnDialogListener
import com.crystal.todayprice.util.Result
import com.crystal.todayprice.viewmodel.InquiryViewModel
import com.crystal.todayprice.viewmodel.UserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyFavoriteActivity : BaseActivity(ToolbarType.ONLY_BACK, TransitionMode.HORIZON) {

    private lateinit var binding: ActivityMyFavoriteBinding
    private lateinit var adapter: MarketAdapter
    private lateinit var markets: List<Market>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMyFavoriteBinding.inflate(layoutInflater)
        baseBinding.contentLayout.addView(binding.root)
    }


    override fun onResume() {
        super.onResume()

        baseBinding.progressBar.visibility = View.VISIBLE


        setRecyclerView()
        setData()
    }
    private fun setData() {
        userDataManager.user?.let {
            CoroutineScope(Dispatchers.Main).launch {
                markets = userViewModel.getFavoriteMarkets(userDataManager.user!!.id)
                submitList(markets)
                baseBinding.progressBar.visibility = View.GONE
            }
        }
    }

    private fun submitList(list: List<Market>) {
        adapter.submitList(list)
        if (markets.isEmpty()) {
            binding.infoTextView.visibility = View.VISIBLE
        } else {
            binding.infoTextView.visibility = View.GONE
        }
    }

    private fun setRecyclerView() {

        adapter = MarketAdapter { market ->
            val intent = Intent(this, MarketActivity::class.java)
            intent.putExtra(MarketActivity.MARKET_OBJECT, market)
            startActivity(intent)
        }

        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.horizontal_padding)
        val spanCount = 2
        val includeEdge = true

        binding.recyclerView.layoutManager = GridLayoutManager(this, spanCount)
        val itemDecoration = GridSpacingItemDecoration(spanCount, spacingInPixels, includeEdge)
        binding.recyclerView.addItemDecoration(itemDecoration)
        binding.recyclerView.adapter = adapter

    }

}