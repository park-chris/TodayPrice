package com.crystal.todayprice.repository

import com.crystal.todayprice.data.Notice
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class NoticeRepositoryImpl: NoticeRepository {
    private val database = Firebase.firestore
    private val noticeRef = database.collection("notices")

    override suspend fun getNotice(): List<Notice> {
        return try {
            val snapshot = noticeRef.get().await()
            snapshot.toObjects()
        } catch (e: Exception) {
            emptyList()
        }
    }

}