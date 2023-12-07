package com.crystal.todayprice.callback

interface ResultCallback<T> {
    fun onSuccess(result: T)
    fun onFailure(error: Throwable)
}
