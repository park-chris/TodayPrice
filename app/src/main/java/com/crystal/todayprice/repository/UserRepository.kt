package com.crystal.todayprice.repository

import com.crystal.todayprice.data.User
import com.crystal.todayprice.util.FirebaseCallback

interface UserRepository {
    fun createUser(user: User, callback: FirebaseCallback)
    suspend fun getUser(userId: String): User?
}

enum class Result {
    SUCCESS, FAIL
}