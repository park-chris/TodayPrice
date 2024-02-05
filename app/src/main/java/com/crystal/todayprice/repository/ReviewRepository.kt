package com.crystal.todayprice.repository

import com.crystal.todayprice.data.Market
import com.crystal.todayprice.data.Review
import com.crystal.todayprice.data.User
import com.crystal.todayprice.util.FirebaseCallback

interface ReviewRepository {
    suspend fun getReview(marketId: Int): List<Review>
    fun addReview(review: Review, callback: FirebaseCallback)

    fun updateReview(review: Review, userId: String)

}