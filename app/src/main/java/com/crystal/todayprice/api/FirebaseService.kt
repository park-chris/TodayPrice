package com.crystal.todayprice.api

import com.crystal.todayprice.data.HomeResponse
import retrofit2.http.GET

interface FirebaseService {

    @GET("getHomeList/")
    suspend fun getHomeList(): HomeResponse

}