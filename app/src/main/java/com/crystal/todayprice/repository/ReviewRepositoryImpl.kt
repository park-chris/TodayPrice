package com.crystal.todayprice.repository

import com.crystal.todayprice.data.Review
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import com.google.firebase.firestore.ktx.toObjects
import java.lang.Exception


class ReviewRepositoryImpl: ReviewRepository {
    private val database = Firebase.firestore
    private val reviewCollection = database.collection("reviews")

    override suspend fun getReview(marketId: Int): List<Review> {
        return try {
            val snapshot = reviewCollection.whereEqualTo("marketId", marketId).get().await()
            snapshot.toObjects()
        } catch (e: Exception) {
            emptyList()
        }
    }
}