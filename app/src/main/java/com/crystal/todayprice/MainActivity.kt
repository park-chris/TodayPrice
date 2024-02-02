package com.crystal.todayprice

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.crystal.todayprice.adapter.ListItemAdapter
import com.crystal.todayprice.component.BaseActivity
import com.crystal.todayprice.util.OnItemListItemListener
import com.crystal.todayprice.component.ToolbarType
import com.crystal.todayprice.data.ListItem
import com.crystal.todayprice.data.Market
import com.crystal.todayprice.data.News
import com.crystal.todayprice.data.Notice
import com.crystal.todayprice.data.User
import com.crystal.todayprice.data.ViewType
import com.crystal.todayprice.databinding.ActivityMainBinding
import com.crystal.todayprice.repository.ListItemRepositoryImpl
import com.crystal.todayprice.ui.MarketActivity
import com.crystal.todayprice.ui.MarketListActivity
import com.crystal.todayprice.ui.NewsListActivity
import com.crystal.todayprice.ui.NoticeActivity
import com.crystal.todayprice.viewmodel.ListItemViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : BaseActivity(ToolbarType.MENU) {
    private lateinit var binding: ActivityMainBinding

    private val listItemViewModel: ListItemViewModel by viewModels {
        ListItemViewModel.ListItemViewModelFactory(ListItemRepositoryImpl())
    }

    private lateinit var adapter: ListItemAdapter
    private var currentUser: FirebaseUser? = FirebaseAuth.getInstance().currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        baseBinding.contentLayout.addView(binding.root)
        getUser()
        setAdapter()
        observerList()
    }

    private fun getUser() {
        currentUser?.let {
            CoroutineScope(Dispatchers.Default).launch {
                val user = userViewModel.getUser(it.uid)
                withContext(Dispatchers.Main) {
                    updateProfile(user)
                }
            }
        }
    }

    private fun setAdapter() {
        adapter = ListItemAdapter(object : OnItemListItemListener {
            override fun onItemClick(listItem: ListItem) {
                when (listItem) {
                    is Market -> {
                        moveToActivity(MarketActivity::class.java, listItem)
                    }
                    is News -> {
                        Toast.makeText(this@MainActivity, "news title : ${listItem.newsTitle}", Toast.LENGTH_SHORT).show()
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

    private fun observerList() {
        listItemViewModel.listItem.observe(this, Observer { listItem ->
            listItem?.let {
                setMarketList(it)
                binding.progressBar.isVisible = false
            }
        })

        listItemViewModel.getHomeListItem()
    }

    private fun moveToActivity(destinationClass: Class<*>, argument: ListItem?) {
        val intent = Intent(this, destinationClass)
        argument?.let {
            when (it) {
                is Market -> { intent.putExtra(MarketActivity.MARKET_OBJECT, it) }
                is Notice -> { intent.putExtra(NOTICE_OBJECT, it) }
                else -> {}
            }
        }
        startActivity(intent)
    }

    private fun setMarketList(listItem: List<ListItem>) {
        binding.recyclerView.adapter = adapter

        adapter.submitList(listItem)
    }

    companion object {
        const val NOTICE_OBJECT = "notice_object"
    }
}
