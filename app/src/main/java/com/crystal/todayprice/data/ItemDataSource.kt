package com.crystal.todayprice.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.crystal.todayprice.api.PriceService
import com.crystal.todayprice.repository.TAG
import java.lang.Exception

class ItemDataSource(
    private val query: String?,
    private val priceService: PriceService
): PagingSource<Int, NecessaryPrice>() {
    override fun getRefreshKey(state: PagingState<Int, NecessaryPrice>): Int? {
        return state.anchorPosition?.let {
            val closestPageToPosition = state.closestPageToPosition(it)
            closestPageToPosition?.prevKey?.plus(defaultDisplay)
                ?: closestPageToPosition?.nextKey?.minus(defaultDisplay)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NecessaryPrice> {
        val start = params.key ?: defaultStart

        Log.e(TAG, "ItemDataSource load function is operate")

        return try {
            val response = priceService.getMarketItems(defaultStart, defaultStart * defaultDisplay)

            Log.e(TAG, "priceService is started")

            val items = response.listNecessariesPrices.row
            val nextKey = if (items.isEmpty()) {
                null
            } else {
                start + params.loadSize
            }

            val prevKey = if (start == defaultStart) {
                null
            } else {
                start - defaultDisplay
            }
            LoadResult.Page(items, prevKey, nextKey)
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    companion object {
        const val defaultStart = 1
        const val defaultDisplay = 10
    }
}