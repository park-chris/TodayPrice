package com.crystal.todayprice.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.crystal.todayprice.data.Market
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class MarketRepositoryImpl: MarketRepository {

    private val database = Firebase.firestore
    private val marketCollection = database.collection("markets")

    override suspend fun getAllMarkets(): List<Market> {

        return try {
            val snapshot = marketCollection.get().await()
            snapshot.toObjects<Market>()
        } catch (e: Exception) {
            emptyList()
        }
    }
}