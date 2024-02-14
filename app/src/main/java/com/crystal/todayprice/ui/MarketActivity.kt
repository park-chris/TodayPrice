package com.crystal.todayprice.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
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
import com.kakao.vectormap.label.LabelOptions
import com.kakao.vectormap.label.LabelStyle
import com.kakao.vectormap.label.LabelStyles
import com.kakao.vectormap.shape.MapPoints
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

    override fun onDestroy() {
        binding.mapView.removeAllViews()
        super.onDestroy()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setMap() {
        binding.mapView.start(object : KakaoMapReadyCallback() {
            override fun onMapReady(kakaoMap: KakaoMap) {
                val style = kakaoMap.labelManager?.addLabelStyles(LabelStyles.from(LabelStyle.from(R.drawable.ic_mark)))
                val options = LabelOptions.from(LatLng.from(market!!.latitude, market!!.longitude)).setStyles(style)
                val layer = kakaoMap.labelManager?.layer
                layer?.addLabel(options)

                binding.scrollView.setHitRect(binding.mapView.left, binding.mapView.top, binding.mapView.right, binding.mapView.bottom)
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
            intent.putExtra(MARKET_NAME, market?.name)
            startActivity(intent)
        }
    }

    private fun setScrollEvent() {
        binding.scrollView.apply {
            viewTreeObserver.addOnScrollChangedListener {
                if (scrollY <= 0
                    && binding.motionLayout.currentState == R.id.end
                    && (binding.motionLayout.progress >= 1f
                            || binding.motionLayout.progress <= 0f)
                ) {
                    binding.motionLayout.transitionToStart()
                }
                if (scrollY > 0
                    && binding.motionLayout.currentState == R.id.start
                    && (binding.motionLayout.progress >= 1f
                            || binding.motionLayout.progress <= 0f)
                ) {
                    binding.motionLayout.transitionToEnd()
                }
            }

        }
    }


    companion object {
        const val MARKET_OBJECT = "market_object"
        const val MARKET_NAME = "market_name"
        const val MARKET_ID = "market_id"
    }
}