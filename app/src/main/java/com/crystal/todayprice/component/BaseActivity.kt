package com.crystal.todayprice.component

import android.app.ProgressDialog.show
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.crystal.todayprice.MainActivity
import com.crystal.todayprice.R
import com.crystal.todayprice.data.ListItem
import com.crystal.todayprice.data.Market
import com.crystal.todayprice.data.Notice
import com.crystal.todayprice.data.User
import com.crystal.todayprice.databinding.ActivityBaseBinding
import com.crystal.todayprice.databinding.DrawerHeaderBinding
import com.crystal.todayprice.ui.InquiryActivity
import com.crystal.todayprice.ui.LoginActivity
import com.crystal.todayprice.ui.MarketActivity
import com.crystal.todayprice.ui.MyFavoriteActivity
import com.crystal.todayprice.ui.MyInquiryActivity
import com.crystal.todayprice.ui.NoticeListActivity
import com.crystal.todayprice.ui.UserReviewActivity
import com.crystal.todayprice.viewmodel.UserViewModel
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth


open class BaseActivity(
    private val toolbarType: ToolbarType,
    private val transitionMode: TransitionMode = TransitionMode.NONE,
) : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    protected lateinit var baseBinding: ActivityBaseBinding
    private var searchViewVisible = false
    val userDataManager = UserDataManager.getInstance()

    val userViewModel by lazy {
        ViewModelProvider(this)[UserViewModel::class.java]
    }

    private lateinit var drawerHeaderBinding: DrawerHeaderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        baseBinding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(baseBinding.root)
        setSupportActionBar(baseBinding.toolbar)
        setToolbar()
        baseBinding.navigationView.setNavigationItemSelectedListener(this)

        setEnterAnimation()
        setSearchViewListener()
        setHeaderView()
    }

    override fun onResume() {
        super.onResume()
        updateProfile(userDataManager.user)
    }

    override fun finish() {
        super.finish()
        exitAnimation()
    }

    override fun onBackPressed() {

        if (baseBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            baseBinding.drawerLayout.closeDrawers()
        } else if (searchViewVisible) {
            closeSearchView()
        } else {
            super.onBackPressed()
            if (isFinishing) exitAnimation()
        }
    }

    override fun onDestroy() {
        baseBinding.searchView.setOnQueryTextListener(null)
        baseBinding.navigationView.setNavigationItemSelectedListener(null)
        super.onDestroy()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search -> actionMenuSearch()
            R.id.action_home -> actionMenuHome()
            android.R.id.home -> actionHome()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        when (toolbarType) {
            ToolbarType.MENU -> menuInflater.inflate(R.menu.menu_with_search, menu)
            ToolbarType.BACK -> menuInflater.inflate(R.menu.back_with_search, menu)
            ToolbarType.HOME -> menuInflater.inflate(R.menu.back_with_home, menu)
            ToolbarType.ONLY_BACK -> {}
        }
        return true
    }

    private fun exitAnimation() {
        when (transitionMode) {
            TransitionMode.HORIZON -> overridePendingTransition(
                R.anim.none,
                R.anim.horizon_exit
            )

            TransitionMode.VERTICAL -> overridePendingTransition(
                R.anim.none,
                R.anim.vertical_exit
            )

            else -> Unit
        }
    }

    private fun setHeaderView() {
        val headerView = baseBinding.navigationView.getHeaderView(0)
        drawerHeaderBinding = DataBindingUtil.bind(headerView)!!
    }

    private fun setEnterAnimation() {
        when (transitionMode) {
            TransitionMode.HORIZON -> overridePendingTransition(R.anim.horizon_enter, R.anim.none)
            TransitionMode.VERTICAL -> overridePendingTransition(R.anim.vertical_enter, R.anim.none)
            else -> {}
        }
    }

    private fun setSearchViewListener() {
        when (toolbarType) {
            ToolbarType.BACK -> {
                baseBinding.searchView.setOnQueryTextListener(object :
                    androidx.appcompat.widget.SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        onSearch(query)
                        return true
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        return true
                    }
                })
            }

            else -> {}
        }
    }

    private fun setToolbar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setBackgroundDrawable(
            ColorDrawable(
                ContextCompat.getColor(
                    applicationContext,
                    R.color.background
                )
            )
        )
        when (toolbarType) {
            ToolbarType.MENU -> supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu)
            ToolbarType.BACK -> supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
            ToolbarType.HOME -> supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
            ToolbarType.ONLY_BACK -> supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
        }
    }

    open fun actionMenuSearch() {
        if (toolbarType == ToolbarType.BACK) {
            openSearchView()
        }
    }

    private fun openSearchView() {
        baseBinding.searchView.visibility = View.VISIBLE
        baseBinding.toolbarLayout.visibility = View.GONE
        baseBinding.searchView.requestFocus()
        searchViewVisible = true
    }

    private fun closeSearchView() {
        baseBinding.searchView.visibility = View.GONE
        baseBinding.toolbarLayout.visibility = View.VISIBLE
        searchViewVisible = false
    }

    open fun onSearch(query: String?) {
        closeSearchView()
    }

    fun updateProfile(user: User?) {
        drawerHeaderBinding.user = user
        drawerHeaderBinding.executePendingBindings()
        if (userDataManager.user != null) {
            baseBinding.navigationView.menu.clear()
            baseBinding.navigationView.inflateMenu(R.menu.drawer_user)
        } else {
            baseBinding.navigationView.menu.clear()
            baseBinding.navigationView.inflateMenu(R.menu.drawer_anonymous)
        }

    }

//    open fun actionMenuFavorite() {}
    private fun actionMenuHome() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    fun moveToActivity(destinationClass: Class<*>, argument: ListItem?) {
        val intent = Intent(this, destinationClass)
        argument?.let {
            when (it) {
                is Market -> {
                    intent.putExtra(MarketActivity.MARKET_OBJECT, it)
                }

                is Notice -> {
                    intent.putExtra(MainActivity.NOTICE_OBJECT, it)
                }

                else -> {}
            }
        }
        startActivity(intent)
    }

    private fun actionHome() {
        when (toolbarType) {
            ToolbarType.MENU -> {
                baseBinding.drawerLayout.openDrawer(GravityCompat.START)
            }

            ToolbarType.BACK -> {
                finish()
            }

            ToolbarType.HOME -> {
                finish()
            }

            ToolbarType.ONLY_BACK -> {
                finish()
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_my_favorite -> startActivity(Intent(this, MyFavoriteActivity::class.java))
            R.id.action_inquiry -> startActivity(Intent(this, InquiryActivity::class.java))
            R.id.action_my_inquiry_list -> startActivity(Intent(this, MyInquiryActivity::class.java))
            R.id.action_login -> startActivity(Intent(this, LoginActivity::class.java))
            R.id.action_logout -> {
                userDataManager.user ?: return false
                FirebaseAuth.getInstance().signOut()
                userDataManager.user = null
                updateProfile(null)
            }
            R.id.action_my_review -> startActivity(Intent(this, UserReviewActivity::class.java))
            R.id.action_notice -> startActivity(Intent(this, NoticeListActivity::class.java))
        }
        return false
    }
}

enum class ToolbarType {
    MENU,
    BACK,
    HOME,
    ONLY_BACK,
}

enum class TransitionMode {
    NONE,
    HORIZON,
    VERTICAL
}