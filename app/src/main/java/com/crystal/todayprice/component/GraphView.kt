package com.crystal.todayprice.component

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import com.crystal.todayprice.R
import com.crystal.todayprice.data.Price
import com.crystal.todayprice.util.TextUtil

data class GraphPoint(
     val index: Int,
     var x: Float,
     var y: Float,
     val priceTag: Int,
     val dateTag: String,
)

class GraphView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    private val padding = 5f

    private val graphPadding = 100f
    private val graphMarginRight = padding
    private val graphMarginLeft = 100f
    private val graphMarginTop = padding
    private val graphMarginBottom = 40f

    private val startGraphX = graphMarginLeft
    private val startGraphY = graphMarginTop

    private var endGraphX = width.toFloat() - graphMarginRight
    private var endGraphY = height.toFloat() - graphMarginBottom

    private var pointList = emptyList<GraphPoint>()

    private val frameLinePaint = Paint().apply {
        color =  ContextCompat.getColor(context, R.color.hint)
        strokeWidth = 5f
    }

    private val linePaint = Paint().apply {
        color =  ContextCompat.getColor(context, R.color.highlight)
        strokeWidth = 5f
    }

    private val pointPaint = Paint().apply {
        color =  ContextCompat.getColor(context, R.color.highlight)
        strokeWidth = 5f
        style = Paint.Style.FILL_AND_STROKE
    }

    private val textPaint = Paint().apply {
        color =  ContextCompat.getColor(context, R.color.hint)
        textSize = 25f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        endGraphX = width.toFloat() - padding
        endGraphY = height.toFloat() - 40f

        drawFrame(canvas)
        drawGraph(pointList, canvas)
    }

    private fun drawFrame(canvas: Canvas) {
        canvas.drawLine(startGraphX, startGraphY, endGraphX, startGraphY, frameLinePaint)
        canvas.drawLine(startGraphX, startGraphY, startGraphX, endGraphY, frameLinePaint)
        canvas.drawLine(startGraphX, endGraphY, endGraphX, endGraphY, frameLinePaint)
        canvas.drawLine(endGraphX, endGraphY, endGraphX, startGraphY, frameLinePaint)
    }

    fun setData(list: List<Price>) {

        val intervalX = ((endGraphX  - graphPadding * 2) / list.size)
        var x = startGraphX + graphPadding

        val points = mutableListOf<GraphPoint>()
        val prices = mutableListOf<Int>()

        for (i: Int in list.indices) {
            val point = GraphPoint(i, x, 0f,  list[i].itemPrice, list[i].surveyDate)
            points.add(point)
            prices.add(i, list[i].itemPrice)
            x += intervalX
        }
        val filteredList = prices.distinct()
        val sortedList = filteredList.sortedByDescending {it}

        val intervalY = ((endGraphY - startGraphY - graphPadding) / sortedList.size)
        points.map {point: GraphPoint ->
            val index = sortedList.indexOf(point.priceTag)
            point.y = (index + 1) * intervalY
            point
        }
        this.pointList = points
        invalidate()
    }

    private fun drawGraph(points: List<GraphPoint>, canvas: Canvas) {
        for (i in points.indices) {
            val point = points[i]
            canvas.drawCircle(point.x, point.y, 10f, pointPaint)
            if (i != 0) {
                val prevPoint = points[i - 1]
                canvas.drawLine(prevPoint.x, prevPoint.y, point.x, point.y, linePaint)
            }
            canvas.drawText(point.dateTag, point.x - 50f, height.toFloat(), textPaint)
            canvas.drawText( TextUtil.priceFormat(context,  point.priceTag.toDouble()), padding, point.y, textPaint)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_UP) {
            // 여기에 클릭 이벤트 처리 코드 추가
        }
        return true
    }

}