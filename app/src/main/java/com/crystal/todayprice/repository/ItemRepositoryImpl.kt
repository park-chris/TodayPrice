package com.crystal.todayprice.repository

import android.util.Log
import com.crystal.todayprice.data.Item
import com.crystal.todayprice.data.Market
import com.crystal.todayprice.data.Price
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import com.google.firebase.firestore.ktx.toObjects

class ItemRepositoryImpl : ItemRepository {

    private val database = Firebase.firestore
    private val marketCollection = database.collection("markets")
    override suspend fun getItem(marketId: Int, itemId: Int): List<Price> {
        return try {
            val snapshot = marketCollection.document(marketId.toString()).collection("items").document(itemId.toString()).collection("prices").get().await()
            snapshot.toObjects<Price>()
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun getItems(marketId: Int): List<Item> {
        return try {
            val snapshot = marketCollection.document(marketId.toString()).collection("items").get().await()
            snapshot.toObjects<Item>()
        } catch (e: Exception) {
            emptyList()
        }
    }
}