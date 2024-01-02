package com.crystal.todayprice.component

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.crystal.todayprice.MainActivity
import com.crystal.todayprice.R
import com.crystal.todayprice.databinding.ActivityBaseBinding
import com.crystal.todayprice.repository.TAG
import com.google.android.material.navigation.NavigationView


open class BaseActivity(
    private val toolbarType: ToolbarType,
    private val transitionMode: TransitionMode = TransitionMode.NONE,
) : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    protected lateinit var baseBinding: ActivityBaseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        baseBinding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(baseBinding.root)
        setSupportActionBar(baseBinding.toolbar)
        setToolbar()
        baseBinding.navigationView.setNavigationItemSelectedListener(this)

        when (transitionMode) {
            TransitionMode.HORIZON -> overridePendingTransition(R.anim.horizon_enter, R.anim.none)
            TransitionMode.VERTICAL -> overridePendingTransition(R.anim.vertical_enter, R.anim.none)
            else -> {}
        }

    }

    fun setHeader() {
        val customView = layoutInflater.inflate(R.layout.market_header, null)
        baseBinding.toolbarLayout.addView(customView)
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
        } else {
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
        baseBinding.navigationView.setNavigationItemSelectedListener(null)
        super.onDestroy()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search -> {
                actionMenuSearch()
            }

            R.id.action_favorite -> {
                actionMenuFavorite()
            }

            R.id.action_home -> {
                actionMenuHome()
            }

            android.R.id.home -> {
                actionHome()
            }
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        when (toolbarType) {
            ToolbarType.MENU -> menuInflater.inflate(R.menu.menu_with_search, menu)
            ToolbarType.BACK -> menuInflater.inflate(R.menu.back_with_search, menu)
            ToolbarType.HOME -> menuInflater.inflate(R.menu.back_with_home, menu)
        }
        return true
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

        if (toolbarType == ToolbarType.MENU) {
            supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu)
        }
        if (toolbarType == ToolbarType.BACK) {
            supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
        }
    }

    fun setImageView(url: String) {
        if (url.isNotEmpty()) {
            Glide.with(baseBinding.root)
                .load(url)
                .centerCrop()
                .error(R.drawable.no_picture)
                .into(baseBinding.backgroundImageView)
        } else {
            baseBinding.backgroundImageView.setImageResource(R.drawable.no_picture)
        }
    }

    fun setTitle(title: String) {
        baseBinding.toolbarLayout.title = title
        baseBinding.backgroundImageView.isVisible = true
        supportActionBar?.setBackgroundDrawable(
            ColorDrawable(
                ContextCompat.getColor(
                    applicationContext,
                    android.R.color.transparent
                )
            )
        )
    }

    private fun actionMenuSearch() {
        Toast.makeText(this, "검색하기", Toast.LENGTH_SHORT).show()
    }
    private fun actionMenuFavorite() {
        Toast.makeText(this, "즐겨찾기", Toast.LENGTH_SHORT).show()
    }
    private fun actionMenuHome() {
        Toast.makeText(this, "호모오옴", Toast.LENGTH_SHORT).show()

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
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuItem1 -> Toast.makeText(this, "item1 clicked", Toast.LENGTH_SHORT).show()
            R.id.menuItem2 -> Toast.makeText(this, "item2 clicked", Toast.LENGTH_SHORT).show()
            R.id.menuItem3 -> Toast.makeText(this, "item3 clicked", Toast.LENGTH_SHORT).show()
        }
        return false
    }
}

enum class ToolbarType {
    MENU,
    BACK,
    HOME,
}

enum class TransitionMode {
    NONE,
    HORIZON,
    VERTICAL
}