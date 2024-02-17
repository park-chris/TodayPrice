package com.crystal.todayprice.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.crystal.todayprice.data.Market
import com.crystal.todayprice.data.User
import com.crystal.todayprice.repository.UserRepository
import com.crystal.todayprice.repository.UserRepositoryImpl
import com.crystal.todayprice.util.FirebaseCallback

class UserViewModel() : ViewModel() {

    private val userRepository: UserRepository = UserRepositoryImpl()

    fun createUser(user: User, callback: FirebaseCallback) {
        userRepository.createUser(user, callback)
    }

    suspend fun getUser(userId: String): User? = userRepository.getUser(userId)
    fun submitReport(
        reviewId: String,
        userId: String,
        reportTitle: String,
        reportContent: String?,
        callback: FirebaseCallback
    ) {
        userRepository.submitReport(reviewId, userId, reportTitle, reportContent, callback)
    }
    suspend fun getFavoriteMarkets(userId: String): List<Market> = userRepository.getFavoriteMarkets(userId)
    fun addFavorite(
        userId: String,
        market: Market,
        firebaseCallback: FirebaseCallback
    ) {
        userRepository.addFavoriteMarket(userId, market, firebaseCallback)
    }

    fun removeFavorite(
        userId: String,
        marketId: String,
        firebaseCallback: FirebaseCallback
    ) {
        userRepository.removeFavoriteMarket(userId, marketId, firebaseCallback)
    }
}