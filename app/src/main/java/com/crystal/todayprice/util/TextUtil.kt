package com.crystal.todayprice.util

import android.content.Context
import com.crystal.todayprice.R
import java.text.DecimalFormat

class TextUtil {
    companion object {
        fun priceFormat(context: Context, price: Double): String {
            return if (price <= 0) {
                context.getString(R.string.no_price)
            } else {
                val decimalFormat = DecimalFormat("#,###")
                val formattedPrice = decimalFormat.format(price)

                val result = context.getString(R.string.price_format_won, formattedPrice)
                result
            }
        }
    }
}