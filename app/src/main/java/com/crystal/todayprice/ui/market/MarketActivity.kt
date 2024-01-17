package com.crystal.todayprice.ui.market

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.crystal.todayprice.R
import com.crystal.todayprice.adapter.ItemAdapter
import com.crystal.todayprice.component.BaseActivity
import com.crystal.todayprice.component.ToolbarType
import com.crystal.todayprice.component.TransitionMode
import com.crystal.todayprice.data.Item
import com.crystal.todayprice.data.Market
import com.crystal.todayprice.databinding.ActivityMarketBinding
import com.crystal.todayprice.repository.ItemRepositoryImpl
import com.crystal.todayprice.ui.item.ItemActivity
import com.crystal.todayprice.util.CommonUtil.Companion.intentSerializable
import com.crystal.todayprice.util.TextUtil
import com.crystal.todayprice.viewmodel.ItemViewModel
import com.google.android.material.chip.Chip
import kotlinx.coroutines.launch

private const val TAG = "TestLog"
class MarketActivity : BaseActivity(ToolbarType.BACK, TransitionMode.HORIZON) {

    private lateinit var binding: ActivityMarketBinding

    private val itemViewModel: ItemViewModel by viewModels {
        ItemViewModel.ItemViewModelFactory(ItemRepositoryImpl())
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

        market?.let {

        }

        addChip()

    }

    override fun onResume() {
        super.onResume()

        setItems()
        setScrollEvent()

    }
    private fun setScrollEvent() {
        binding.itemRecyclerView.apply {
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager

                    if (dy < 0
                        && layoutManager.findFirstCompletelyVisibleItemPosition() == 0
                        && binding.motionLayout.currentState == R.id.end
                        && (binding.motionLayout.progress >= 1f
                                || binding.motionLayout.progress <= 0f)
                    ) {
                        binding.motionLayout.transitionToStart()
                    }

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

    private fun moveToItem(item: Item) {
        val intent = Intent(this, ItemActivity::class.java)
        intent.putExtra(ItemActivity.ITEM_NAME, item)
        startActivity(intent)
    }

    private fun setItems() {

        binding.itemRecyclerView.adapter = adapter

        lifecycleScope.launch {
            itemViewModel.items.observe(this@MarketActivity, Observer {
                adapter.submitList(it)
            })
        }

        market?.let {
            itemViewModel.getItems(it.id)
        }
    }

    private fun addChip() {

        val categoryArray = resources.getStringArray(R.array.item_type_array)

        for (category in categoryArray) {

            binding.chipGroup.addView(Chip(this).apply {
                id = when (category) {
                    "all" -> R.id.all
                    "grain" -> R.id.grain
                    "fruits" -> R.id.fruits
                    "seaFood" -> R.id.seaFood
                    "meatEggs" -> R.id.meatEggs
                    "vegetables" -> R.id.vegetables
                    "seasonings" -> R.id.seasonings
                    "processedFoods" -> R.id.processedFoods
                    "dairyProducts" -> R.id.dairyProducts
                    "beverages" -> R.id.beverages
                    "householdItems" -> R.id.householdItems
                    "undefined" -> R.id.undefined
                    else -> { R.id.all }
                }
                text = TextUtil.categoryFormat(this@MarketActivity, category)
                textSize = 20F
                isCheckable = true
                isCheckedIconVisible = false
                isChecked = category == "all"
                chipBackgroundColor = ContextCompat.getColorStateList(this@MarketActivity, R.color.bg_chip_state)

                this.setOnClickListener {
                    if (!this.isChecked) {
                        this.isChecked = true
                    }
                }
            })
        }

        binding.chipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            for (id in checkedIds) {
                when (id) {
                    R.id.all -> Log.e(TAG, "전체")
                    R.id.grain -> Log.e(TAG, "grain")
                    R.id.fruits -> Log.e(TAG, "fruits")
                    R.id.seaFood -> Log.e(TAG, "seaFood")
                    R.id.meatEggs -> Log.e(TAG, "meatEggs")
                    R.id.vegetables -> Log.e(TAG, "vegetables")
                    R.id.seasonings -> Log.e(TAG, "seasonings")
                    R.id.processedFoods -> Log.e(TAG, "processedFoods")
                    R.id.dairyProducts -> Log.e(TAG, "dairyProducts")
                    R.id.beverages -> Log.e(TAG, "beverages")
                    R.id.householdItems -> Log.e(TAG, "householdItems")
                    R.id.undefined -> Log.e(TAG, "undefined")
                }
            }

        }

    }

    companion object {
        const val MARKET_NAME = "market_name"
    }
}