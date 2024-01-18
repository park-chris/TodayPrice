package com.crystal.todayprice.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnScrollChangeListener
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.crystal.todayprice.R
import com.crystal.todayprice.adapter.ItemAdapter
import com.crystal.todayprice.component.BaseActivity
import com.crystal.todayprice.component.ToolbarType
import com.crystal.todayprice.component.TransitionMode
import com.crystal.todayprice.data.Item
import com.crystal.todayprice.data.Market
import com.crystal.todayprice.databinding.ActivityMarketBinding
import com.crystal.todayprice.repository.ItemRepositoryImpl
import com.crystal.todayprice.util.CommonUtil.Companion.intentSerializable
import com.crystal.todayprice.util.TextUtil
import com.crystal.todayprice.viewmodel.ItemViewModel
import com.google.android.material.chip.Chip
import kotlinx.coroutines.launch

private const val TAG = "TestLog"
class MarketActivity : BaseActivity(ToolbarType.BACK, TransitionMode.HORIZON) {

    private lateinit var binding: ActivityMarketBinding

    private var market: Market? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMarketBinding.inflate(layoutInflater)
        baseBinding.contentLayout.addView(binding.root)

        market = intent.intentSerializable(MARKET_NAME, Market::class.java)

        market?.let {
            Glide.with(binding.root)
                .load(it.imgUrl)
                .centerCrop()
                .error(R.drawable.no_picture)
                .into(binding.marketImageView)

            binding.market = it
            if (it.description.isEmpty()) {
                binding.descriptionTextView.text = resources.getString(R.string.market_empty_description)
            }
        }

    }

    override fun onResume() {
        super.onResume()

        setScrollEvent()
        setupEvent()
    }

    private fun setupEvent() {
        binding.pricesButton.setOnClickListener {
            val intent = Intent(this, ItemListActivity::class.java)
            intent.putExtra(MARKET_NAME, market)
            startActivity(intent)
        }
    }

    private fun setScrollEvent() {
        binding.scrollView.apply {
            viewTreeObserver.addOnScrollChangedListener {
                if (scrollY == 0
                    && binding.motionLayout.currentState == R.id.end
                    && (binding.motionLayout.progress >= 1f || binding.motionLayout.progress <= 0f)
                ) {
                    binding.motionLayout.transitionToStart()
                }

                // 아래로 스크롤할 때의 처리
                 if (scrollY > 0 && binding.motionLayout.currentState == R.id.start
                     && (binding.motionLayout.progress >= 1f || binding.motionLayout.progress <= 0f)
                 ) {
                     binding.motionLayout.transitionToEnd()
                 }

                val maxScroll = getChildAt(0).height - height

                if (scrollY == maxScroll
                    && binding.motionLayout.currentState == R.id.end
                    && (binding.motionLayout.progress >= 1f || binding.motionLayout.progress <= 0f)
                ) {
                    binding.motionLayout.transitionToStart()
                }

            }

        }
    }


    companion object {
        const val MARKET_NAME = "market_name"
    }
}