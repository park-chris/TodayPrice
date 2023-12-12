package com.crystal.todayprice.component

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.crystal.todayprice.MainActivity
import com.crystal.todayprice.R
import com.crystal.todayprice.databinding.ActivityBaseBinding


open class BaseActivity(
    private val toolbarType: ToolbarType,
    private val transitionMode: TransitionMode = TransitionMode.NONE
) : AppCompatActivity() {
    protected lateinit var baseBinding: ActivityBaseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        baseBinding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(baseBinding.root)
        setSupportActionBar(baseBinding.toolbar)
        setToolbar()

        when (transitionMode) {
            TransitionMode.HORIZON -> overridePendingTransition(R.anim.horizon_enter, R.anim.none)
            TransitionMode.VERTICAL -> overridePendingTransition(R.anim.vertical_enter, R.anim.none)
            else -> {}
        }
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
        super.onBackPressed()

        if (isFinishing) {
            when (transitionMode) {
                TransitionMode.HORIZON -> overridePendingTransition(R.anim.none, R.anim.horizon_exit)
                TransitionMode.VERTICAL -> overridePendingTransition(R.anim.none, R.anim.vertical_exit)
                else -> Unit
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search -> {
                Toast.makeText(this, "검색하기", Toast.LENGTH_SHORT).show()
            }

            R.id.action_favorite -> {
                Toast.makeText(this, "즐겨찾기", Toast.LENGTH_SHORT).show()
            }

            R.id.action_home -> {
                Toast.makeText(this, "홈", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }

            android.R.id.home -> {
                if (toolbarType == ToolbarType.MENU) {
                    Toast.makeText(this, "메뉴", Toast.LENGTH_SHORT).show()
                }
                if (toolbarType == ToolbarType.BACK) {
                    Toast.makeText(this, "뒤로가기", Toast.LENGTH_SHORT).show()
                    finish()
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
    private fun setToolbar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(applicationContext, R.color.background)))

        if (toolbarType == ToolbarType.MENU) {
            supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu)
        }
        if (toolbarType == ToolbarType.BACK) {
            supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
        }
    }


}

enum class ToolbarType {
    MENU,
    BACK,
}

enum class TransitionMode {
    NONE,
    HORIZON,
    VERTICAL
}