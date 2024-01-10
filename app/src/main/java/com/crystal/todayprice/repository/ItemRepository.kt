package com.crystal.todayprice.repository

import com.crystal.todayprice.data.Item
import com.crystal.todayprice.data.Price

interface ItemRepository {
    suspend fun getItem(marketId: Int, itemId: Int): List<Price>
}