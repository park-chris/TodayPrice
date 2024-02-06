package com.crystal.todayprice.util


interface FirebaseCallback {
    fun onResult(result: Result)
}

enum class Result {
    SUCCESS, FAIL
}