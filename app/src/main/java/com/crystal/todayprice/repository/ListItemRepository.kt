package com.crystal.todayprice.repository

import com.crystal.todayprice.data.ListItem

interface ListItemRepository {
    suspend fun getHomeList(): List<ListItem>
    suspend fun searchQuery(query: String?): List<ListItem>

}