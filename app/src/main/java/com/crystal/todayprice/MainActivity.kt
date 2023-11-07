package com.crystal.todayprice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import com.crystal.todayprice.databinding.ActivityMainBinding
import com.google.android.material.appbar.AppBarLayout
import kotlin.math.abs

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_with_search, menu)

        binding.appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->

            if (verticalOffset == 0) {

                // 스크롤이 가장 위로 올라왔을 때 (expanded 상태)
                // 검색 창을 다시 표시하고 메뉴를 2개로 설정
                // 여기에서 검색 창을 표시하는 논리를 추가
                menu?.clear()
                menuInflater.inflate(R.menu.menu_with_search, menu)
            } else if (abs(verticalOffset) >= appBarLayout.totalScrollRange) {
                // 스크롤이 가장 아래로 내려갔을 때 (collapsed 상태)
                // 검색 창을 숨기고 메뉴를 3개로 설정
                // 여기에서 검색 창을 숨기는 논리를 추가
                menu?.clear()
                menuInflater.inflate(R.menu.menu_without_search, menu)
            }
        })

        return true
    }
}