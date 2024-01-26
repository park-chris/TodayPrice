package com.crystal.todayprice.util

import android.content.Context
import com.crystal.todayprice.R
import com.crystal.todayprice.data.Item
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
                "search" -> context.resources.getString(R.string.search_result)
                else -> ""
            }
        }

        fun stringToItemType(string: String): ItemType? {
            return when (string) {
                "grain" -> ItemType.GRAIN
                "fruits" -> ItemType.FRUITS
                "seaFood" -> ItemType.SEAFOOD
                "meatEggs" -> ItemType.MEAT_EGGS
                "vegetables" -> ItemType.VEGETABLES
                "seasonings" -> ItemType.SEASONINGS
                "processedFoods" -> ItemType.PROCESSED_FOODS
                "dairyProducts" -> ItemType.DAIRY_PRODUCTS
                "beverages" -> ItemType.BEVERAGES
                "householdItems" -> ItemType.HOUSEHOLD_ITEMS
                "undefined" -> ItemType.UNDEFINED
                else -> null
            }
        }

        fun itemTypeToString(type: ItemType): String? {
            return when (type) {
                ItemType.GRAIN -> "grain"
                ItemType.FRUITS -> "fruits"
                ItemType.SEAFOOD -> "seaFood"
                ItemType.MEAT_EGGS -> "meatEggs"
                ItemType.VEGETABLES -> "vegetables"
                ItemType.SEASONINGS -> "seasonings"
                ItemType.PROCESSED_FOODS -> "processedFoods"
                ItemType.DAIRY_PRODUCTS -> "dairyProducts"
                ItemType.BEVERAGES -> "beverages"
                ItemType.HOUSEHOLD_ITEMS -> "householdItems"
                ItemType.UNDEFINED -> "undefined"
                else -> null
            }
        }

        fun itemTypeToId(type: ItemType): Int {
            return when (type) {
                ItemType.GRAIN -> R.id.grain
                ItemType.FRUITS -> R.id.fruits
                ItemType.SEAFOOD -> R.id.seaFood
                ItemType.MEAT_EGGS ->  R.id.meatEggs
                ItemType.VEGETABLES -> R.id.vegetables
                ItemType.SEASONINGS -> R.id.seasonings
                ItemType.PROCESSED_FOODS ->  R.id.processedFoods
                ItemType.DAIRY_PRODUCTS -> R.id.dairyProducts
                ItemType.BEVERAGES -> R.id.beverages
                ItemType.HOUSEHOLD_ITEMS -> R.id.householdItems
                ItemType.UNDEFINED -> R.id.undefined
                else -> R.id.all
            }
        }

    }
}