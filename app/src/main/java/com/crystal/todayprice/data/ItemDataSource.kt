package com.crystal.todayprice.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.crystal.todayprice.api.PriceService
import java.lang.Exception

class ItemDataSource(
    private val query: String?,
    private val priceService: PriceService
): PagingSource<Int, NecessaryPrice>() {
    override fun getRefreshKey(state: PagingState<Int, NecessaryPrice>): Int? {
        return state.anchorPosition?.let {
            val closestPageToPosition = state.closestPageToPosition(it)
            closestPageToPosition?.prevKey?.plus(1 )
                ?: closestPageToPosition?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NecessaryPrice> {
        val page = params.key ?: defaultStart

        return try {

            val startIndex = (page - 1) * defaultDisplay + 1
            val endIndex = if (params.loadSize == defaultDisplay) {
                page * defaultDisplay
            } else {
                page * defaultDisplay + (params.loadSize - defaultDisplay)
            }
            val response = priceService.getMarketItems(startIndex, endIndex , query)
            val items = response.listNecessariesPrices.row
            val nextKey = if (items.isEmpty()) {
                null
            } else if (params.loadSize == defaultDisplay) {
                page + 1
            } else {
                val plusPage = params.loadSize / defaultDisplay
                page + plusPage
            }
            val prevKey = if (page == defaultStart) {
                null
            } else {
                page - 1
            }
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