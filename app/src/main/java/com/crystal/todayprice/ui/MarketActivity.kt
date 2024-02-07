package com.crystal.todayprice.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.crystal.todayprice.R
import com.crystal.todayprice.component.BaseActivity
import com.crystal.todayprice.component.ToolbarType
import com.crystal.todayprice.component.TransitionMode
import com.crystal.todayprice.data.Market
import com.crystal.todayprice.databinding.ActivityMarketBinding
import com.crystal.todayprice.util.CommonUtil.Companion.intentSerializable
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.LatLng
import com.kakao.vectormap.MapLifeCycleCallback
import com.kakao.vectormap.MapType
import com.kakao.vectormap.MapViewInfo
import java.lang.Exception

private const val TAG = "TestLog"
class MarketActivity : BaseActivity(ToolbarType.HOME, TransitionMode.HORIZON) {

    private lateinit var binding: ActivityMarketBinding

    private var market: Market? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMarketBinding.inflate(layoutInflater)
        baseBinding.contentLayout.addView(binding.root)

        market = intent.intentSerializable(MARKET_OBJECT, Market::class.java)

        market?.let {
            binding.market = it
        }

    }

    override fun onResume() {
        super.onResume()

        setScrollEvent()
        setupEvent()
        setMap()
    }

    private fun setMap() {
        binding.mapView.start(object : MapLifeCycleCallback() {
            override fun onMapDestroy() {
                Log.e(TAG, "onMapDestroy")
            }

            override fun onMapError(error: Exception?) {
                Log.e(TAG, "onMApError", error)

            }

        }, object : KakaoMapReadyCallback() {
            override fun onMapReady(kakaoMap: KakaoMap) {
                Log.e(TAG, "onMapReady")
            }

            override fun getPosition(): LatLng {
                market ?: return super.getPosition()
                return LatLng.from(market!!.latitude, market!!.longitude)
            }

        })
    }

    private fun setupEvent() {
        binding.pricesButton.setOnClickListener {
            val intent = Intent(this, ItemListActivity::class.java)
            intent.putExtra(MARKET_OBJECT, market)
            startActivity(intent)
        }
        binding.reviewTextView.setOnClickListener {
            val intent = Intent(this, ReviewActivity::class.java)
            intent.putExtra(MARKET_ID, market?.id)
            startActivity(intent)
        }
    }

    private fun setScrollEvent() {
        binding.scrollView.apply {
            viewTreeObserver.addOnScrollChangedListener {
                if (scrollY == 0
                    && binding.motionLayout.currentState == R.id.end
                    && (binding.motionLayout.progress >= 1f || binding.motionLayout.progress <= 0f)
                ) {
                    binding.motionLayout.transitionToStart()
                }

                // 아래로 스크롤할 때의 처리
                 if (scrollY > 0 && binding.motionLayout.currentState == R.id.start
                     && (binding.motionLayout.progress >= 1f || binding.motionLayout.progress <= 0f)
                 ) {
                     binding.motionLayout.transitionToEnd()
                 }

                val maxScroll = getChildAt(0).height - height

                if (scrollY == maxScroll
                    && binding.motionLayout.currentState == R.id.end
                    && (binding.motionLayout.progress >= 1f || binding.motionLayout.progress <= 0f)
                ) {
                    binding.motionLayout.transitionToStart()
                }

            }

        }
    }


    companion object {
        const val MARKET_OBJECT = "market_object"
        const val MARKET_ID = "market_id"
    }
}