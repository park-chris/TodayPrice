package com.crystal.todayprice.repository

import com.crystal.todayprice.data.Review
import com.crystal.todayprice.util.FirebaseCallback
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import com.google.firebase.firestore.ktx.toObjects
import java.lang.Exception


class ReviewRepositoryImpl: ReviewRepository {
    private val database = Firebase.firestore
    private val reviewRef = database.collection("reviews")

    override suspend fun getReview(marketId: Int): List<Review> {
        return try {
            val snapshot = reviewRef.whereEqualTo("marketId", marketId).get().await()
            snapshot.toObjects()
        } catch (e: Exception) {
            emptyList()
        }
    }

    override fun addReview(review: Review, callback: FirebaseCallback) {
        val reviewHashMap = hashMapOf(
            "id" to review.id,
            "userId" to review.userId,
            "marketId" to review.marketId,
            "userName" to review.userName,
            "content" to review.content,
            "date" to review.date,
            "likeCount" to review.likeCount,
            "likeUsers" to review.likeUsers,
        )

        reviewRef.document(review.id).set(reviewHashMap).addOnSuccessListener {
            callback.onResult(Result.SUCCESS)
        }.addOnFailureListener {
            callback.onResult(Result.FAIL)
        }
    }
}