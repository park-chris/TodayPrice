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
        Log.e(TAG, "getRefreshKey refresh")
        return state.anchorPosition?.let {
            val closestPageToPosition = state.closestPageToPosition(it)
            closestPageToPosition?.prevKey?.plus(defaultDisplay )
                ?: closestPageToPosition?.nextKey?.minus(defaultDisplay)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NecessaryPrice> {
        val start = params.key ?: defaultStart

        Log.e(TAG, "ItemDataSource load function is operate ${params.key} ${params.loadSize}")

        return try {
            val response = priceService.getMarketItems(start, start + params.loadSize - 1 , query)

            Log.e(TAG, "start $start end ${start + params.loadSize}")

            val items = response.listNecessariesPrices.row

            Log.e(TAG, "items: $items")


            Log.e(TAG, "items count : ${items.size}")
            val nextKey = if (items.isEmpty()) {
                null
            } else {
                start + params.loadSize - 1
            }
            val prevKey = if (start == defaultStart) {
                null
            } else {
                start - defaultDisplay + 1
            }

            Log.e(TAG, "nextPage: ${items.size } $prevKey $nextKey")

            LoadResult.Page(items, prevKey, nextKey)

        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    companion object {
        const val defaultStart = 1
        const val defaultDisplay = 30
    }
}