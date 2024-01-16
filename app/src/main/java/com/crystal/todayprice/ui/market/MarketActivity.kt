package com.crystal.todayprice.ui.market

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.crystal.todayprice.R
import com.crystal.todayprice.adapter.ItemAdapter
import com.crystal.todayprice.component.BaseActivity
import com.crystal.todayprice.component.ToolbarType
import com.crystal.todayprice.component.TransitionMode
import com.crystal.todayprice.data.Market
import com.crystal.todayprice.data.NecessaryPrice
import com.crystal.todayprice.databinding.ActivityMarketBinding
import com.crystal.todayprice.repository.PriceRepositoryImpl
import com.crystal.todayprice.repository.TAG
import com.crystal.todayprice.ui.item.ItemActivity
import com.crystal.todayprice.util.CommonUtil.Companion.intentSerializable
import com.crystal.todayprice.viewmodel.PriceViewModel
import com.google.android.material.chip.Chip
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MarketActivity : BaseActivity(ToolbarType.BACK, TransitionMode.HORIZON) {

    private lateinit var binding: ActivityMarketBinding

    private val priceViewModel: PriceViewModel by viewModels {
        PriceViewModel.PriceViewModelFactory(PriceRepositoryImpl())
    }

    private val adapter: ItemAdapter = ItemAdapter {
        Toast.makeText(this, "${it.itemName} is clicked!!", Toast.LENGTH_SHORT).show()
        moveToItem(it)
    }

    private var market: Market? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMarketBinding.inflate(layoutInflater)
        baseBinding.contentLayout.addView(binding.root)

        market = intent.intentSerializable(MARKET_NAME, Market::class.java)

        Log.e(TAG, "market: $market")
        market?.let {
        }

        addChip()

    }

    private fun addChip() {
        val list = listOf<String>("첫번째", "두번째", "세번쨰", "네번째", "다섯번째", "여섯번째")

        for (string in list) {
            binding.chipGroup.addView(Chip(this).apply {
                text = string
                textSize = 20F
                isCheckable = true

                isCheckedIconVisible = false
                chipBackgroundColor = ContextCompat.getColorStateList(this@MarketActivity, R.color.bg_chip_state)

//                this.setChipBackgroundColorResource()
//                setOnClickListener {
//                    this.isChecked = !this.isChecked
//                }
            })
        }


    }

    override fun onResume() {
        super.onResume()

        setItems()

        binding.itemRecyclerView.apply {
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager

                    // 스크롤을 위로 올렸을 경우, 첫 번째 항목이 완전히 보이는지 확인 (맨 위까지 스크롤),
                    // 버벅거림 방지를 위해 transition 상태가 확인 후,
                    // 현재 애니메이션이 진행되고 있지 않다면 motion transition 수행
                    if (dy < 0
                        && layoutManager.findFirstCompletelyVisibleItemPosition() == 0
                        && binding.motionLayout.currentState == R.id.end
                        && (binding.motionLayout.progress >= 1f
                                || binding.motionLayout.progress <= 0f)
                    ) {
                        binding.motionLayout.transitionToStart()
                    }

                    // 스크롤을 아래로 내렸을 경우, 버벅거림 방지를 위해 transition 상태 확인 후,
                    // 현재 애니메이션이 진행되고 있지 않다면 motion transition 수행
                    if (dy > 0
                        && binding.motionLayout.currentState == R.id.start
                        && (binding.motionLayout.progress >= 1f
                                || binding.motionLayout.progress <= 0f)
                    ) {
                        binding.motionLayout.transitionToEnd()
                    }
                }
            })
        }
    }


    private fun moveToItem(item: NecessaryPrice) {
        val intent = Intent(this, ItemActivity::class.java)
        intent.putExtra(ItemActivity.ITEM_NAME, item)
        startActivity(intent)
    }

    private fun setItems() {

        binding.itemRecyclerView.adapter = adapter

        lifecycleScope.launch {
            priceViewModel.itemPagingDataFlow
                .collectLatest { items ->
                    Log.e(TAG, "flow ${items}")
                    adapter.submitData(items)
                }
        }

        market?.let {
            priceViewModel.handleQuery(it.name)
        }
    }

    companion object {
        const val MARKET_NAME = "market_name"
    }
}