package com.crystal.todayprice.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.crystal.todayprice.R
import com.crystal.todayprice.adapter.ItemAdapter
import com.crystal.todayprice.component.BaseActivity
import com.crystal.todayprice.component.ToolbarType
import com.crystal.todayprice.component.TransitionMode
import com.crystal.todayprice.data.Item
import com.crystal.todayprice.data.Market
import com.crystal.todayprice.databinding.ActivityItemListBinding
import com.crystal.todayprice.repository.ItemRepositoryImpl
import com.crystal.todayprice.util.CommonUtil.Companion.intentSerializable
import com.crystal.todayprice.util.TextUtil
import com.crystal.todayprice.viewmodel.ItemViewModel
import com.google.android.material.chip.Chip
import kotlinx.coroutines.launch

class ItemListActivity : BaseActivity(ToolbarType.HOME, TransitionMode.HORIZON) {

    private lateinit var binding: ActivityItemListBinding

    private val itemViewModel: ItemViewModel by viewModels {
        ItemViewModel.ItemViewModelFactory(ItemRepositoryImpl())
    }

    private var market: Market? = null

    private val adapter: ItemAdapter = ItemAdapter {
        moveToItem(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityItemListBinding.inflate(layoutInflater)
        baseBinding.contentLayout.addView(binding.root)

        market = intent.intentSerializable(MarketActivity.MARKET_NAME, Market::class.java)

        setItems()

        market?.let {
            itemViewModel.getItems(it.id)
        }

        addChip()
        setItems()
    }

    private fun setItems() {

        binding.itemRecyclerView.layoutManager = GridLayoutManager(this, 2)
        binding.itemRecyclerView.adapter = adapter

        lifecycleScope.launch {
            itemViewModel.items.observe(this@ItemListActivity, Observer {
                adapter.submitList(it)
            })
        }

    }

    private fun moveToItem(item: Item) {
        val intent = Intent(this, ItemActivity::class.java)
        intent.putExtra(ItemActivity.ITEM_NAME, item)
        intent.putExtra(MarketActivity.MARKET_NAME, market)
        startActivity(intent)
    }

    private fun addChip() {

        val categoryArray = resources.getStringArray(R.array.item_type_array)

        for (category in categoryArray) {

            val categoryId = when (category) {
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
                else -> R.id.all
            }

            binding.chipGroup.addView(Chip(this).apply {
                id = categoryId
                text = TextUtil.categoryFormat(this@ItemListActivity, category)
                textSize = 20F
                chipStrokeColor = ContextCompat.getColorStateList(this@ItemListActivity, R.color.stroke_chip_state)
                chipStrokeWidth = 2F
                isCheckable = true
                isCheckedIconVisible = false
                isChecked = category == "all"
                chipBackgroundColor =
                    ContextCompat.getColorStateList(this@ItemListActivity, R.color.bg_chip_state)
                setTextColor(ContextCompat.getColorStateList(this@ItemListActivity, R.color.text_chip_state))

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
                    R.id.all -> adapter.submitList(itemViewModel.items.value ?: emptyList())
                    R.id.grain -> adapter.submitList(itemViewModel.getFilterItems("grain"))
                    R.id.fruits -> adapter.submitList(itemViewModel.getFilterItems("fruits"))
                    R.id.seaFood -> adapter.submitList(itemViewModel.getFilterItems("seaFood"))
                    R.id.meatEggs -> adapter.submitList(itemViewModel.getFilterItems("meatEggs"))
                    R.id.vegetables -> adapter.submitList(itemViewModel.getFilterItems("vegetables"))
                    R.id.seasonings -> adapter.submitList(itemViewModel.getFilterItems("seasonings"))
                    R.id.processedFoods -> adapter.submitList(itemViewModel.getFilterItems("processedFoods"))
                    R.id.dairyProducts -> adapter.submitList(itemViewModel.getFilterItems("dairyProducts"))
                    R.id.beverages -> adapter.submitList(itemViewModel.getFilterItems("beverages"))
                    R.id.householdItems -> adapter.submitList(itemViewModel.getFilterItems("householdItems"))
                    R.id.undefined -> adapter.submitList(itemViewModel.getFilterItems("undefined"))
                }
            }
        }

    }


}