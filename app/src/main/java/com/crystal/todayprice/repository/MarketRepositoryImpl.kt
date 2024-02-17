package com.crystal.todayprice.repository

import com.crystal.todayprice.component.UserDataManager
import com.crystal.todayprice.data.Market
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import io.grpc.internal.JsonUtil.getList
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class MarketRepositoryImpl : MarketRepository {

    private val database = Firebase.firestore
    private val marketCollection = database.collection("markets")

    override suspend fun getAllMarkets(): List<Market> {
        return try {
            val user = UserDataManager.getInstance().user
            val snapshot = marketCollection.get().await()
            val list = snapshot.toObjects<Market>()

            if (user != null) {
                list.forEach { market ->
                    market.favoriteState = user.favoriteList.contains(market.id)
                }
            }
            list
        } catch (e: Exception) {
            emptyList()
        }
    }

    override fun countMarketReview(marketId: Int, count: Int) {
        marketCollection.document(marketId.toString())
            .update("reviewCount", FieldValue.increment(count.toDouble()))
    }
}