package com.crystal.todayprice.util

import android.content.Context
import com.crystal.todayprice.R
import com.crystal.todayprice.data.ItemType
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

        fun categoryFormat(context: Context, category: String): String {
            return when (category) {
                "all" -> context.resources.getString(R.string.all)
                "grain" -> context.resources.getString(R.string.grain)
                "fruits" -> context.resources.getString(R.string.fruits)
                "seaFood" -> context.resources.getString(R.string.seaFood)
                "meatEggs" -> context.resources.getString(R.string.meatEggs)
                "vegetables" -> context.resources.getString(R.string.vegetables)
                "seasonings" -> context.resources.getString(R.string.seasonings)
                "processedFoods" -> context.resources.getString(R.string.processedFoods)
                "dairyProducts" -> context.resources.getString(R.string.dairyProducts)
                "beverages" -> context.resources.getString(R.string.beverages)
                "householdItems" -> context.resources.getString(R.string.householdItems)
                "undefined" -> context.resources.getString(R.string.undefined)
                else -> ""
            }
        }
    }
}