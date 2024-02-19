package com.crystal.todayprice.repository

import com.crystal.todayprice.component.UserDataManager
import com.crystal.todayprice.data.Market
import com.crystal.todayprice.data.User
import com.crystal.todayprice.util.FirebaseCallback
import com.crystal.todayprice.util.Result
import com.crystal.todayprice.util.TextUtil
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.lang.Exception

class UserRepositoryImpl : UserRepository {
    private val database = Firebase.firestore
    private val userRef = database.collection("users")
    private val favoriteRef = database.collection("favorites")

    override fun createUser(user: User, callback: FirebaseCallback) {
        userRef.document(user.id).set(user).addOnSuccessListener {
            callback.onResult(Result.SUCCESS)
        }.addOnFailureListener {
            callback.onResult(Result.FAIL)
        }
    }

    override suspend fun getUser(userId: String): User? = withContext(Dispatchers.IO) {
        try {
            val snapshot = userRef.document(userId).get().await()
            snapshot.toObject<User>()
        } catch (e: Exception) {
            null
        }
    }

    override fun submitReport(
        reviewId: String,
        userId: String,
        reportTitle: String,
        reportContent: String?,
        callback: FirebaseCallback
    ) {
        val reportHashMap = hashMapOf(
            "reviewId" to reviewId,
            "userId" to userId,
            "title" to reportTitle,
            "content" to reportContent,
            "date" to  TextUtil.todayDateString(),
            "isChecked" to false
        )

        database.collection("report").document().set(reportHashMap).addOnSuccessListener {
            callback.onResult(Result.SUCCESS)
        }.addOnFailureListener {
            callback.onResult(Result.FAIL)
        }
    }

    override suspend fun getFavoriteMarkets(userId: String): List<Market> =  withContext(Dispatchers.IO) {
        try {
            val user = UserDataManager.getInstance().user
            val snapshot = favoriteRef.document(userId).collection("market").get().await()

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

    override suspend fun addFavoriteMarket(
        userId: String,
        market: Market
    ): Result {
        val batch = database.batch()
        batch.update(userRef.document(userId), "favoriteList", FieldValue.arrayUnion(market.id))
        batch.set(favoriteRef.document(userId).collection("market").document(market.id.toString()), market)
        return try {
            batch.commit().await()
            Result.SUCCESS
        } catch (e: Exception) {
            Result.FAIL
        }
    }

    override suspend fun removeFavoriteMarket(
        userId: String,
        marketId: Int
    ): Result {
        val batch = database.batch()
        batch.update(userRef.document(userId), "favoriteList", FieldValue.arrayRemove(marketId))
        batch.delete(favoriteRef.document(userId).collection("market").document(marketId.toString()))
        return try {
            batch.commit().await()
            Result.SUCCESS
        } catch (e: Exception) {
            Result.FAIL
        }
    }

}