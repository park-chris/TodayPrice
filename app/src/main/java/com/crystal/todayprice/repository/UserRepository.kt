package com.crystal.todayprice.repository

import com.crystal.todayprice.data.Market
import com.crystal.todayprice.data.User
import com.crystal.todayprice.util.*

interface UserRepository {
    fun createUser(user: User, callback: FirebaseCallback)
    fun updateUserName(user: User, callback: FirebaseCallback)
    suspend fun getUser(userId: String): User?
    fun submitReport( reviewId: String, userId: String, reportTitle: String, reportContent: String?, callback: FirebaseCallback)
    suspend fun getFavoriteMarkets(userId: String): List<Market>
    suspend fun addFavoriteMarket(userId: String, market: Market): Result
    suspend fun removeFavoriteMarket(userId: String, marketId: Int): Result

    suspend fun deleteAccount(user: User): Result

}

