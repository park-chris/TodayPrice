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

    override fun addReview(review: Review, callback: FirebaseCallback) {
        val reviewHashMap = hashMapOf(
            "id" to review.id,
            "userId" to review.userId,
            "marketId" to review.marketId,
            "userName" to review.userName,
            "marketName" to review.marketName,
            "content" to review.content,
            "date" to review.date,
            "likeCount" to review.likeCount,
            "likeUsers" to review.likeUsers,
            "blockUsers" to review.blockUsers,
        )

        val batch = database.batch()
        batch.set(reviewRef.document(review.id), reviewHashMap)
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
}