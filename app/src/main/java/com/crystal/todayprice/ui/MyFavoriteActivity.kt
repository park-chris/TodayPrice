package com.crystal.todayprice.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.crystal.todayprice.R
import com.crystal.todayprice.adapter.MarketAdapter
import com.crystal.todayprice.component.BaseActivity
import com.crystal.todayprice.component.CustomDialog
import com.crystal.todayprice.component.ToolbarType
import com.crystal.todayprice.component.TransitionMode
import com.crystal.todayprice.data.Market
import com.crystal.todayprice.databinding.ActivityMyFavoriteBinding
import com.crystal.todayprice.util.GridSpacingItemDecoration
import com.crystal.todayprice.util.OnDialogListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
        adapter = MarketAdapter(onClick = { moveToMarket(it) }, onFavorite = {market ->

            if (userDataManager.user == null) {
                Toast.makeText(
                    this@MyFavoriteActivity,
                    getString(R.string.require_login),
                    Toast.LENGTH_SHORT
                ).show()
                return@MarketAdapter
            }
            val dialog = CustomDialog(this, object : OnDialogListener {
                override fun onOk() {
                    CoroutineScope(Dispatchers.Main).launch {
                        userViewModel.removeFavorite(userDataManager.user!!.id, market.id)
                        val newList = userDataManager.user!!.favoriteList.toMutableList()
                        newList.remove(market.id)
                        userDataManager.user = userDataManager.user!!.copy(favoriteList = newList.toList())
                        val list = mutableListOf<Market>()
                        list.addAll(adapter.getList())
                        list.remove(market)
                        submitList(list)
                    }
                }
            })

            dialog.start(null, getString(R.string.favorite_remove_dialog_message), getString(R.string.cancel), getString(R.string.button_delete), true)

        })

        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.horizontal_padding)
        val spanCount = 2
        val includeEdge = true

        binding.recyclerView.layoutManager = GridLayoutManager(this, spanCount)
        val itemDecoration = GridSpacingItemDecoration(spanCount, spacingInPixels, includeEdge)
        binding.recyclerView.addItemDecoration(itemDecoration)
        binding.recyclerView.adapter = adapter

    }

}