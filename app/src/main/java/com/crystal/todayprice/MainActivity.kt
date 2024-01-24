package com.crystal.todayprice

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.crystal.todayprice.adapter.ListItemAdapter
import com.crystal.todayprice.component.BaseActivity
import com.crystal.todayprice.component.OnItemListItemListener
import com.crystal.todayprice.component.ToolbarType
import com.crystal.todayprice.data.ListItem
import com.crystal.todayprice.data.Market
import com.crystal.todayprice.data.News
import com.crystal.todayprice.data.ViewType
import com.crystal.todayprice.databinding.ActivityMainBinding
import com.crystal.todayprice.repository.ListItemRepositoryImpl
import com.crystal.todayprice.ui.MarketActivity
import com.crystal.todayprice.viewmodel.ListItemViewModel

const val TAG = "TestLog"
class MainActivity : BaseActivity(ToolbarType.MENU) {
    private lateinit var binding: ActivityMainBinding

    private val listItemViewModel: ListItemViewModel by viewModels {
        ListItemViewModel.ListItemViewModelFactory(ListItemRepositoryImpl())
    }

    private lateinit var adapter: ListItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        baseBinding.contentLayout.addView(binding.root)

        adapter = ListItemAdapter(object : OnItemListItemListener {
            override fun onItemClick(listItem: ListItem) {
                when (listItem) {
                    is Market -> {
                        moveToMarketActivity(listItem)
                    }
                    is News -> {
                        Toast.makeText(this@MainActivity, "news title : ${listItem.newsTitle}", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onSeeMoreClick(viewType: ViewType) {
                when (viewType) {
                    ViewType.NEWS -> {
                        Toast.makeText(this@MainActivity, "뉴스화면으로 이동", Toast.LENGTH_SHORT).show()
                    }
                    ViewType.MARKET -> {
                        Toast.makeText(this@MainActivity, "마켓화면으로 이동", Toast.LENGTH_SHORT).show()
                    }
                    else -> {}
                }
            }

        })

        listItemViewModel.listItem.observe(this, Observer { listItem ->
            listItem?.let {
                setMarketList(it)
            }
        })

    }

    override fun onResume() {
        super.onResume()


        listItemViewModel.getHomeListItem()

    }

    private fun moveToMarketActivity(market: Market) {
        val intent = Intent(this, MarketActivity::class.java)
        intent.putExtra(MarketActivity.MARKET_NAME, market)
        startActivity(intent)
    }

    private fun setMarketList(listItem: List<ListItem>) {
        binding.recyclerView.adapter = adapter

        adapter.submitList(listItem)
    }

}
