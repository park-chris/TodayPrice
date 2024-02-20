package com.crystal.todayprice.repository

import com.crystal.todayprice.data.Inquiry
import com.crystal.todayprice.data.Notice
import com.crystal.todayprice.util.FirebaseCallback
import com.crystal.todayprice.util.Result

interface InquiryRepository {
    suspend fun getMyInquiry(userId: String): List<Inquiry>
    fun addInquiry(inquiry: Inquiry, callback: FirebaseCallback)
    fun deleteInquiry(inquiry: Inquiry, callback: FirebaseCallback)
    suspend fun deleteAccountInquiry(userId: String): Result
}