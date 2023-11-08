package com.crystal.todayprice.component

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


open class BaseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBaseBinding
    protected lateinit var contentFrameLayout: NestedScrollView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBaseBinding.inflate(layoutInflater)
        contentFrameLayout = binding.contentLayout
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_launcher_foreground)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search -> {
                Toast.makeText(this, "검색하기", Toast.LENGTH_SHORT).show()
            }
            R.id.action_favorite -> {
                Toast.makeText(this, "즐겨찾기", Toast.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_with_search, menu)
//        binding.appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
//
//            if (verticalOffset == 0) {
//
//                // 스크롤이 가장 위로 올라왔을 때 (expanded 상태)
//                // 검색 창을 다시 표시하고 메뉴를 2개로 설정
//                // 여기에서 검색 창을 표시하는 논리를 추가
//                menu?.clear()
//                menuInflater.inflate(R.menu.menu_with_search, menu)
//            } else if (abs(verticalOffset) >= appBarLayout.totalScrollRange) {
//                // 스크롤이 가장 아래로 내려갔을 때 (collapsed 상태)
//                // 검색 창을 숨기고 메뉴를 3개로 설정
//                // 여기에서 검색 창을 숨기는 논리를 추가
//                menu?.clear()
//                menuInflater.inflate(R.menu.menu_without_search, menu)
//            } else {
//                menuInflater.inflate(R.menu.menu_with_search, menu)
//
//            }
//        })
        return true
    }

//    protected open fun beforeSetContentView() {}
//    protected open fun initView() {}
//    protected open fun initViewModel() {}
//    protected open fun initListener() {}

}