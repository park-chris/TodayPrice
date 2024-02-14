package com.crystal.todayprice.repository

import com.crystal.todayprice.api.RetrofitManager
import com.crystal.todayprice.data.ListItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ListItemRepositoryImpl: ListItemRepository {
    override suspend fun getHomeList(): List<ListItem> =  withContext(Dispatchers.IO) {
        val response = RetrofitManager.firebaseService.getHomeList()
        response.list
    }

    override suspend fun searchQuery(query: String?) = withContext(Dispatchers.IO) {
        val response = RetrofitManager.firebaseService.getSearch(query)
        response.list
    }

}