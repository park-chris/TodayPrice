package com.crystal.todayprice.api

import com.crystal.todayprice.BuildConfig
import com.crystal.todayprice.data.ListItem
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitManager {

    private const val BASE_URL = "http://openAPI.seoul.go.kr:8088/${BuildConfig.PUBLIC_API_KEY}/json/"
    private const val FIREBASE_URL = BuildConfig.FIREBASE_URL

    // okHttpClient Settings
    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .build()

    private val gson = GsonBuilder().registerTypeAdapter(ListItem::class.java, ListItemDeserializer()).setLenient().create()

    private val retrofit = Retrofit.Builder()
        .baseUrl(FIREBASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    val priceService: PriceService by lazy { retrofit.create(PriceService::class.java) }

    val firebaseService: FirebaseService by lazy { retrofit.create(FirebaseService::class.java) }
}