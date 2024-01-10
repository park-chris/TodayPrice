package com.crystal.todayprice.data

data class Item(
    val itemId: Int,
    val itemName: String,
    val itemPrice: Int,
    val category: ItemType,
    val prices: List<Price>,
)

data class Price(
    val itemId: Int = -1,
    val itemName: String = "",
    val itemPrice: Int = -1,
    val itemUnit: String = "",
    val surveyDate: String = "",
)

enum class ItemType {
    UNDEFINED,
    GRAIN,
    FRUITS,
    SEAFOOD,
    VEGETABLES,
    MEAT_EGGS,
    SEASONINGS,
    PROCESSED_FOODS,
    DAIRY_PRODUCTS,
    BEVERAGES,
    HOUSEHOLD_ITEMS,
}