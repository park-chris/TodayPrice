package com.crystal.todayprice.repository

import com.crystal.todayprice.data.User
import com.crystal.todayprice.util.FirebaseCallback
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.lang.Exception

class UserRepositoryImpl : UserRepository {
    private val database = Firebase.firestore
    private val userRef = database.collection("users")

    override fun createUser(user: User, callback: FirebaseCallback) {
        val userHashMap = hashMapOf(
            "id" to user.id,
            "name" to user.name,
            "email" to user.email,
        )
        userRef.document(user.id).set(userHashMap).addOnSuccessListener {
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
}