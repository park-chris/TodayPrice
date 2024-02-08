package com.crystal.todayprice.component

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.ScrollView
import com.kakao.vectormap.MapView

class CustomScrollView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ScrollView(context, attrs) {

    private var mapTouchStarted = false
    private val hitRect = Rect()

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.actionMasked) {
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL-> {
                mapTouchStarted = false
                return super.onTouchEvent(event)
            }
        }

        mapTouchStarted = hitRect.contains(event.x.toInt(), event.y.toInt())
        return super.onTouchEvent(event) && !mapTouchStarted
    }

    private val gestureListener by lazy {
        object : GestureDetector.SimpleOnGestureListener() {
            override fun onScroll(
                e1: MotionEvent,
                e2: MotionEvent,
                distanceX: Float,
                distanceY: Float
            ): Boolean {
                return hitRect.contains(e1.x.toInt(), e1.y.toInt())
            }
        }
    }

    private val gestureDetector by lazy {
        GestureDetector(context, gestureListener)
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_SCROLL-> return false
            MotionEvent.ACTION_UP -> {
                mapTouchStarted = false
                return true
            }
        }
        return gestureDetector.onTouchEvent(event)
    }

    fun setHitRect(left: Int,  top: Int, right: Int, bottom: Int) {
        hitRect.set(left, top, right, bottom)
    }
}