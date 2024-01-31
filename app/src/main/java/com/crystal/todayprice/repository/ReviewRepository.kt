package com.crystal.todayprice.repository

import com.crystal.todayprice.data.Market
import com.crystal.todayprice.data.Review

interface ReviewRepository {
    suspend fun getReview(marketId: Int): List<Review>
}