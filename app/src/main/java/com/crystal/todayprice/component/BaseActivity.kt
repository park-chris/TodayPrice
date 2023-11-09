package com.crystal.todayprice.component

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.NestedScrollView
import com.crystal.todayprice.R
import com.crystal.todayprice.databinding.ActivityBaseBinding
import com.google.android.material.appbar.AppBarLayout
import kotlin.math.abs


open class BaseActivity(
    private val toolbarType: ToolbarType
) : AppCompatActivity() {
    private lateinit var binding: ActivityBaseBinding
    protected lateinit var contentFrameLayout: NestedScrollView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBaseBinding.inflate(layoutInflater)
        contentFrameLayout = binding.contentLayout
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.background)))

        if (toolbarType == ToolbarType.MENU) {
            supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu)
        }
        if (toolbarType == ToolbarType.BACK) {
            supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search -> {
                Toast.makeText(this, "검색하기", Toast.LENGTH_SHORT).show()
            }

            R.id.action_favorite -> {
                Toast.makeText(this, "즐겨찾기", Toast.LENGTH_SHORT).show()
            }

            android.R.id.home -> {
                if (toolbarType == ToolbarType.MENU) {
                    Toast.makeText(this, "메뉴", Toast.LENGTH_SHORT).show()
                }
                if (toolbarType == ToolbarType.BACK) {
                    Toast.makeText(this, "뒤로가기", Toast.LENGTH_SHORT).show()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        when (toolbarType) {
            ToolbarType.MENU -> menuInflater.inflate(R.menu.menu_with_search, menu)
            ToolbarType.BACK -> menuInflater.inflate(R.menu.back_with_search, menu)
        }
        return true
    }

}

enum class ToolbarType {
    MENU,
    BACK,
}
