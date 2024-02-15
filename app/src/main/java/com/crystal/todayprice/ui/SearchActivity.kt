package com.crystal.todayprice.ui

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.crystal.todayprice.R
import com.crystal.todayprice.adapter.ListItemAdapter
import com.crystal.todayprice.component.BaseActivity
import com.crystal.todayprice.component.ToolbarType
import com.crystal.todayprice.component.TransitionMode
import com.crystal.todayprice.data.Horizontal
import com.crystal.todayprice.data.ListItem
import com.crystal.todayprice.data.Market
import com.crystal.todayprice.data.Notice
import com.crystal.todayprice.data.ViewType
import com.crystal.todayprice.databinding.ActivitySearchBinding
import com.crystal.todayprice.repository.ListItemRepositoryImpl
import com.crystal.todayprice.util.OnItemListItemListener
import com.crystal.todayprice.viewmodel.ListItemViewModel

class SearchActivity : BaseActivity(ToolbarType.ONLY_BACK, TransitionMode.HORIZON) {

    private lateinit var binding: ActivitySearchBinding

    private val listItemViewModel: ListItemViewModel by viewModels {
        ListItemViewModel.ListItemViewModelFactory(ListItemRepositoryImpl())
    }

    private lateinit var adapter: ListItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySearchBinding.inflate(layoutInflater)
        baseBinding.contentLayout.addView(binding.root)
        baseBinding.progressBar.visibility = View.VISIBLE

        setAdapter()
        observerList()
        setSearch()
        clickEvent()
    }

    private fun clickEvent() {
        binding.recyclerView.setOnClickListener {
            hideKeyboard()
        }
    }

    private fun setSearch() {
        binding.searchButton.setOnClickListener {
            search()
        }

        binding.searchEditText.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(
                textView: TextView?,
                actionId: Int,
                event: KeyEvent?
            ): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    search()
                    return true
                }
                return false
            }
        })
    }

    private fun search() {
        val query = binding.searchEditText.text.toString()
        hideKeyboard()
        if (query.isBlank()) {
            Toast.makeText(this, getString(R.string.search_hint), Toast.LENGTH_SHORT).show()
        } else {
            baseBinding.progressBar.visibility = View.VISIBLE
            listItemViewModel.getSearchListItem(query)
        }
    }

    private fun setAdapter() {
        adapter = ListItemAdapter(object : OnItemListItemListener {
            override fun onItemClick(listItem: ListItem) {
                when (listItem) {
                    is Market -> {
                        moveToActivity(MarketActivity::class.java, listItem)
                    }

                    is Notice -> {
                        moveToActivity(NoticeActivity::class.java, listItem)
                    }
                }
            }

            override fun onSeeMoreClick(viewType: ViewType) {
                when (viewType) {
                    ViewType.NEWS -> moveToActivity(NewsListActivity::class.java, null)
                    ViewType.MARKET -> moveToActivity(MarketListActivity::class.java, null)
                    else -> {}
                }
            }
        })
    }

    private fun setMarketList(listItem: List<ListItem>) {
        binding.recyclerView.adapter = adapter
        adapter.submitList(listItem)
    }

    private fun hideKeyboard() {
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(binding.searchEditText.windowToken, 0)
        binding.searchEditText.clearFocus()
    }

    private fun observerList() {
        listItemViewModel.listItem.observe(this, Observer { listItem ->
            listItem?.let {
                if (listItem.size > 1) {
                    for (item in listItem) {
                        val horizontal = item as Horizontal
                        if (horizontal.items.isEmpty()) {
                            binding.infoTextView.visibility = View.VISIBLE
                        }
                    }
                } else {
                    binding.infoTextView.visibility = View.GONE
                }
                setMarketList(it)
                baseBinding.progressBar.isVisible = false
            }
        })

        listItemViewModel.getSearchListItem(null)
    }


}