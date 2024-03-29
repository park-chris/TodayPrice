package com.crystal.todayprice.data

import com.google.gson.annotations.SerializedName

data class FirebaseResponse(
    @SerializedName("message")
    val message: Int = -1,
    @SerializedName("list")
    val list: List<ListItem>
)
