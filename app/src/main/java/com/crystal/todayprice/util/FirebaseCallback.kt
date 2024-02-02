package com.crystal.todayprice.util

import com.crystal.todayprice.repository.Result

interface FirebaseCallback {
    fun onResult(result: Result)
}