package com.crystal.todayprice.repository

import com.crystal.todayprice.data.Item

interface ItemRepository {
    suspend fun getItem(marketId: Int, itemId: Int): Item?
}