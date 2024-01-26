package com.crystal.todayprice.component

import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import android.widget.SearchView.OnQueryTextListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import com.crystal.todayprice.MainActivity
import com.crystal.todayprice.R
import com.crystal.todayprice.databinding.ActivityBaseBinding
import com.google.android.material.navigation.NavigationView


open class BaseActivity(
    private val toolbarType: ToolbarType,
    private val transitionMode: TransitionMode = TransitionMode.NONE,
) : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    protected lateinit var baseBinding: ActivityBaseBinding
    private var searchViewVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        baseBinding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(baseBinding.root)
        setSupportActionBar(baseBinding.toolbar)
        setToolbar()
        baseBinding.navigationView.setNavigationItemSelectedListener(this)


        setAnimation()
        setSearchViewListener()

    }

    override fun finish() {
        super.finish()

        when (transitionMode) {
            TransitionMode.HORIZON -> overridePendingTransition(R.anim.none, R.anim.horizon_exit)
            TransitionMode.VERTICAL -> overridePendingTransition(R.anim.none, R.anim.vertical_exit)
            else -> {}
        }
    }

    override fun onBackPressed() {

        if (baseBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            baseBinding.drawerLayout.closeDrawers()
        }
        else if (searchViewVisible) {
            closeSearchView()
        }
        else {
            super.onBackPressed()

            if (isFinishing) {
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
            R.id.action_favorite -> actionMenuFavorite()
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

    private fun setAnimation() {
        when (transitionMode) {
            TransitionMode.HORIZON -> overridePendingTransition(R.anim.horizon_enter, R.anim.none)
            TransitionMode.VERTICAL -> overridePendingTransition(R.anim.vertical_enter, R.anim.none)
            else -> {}
        }
    }

    private fun setSearchViewListener() {
        when (toolbarType) {
            ToolbarType.BACK -> {
                baseBinding.searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
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
    open fun actionMenuFavorite() {}
    private fun actionMenuHome() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
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
            R.id.menuItem1 -> Toast.makeText(this, "item1 is clicked", Toast.LENGTH_SHORT).show()
            R.id.menuItem2 -> Toast.makeText(this, "item2 is clicked", Toast.LENGTH_SHORT).show()
            R.id.menuItem3 -> Toast.makeText(this, "item3 is clicked", Toast.LENGTH_SHORT).show()
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