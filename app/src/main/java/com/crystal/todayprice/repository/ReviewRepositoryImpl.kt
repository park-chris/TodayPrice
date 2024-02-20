package com.crystal.todayprice.repository

import com.crystal.todayprice.data.Review
import com.crystal.todayprice.util.FirebaseCallback
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import com.google.firebase.firestore.ktx.toObjects
import java.lang.Exception
import com.crystal.todayprice.util.Result
import com.google.firebase.firestore.Query


class ReviewRepositoryImpl: ReviewRepository {
    private val database = Firebase.firestore
    private val reviewRef = database.collection("reviews")

    override suspend fun getReview(marketId: Int): List<Review> {
        return try {
            val snapshot = reviewRef.whereEqualTo("marketId", marketId).get().await()
            snapshot.toObjects<Review>().sortedBy { it.date }.reversed()
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun getUserReview(userId: String): List<Review> {
        return try {
            val snapshot = reviewRef.whereEqualTo("userId", userId).whereEqualTo("state", true).get().await()
            snapshot.toObjects<Review>().sortedBy { it.date }.reversed()
        } catch (e: Exception) {
            emptyList()
        }
    }

    override fun addReview(review: Review, callback: FirebaseCallback) {
        val batch = database.batch()
        batch.set(reviewRef.document(review.id), review)
        batch.update(database.collection("markets").document(review.marketId.toString()), "reviewCount", FieldValue.increment(1))
        batch.commit().addOnSuccessListener {
            callback.onResult(Result.SUCCESS)
        }.addOnFailureListener {
            callback.onResult(Result.FAIL)
        }
    }

    override fun updateReview(review: Review, userId: String) {
        reviewRef.document(review.id).set(review, SetOptions.merge())
    }

    override fun updateBlockUser(reviewId: String, isContained: Boolean, userId: String) {
        if (isContained) {
            reviewRef.document(reviewId).update("blockUsers", FieldValue.arrayRemove(userId))
        } else {
            reviewRef.document(reviewId).update("blockUsers", FieldValue.arrayUnion(userId))
        }
    }

    override fun deleteReview(reviewId: String, callback: FirebaseCallback) {
        reviewRef.document(reviewId).delete().addOnSuccessListener {
            callback.onResult(Result.SUCCESS)
        }.addOnFailureListener {
            callback.onResult(Result.FAIL)
        }
    }

    override suspend fun deleteAccount(userId: String): Result {
        return try {
            val list = getUserReview(userId)
            val batch = database.batch()
            for (review in list ) {
                batch.update(reviewRef.document(review.id), "state", false)
            }
            batch.commit()
            Result.SUCCESS
        } catch (e: Exception) {
            Result.FAIL
        }
    }
}