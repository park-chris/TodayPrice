package com.crystal.todayprice.api

import com.crystal.todayprice.data.FirebaseResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface FirebaseService {

    @GET("getHomeList/")
    suspend fun getHomeList(): FirebaseResponse

    @GET("getSearch/")
    suspend fun getSearch(@Query("keyword") query: String?): FirebaseResponse

}