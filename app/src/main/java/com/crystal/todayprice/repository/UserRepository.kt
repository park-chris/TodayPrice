package com.crystal.todayprice.repository

import com.crystal.todayprice.data.Market
import com.crystal.todayprice.data.User
import com.crystal.todayprice.util.FirebaseCallback

interface UserRepository {
    fun createUser(user: User, callback: FirebaseCallback)
    suspend fun getUser(userId: String): User?
    fun submitReport( reviewId: String, userId: String, reportTitle: String, reportContent: String?, callback: FirebaseCallback)
    suspend fun getFavoriteMarkets(userId: String): List<Market>
    fun addFavoriteMarket(userId: String, market: Market, firebaseCallback: FirebaseCallback)
    fun removeFavoriteMarket(userId: String, marketId: String, firebaseCallback: FirebaseCallback)

}

