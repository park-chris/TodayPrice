package com.crystal.todayprice.repository

import com.crystal.todayprice.data.Inquiry
import com.crystal.todayprice.util.FirebaseCallback
import com.crystal.todayprice.util.Result
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class InquiryRepositoryImpl: InquiryRepository {
    private val database = Firebase.firestore
    private val inquiryRef = database.collection("inquiries")

    override suspend fun getMyInquiry(userId: String): List<Inquiry> {
        return try {
            val snapshot = inquiryRef.whereEqualTo("userId", userId).get().await()
            snapshot.toObjects()
        } catch (e: Exception) {
            emptyList()
        }
    }

    override fun addInquiry(inquiry: Inquiry, callback: FirebaseCallback) {
        inquiryRef.document(inquiry.id).set(inquiry).addOnSuccessListener {
            callback.onResult(Result.SUCCESS)
        }.addOnFailureListener {
            callback.onResult(Result.FAIL)
        }
    }

    override fun deleteInquiry(inquiry: Inquiry, callback: FirebaseCallback) {
        inquiryRef.document(inquiry.id).delete().addOnSuccessListener {
            callback.onResult(Result.SUCCESS)
        }.addOnFailureListener {
            callback.onResult(Result.FAIL)
        }
    }

    override suspend fun deleteAccountInquiry(userId: String): Result {
        return try {
            val inquiries = getMyInquiry(userId)

            val batch = database.batch()

            for (inquiry in inquiries) {
                val doc = inquiryRef.document(inquiry.id)
                batch.delete(doc)
            }
            batch.commit()
            Result.SUCCESS
        } catch (e: Exception) {
            Result.FAIL
        }
    }

}