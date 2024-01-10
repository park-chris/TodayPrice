package com.crystal.todayprice.repository

import com.crystal.todayprice.data.Item
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class ItemRepositoryImpl: ItemRepository {

    private val database = Firebase.firestore
    private val marketCollection = database.collection("markets")

    override suspend fun getItem(marketId: Int, itemId: Int): Item? {
        return try {

            var item: Item? = null
            val snapshot = marketCollection.document(marketId.toString()).collection("items").document(itemId.toString()).get().await()
            if (snapshot.exists()) {
                item = snapshot.toObject(Item::class.java)
            }
            item
        } catch (e: Exception) {
            null
        }
    }
}